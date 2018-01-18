package com.shipping.service;

import com.shipping.domain.OrderInfo;
import com.shipping.domain.OrderedItem;
import com.shipping.exception.ItemNotFoundException;
import com.shipping.exception.ItemUnitLackingException;
import com.shipping.exception.NetworkException;
import com.shipping.exception.ReduceFailedException;
import com.shipping.generator.Generator;
import com.shipping.repository.AddressMapper;
import com.shipping.repository.OrderHistoryMapper;
import com.shipping.repository.OrderedItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ShippingService {

    private RestTemplate restTemplate;

    private AddressMapper addressMapper;

    private OrderedItemMapper orderedItemMapper;

    private OrderHistoryMapper orderHistoryMapper;

    private Generator generator;

    private static final String ID_PATTERN = "YYYYMMDDHHSSMMM";

    @Autowired
    public ShippingService(final RestTemplate restTemplate,
                           final AddressMapper addressMapper,
                           final OrderedItemMapper orderedItemMapper,
                           final OrderHistoryMapper orderHistoryMapper,
                           final Generator generator) {
        this.restTemplate = restTemplate;
        this.addressMapper = addressMapper;
        this.orderedItemMapper = orderedItemMapper;
        this.orderHistoryMapper = orderHistoryMapper;
        this.generator = generator;
    }

    public String order(final OrderInfo orderInfo) {
        orderInfo.getOrderedItem().forEach(orderedItem -> {
            checkInventory(orderedItem);
        });
        updateRegisterId(orderInfo);
        registerOrderInfo(orderInfo);

        return "success";
    }

    private void updateRegisterId(final OrderInfo orderInfo) {
        final Long registerId = generator.generate();
        orderInfo.setRegisterId(registerId);
        orderInfo.getAddress().setRegisterId(registerId);
        orderInfo.getOrderedItem().forEach(orderedItem -> orderedItem.setRegisterId(registerId));
        orderInventory(orderInfo.getOrderedItem());
    }

    private void registerOrderInfo(final OrderInfo orderInfo) {
        addressMapper.insertAddress(orderInfo.getAddress());
        orderInfo.getOrderedItem().forEach(orderedItem -> orderedItemMapper.insertOrderedItem(orderedItem));
        orderHistoryMapper.insertOrderHistory(orderInfo);
    }

    private void orderInventory(final List<OrderedItem> orderedItem) {
        final String result = restTemplate.postForObject("http://localhost:8200/inventory/update/",
                orderedItem,
                String.class);
        if (!"success".equals(result)) {
            throw new ReduceFailedException();
        }
    }

    private void checkInventory(final OrderedItem orderedItem) {
        final HashMap<String, String> params = new HashMap<>();
        params.put("itemId", orderedItem.getId().toString());

        try {
            Map<String, Object> result =
                    restTemplate.getForObject("http://localhost:8200/inventory/check/{itemId}/", Map.class, params);
                if (result.containsKey("errorId")) {
                    if ("SVR_URI_010".equals(result.get("errorId"))) {
                        throw new ItemNotFoundException();
                    }
                }

                if (Integer.parseInt(result.get("unit").toString()) < orderedItem.getUnit()) {
                    throw new ItemUnitLackingException();
                }
                orderedItem.setVersionno(Long.parseLong(result.get("versionno").toString()));
        } catch (ResourceAccessException | HttpClientErrorException e) {
            throw new NetworkException(e);
        }
    }
}
