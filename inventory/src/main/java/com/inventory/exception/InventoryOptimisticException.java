package com.inventory.exception;

public class InventoryOptimisticException extends RuntimeException {

    public InventoryOptimisticException() {
        super("The version no of specified Item was updated by other user.");
    }
}
