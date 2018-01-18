package com.shipping.controller;

import com.shipping.domain.OrderInfo;
import com.shipping.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Validated
@RequestMapping("/shipping")
@RestController
public class ShippingController {

    private final ShippingService shippingService;

    @Autowired
    public ShippingController(final ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @PostMapping(value = "/order/")
    public String order(@Validated @RequestBody final OrderInfo orderInfo) {
        return shippingService.order(orderInfo);
    }
}
