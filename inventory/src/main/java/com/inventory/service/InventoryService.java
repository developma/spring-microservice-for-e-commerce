package com.inventory.service;

import com.inventory.domain.Item;
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
        return inventoryMapper.selectItemById(id);
    }

    public List<Item> items(final Integer categoryId) {
        return inventoryMapper.selectItemsByCategoryId(categoryId);
    }
}
