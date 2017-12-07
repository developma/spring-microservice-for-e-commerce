package com.shipping.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shipping.domain.OrderInfo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestData {

    private static OrderInfo orderInfo_valid =
            new OrderInfo(
                    Arrays.asList(new OrderInfo.Item(1, 10)),
                    "testSender",
                    "testReceiver",
                    new OrderInfo.Address("123-4567", "locloclocloc"));

    private static OrderInfo orderInfo_invalid_item =
            new OrderInfo(
                    Arrays.asList(new OrderInfo.Item(0, -1)),
                    "testSender",
                    "testReceiver",
                    new OrderInfo.Address("123-4567", "locloclocloc"));

    private static OrderInfo orderInfo_invalid_sender_receiver =
            new OrderInfo(
                    Arrays.asList(new OrderInfo.Item(1, 10)),
                    "",
                    null,
                    new OrderInfo.Address("123-4567", "locloclocloc"));

    private static OrderInfo orderInfo_invalid_address =
            new OrderInfo(
                    Arrays.asList(new OrderInfo.Item(1, 10)),
                    "testSender",
                    "testReceiver",
                    new OrderInfo.Address("abc123", null));


    public static Map<String, String> getTestData() throws JsonProcessingException {
        return new HashMap<String ,String>() {
            {
                final ObjectMapper objectMapper = new ObjectMapper();
                put("orderInfo_valid", objectMapper.writeValueAsString(orderInfo_valid));
                put("orderInfo_invalid_item", objectMapper.writeValueAsString(orderInfo_invalid_item));
                put("orderInfo_invalid_sender_receiver", objectMapper.writeValueAsString(orderInfo_invalid_sender_receiver));
                put("orderInfo_invalid_address", objectMapper.writeValueAsString(orderInfo_invalid_address));
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
