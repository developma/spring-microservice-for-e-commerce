package com.storefrontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StoreFrontEndController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
