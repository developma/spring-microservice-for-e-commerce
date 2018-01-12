package com.shipping.exception;

public class ItemUnitLackingException extends RuntimeException {
    public ItemUnitLackingException() {
        super("there is not enough amount.");
    }
}
