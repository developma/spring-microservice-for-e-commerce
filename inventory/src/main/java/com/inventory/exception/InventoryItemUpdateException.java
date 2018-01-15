package com.inventory.exception;

public class InventoryItemUpdateException extends RuntimeException {
    public InventoryItemUpdateException() {
        super("an error occurs during updating item.");
    }
}
