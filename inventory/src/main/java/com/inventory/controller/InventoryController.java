package com.inventory.controller;

import com.inventory.domain.Item;
import com.inventory.exception.IllegalRequestBodyException;
import com.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin // To allow accessing from other domain.
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

    @PostMapping("/update/")
    public String reduce(@RequestBody final List<Item> items) {
        return inventoryService.reduce(items);
    }

    @GetMapping("/check/{id}/")
    public Item check(@PathVariable final String id) {
        try {
            return inventoryService.check(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            throw new IllegalRequestBodyException(e);
        }
    }
}
