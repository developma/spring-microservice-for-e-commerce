package com.shipping.service;

import com.shipping.domain.OrderInfo;
import com.shipping.domain.OrderedItem;
import com.shipping.exception.ItemNotFoundException;
import com.shipping.exception.ItemUnitLackingException;
import com.shipping.exception.NetworkException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class ShippingService {

    @Autowired
    private RestTemplate restTemplate;

    public String order(final OrderInfo orderInfo) {

//        - [ ] Serviceクラスにメソッドを用意
//                + [ ] 注文品を取得し、数量をinventoryサービスに問い合わせするロジック追加
//                + [ ] 次の例外クラスを追加
//                * [ ] 注文品がそもそも無い場合
//                * [ ] 注文品の個数が足りない場合
//                * [ ] ネットワーク普通の場合
//                + [ ] inventoryの在庫を減らすリクエスト追加
//                + [ ] 注文情報を登録
//                * [ ] AddressMapper
//                * [ ] OrderedItemMapper
//                * [ ] OrderHistoryMapper

        orderInfo.getOrderedItem().forEach(orderedItem -> {
            checkInventory(orderedItem);
            orderInventory(orderedItem);
        });

        return null;
    }

    private void orderInventory(final OrderedItem orderedItem) {
        final Map result = restTemplate.postForObject("http://localhost:8200/inventory/reduce/", orderedItem, Map.class);
    }

    private void checkInventory(final OrderedItem orderedItem) {
        final HashMap<String, String> params = new HashMap<>();
        params.put("itemId", orderedItem.getItemId().toString());
        Map<String, Object> result = null;

        try {
            result = restTemplate.getForObject("http://localhost:8200/inventory/item/{itemId}/", Map.class, params);
        } catch (ResourceAccessException e) {
            throw new NetworkException(e);
        }

        if (result.containsKey("errorId")) {
            if ("SVR_URI_010".equals(result.get("errorId"))) {
                throw new ItemNotFoundException();
            }
        }

        if (Integer.parseInt(result.get("unit").toString()) <= orderedItem.getItemUnit()) {
            throw new ItemUnitLackingException();
        }
    }
}
