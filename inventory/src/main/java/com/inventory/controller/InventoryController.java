package com.inventory.controller;

import com.inventory.domain.Item;
import com.inventory.domain.ReduceInfo;
import com.inventory.exception.IllegalRequestBodyException;
import com.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/reduce/")
    public String reduce(@RequestBody final Map<String, String> params) {
        final String reqId = params.get("id");
        final String reqUnit = params.get("unit");
        final String reqVersionNo = params.get("versionno");

        if (reqId == null || reqUnit == null || reqVersionNo == null) {
            throw new IllegalRequestBodyException();
        }

        if (!isNumber(reqId, reqUnit)) {
            throw new IllegalRequestBodyException();
        }

        final Integer id = Integer.valueOf(reqId);
        final Integer unit = Integer.valueOf(reqUnit);
        final Long versionno = Long.valueOf(reqVersionNo);
        return inventoryService.reduce(new ReduceInfo(id, unit, versionno));
    }

    @GetMapping("/check/{id}/")
    public Item check(@PathVariable final Integer id) {
        return inventoryService.check(id);
    }

    private boolean isNumber(String... args) {
        for (String value : args) {
            try {
                Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }
}
