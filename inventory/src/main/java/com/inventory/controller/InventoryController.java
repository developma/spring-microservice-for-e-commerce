package com.inventory.controller;

import com.inventory.domain.Item;
import com.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/inventory")
@RestController
@Validated
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(final InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/items/")
    public List<Item> items() {
        return inventoryService.items();
    }

    @GetMapping("/items/category/{id}/")
    public List<Item> itemsOfCategory(@PathVariable("id") final Integer categoryId) {
        return inventoryService.items(categoryId);
    }

    @GetMapping("/item/{id}/")
    public Item item(@PathVariable("id") final Integer id) {
        return inventoryService.item(id);
    }
}
