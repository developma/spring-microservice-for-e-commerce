package com.shipping.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shipping.domain.Address;
import com.shipping.domain.OrderInfo;
import com.shipping.domain.OrderedItem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestData {

    public static final OrderInfo orderInfo =
            new OrderInfo(
                    1L,
                    Arrays.asList(new OrderedItem(1L, 1, 1, 0L)), "TestTestTest",
                    new Address(1L, "123-4567", "TestLocTestLoc", "NameNameName")
            );
    public static final OrderInfo orderInfo_invalid =
            new OrderInfo(
                    1L,
                    Arrays.asList(new OrderedItem(1L, 99, 1, 0L)), "TestTestTest",
                    new Address(1L, "123-4567", "TestLocTestLoc", "NameNameName")
            );

    public static Map<String, String> getTestData() throws JsonProcessingException {
        return new HashMap<String, String>() {
            {
                final ObjectMapper objectMapper = new ObjectMapper();
                put("ORDER_INFO_VALID", objectMapper.writeValueAsString(orderInfo));
                put("ORDER_INFO_INVALID", objectMapper.writeValueAsString(orderInfo_invalid));
            }
        };
    }

    public static void main(String[] args) {
        try {
            System.out.println(getTestData());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
