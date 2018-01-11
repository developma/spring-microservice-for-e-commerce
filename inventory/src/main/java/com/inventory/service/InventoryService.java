package com.inventory.service;

import com.inventory.domain.Item;
import com.inventory.exception.ItemNotFoundException;
import com.inventory.repository.InventoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryMapper inventoryMapper;
    private static final Integer NO_CATEGORY_KEY = null;

    @Autowired
    public InventoryService(final InventoryMapper inventoryMapper) {
        this.inventoryMapper = inventoryMapper;
    }

    public List<Item> items() {
        return inventoryMapper.selectItemsByCategoryId(NO_CATEGORY_KEY);
    }

    public Item item(final Integer id) {
        final Item item = inventoryMapper.selectItemById(id);
        if (item == null) {
            throw new ItemNotFoundException();
        }
        return item;
    }

    public List<Item> items(final Integer categoryId) {
        return inventoryMapper.selectItemsByCategoryId(categoryId);
    }
}
