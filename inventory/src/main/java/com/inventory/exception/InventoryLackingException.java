package com.inventory.exception;

public class InventoryLackingException extends RuntimeException {
    public InventoryLackingException() {
        super("there is not enough amount.");
    }
}
