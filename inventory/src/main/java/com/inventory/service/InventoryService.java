package com.inventory.service;

import com.inventory.domain.Item;
import com.inventory.domain.ReduceInfo;
import com.inventory.exception.InventoryLackingException;
import com.inventory.exception.ItemNotFoundException;
import com.inventory.repository.InventoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public String reduce(final ReduceInfo reduceInfo) {
        final Item item = inventoryMapper.selectItemById(reduceInfo.getId());
        final Integer originalUnit = item.getUnit();
        final Integer calcedUnit = originalUnit - reduceInfo.getUnit();
        if (calcedUnit < 0) {
            throw new InventoryLackingException();
        }
        reduceInfo.setUnit(calcedUnit);
        inventoryMapper.reduce(reduceInfo);
        return "success";
    }

    public Item check(final Integer id) {
        final Item item = inventoryMapper.selectItemById(id);
        if (item == null) {
            throw new ItemNotFoundException();
        }
        return wdightSaving(item);
    }

    private Item wdightSaving(Item item) {
        item.setCategory(null);
        item.setPict(null);
        item.setDescription(null);
        return item;
    }
}
