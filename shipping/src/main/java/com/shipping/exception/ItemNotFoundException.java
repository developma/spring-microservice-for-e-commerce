package com.shipping.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException() {
        super("could not find specified item in the inventory service.");
    }
}
