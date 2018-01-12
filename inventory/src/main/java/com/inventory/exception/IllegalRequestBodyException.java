package com.inventory.exception;

public class IllegalRequestBodyException extends RuntimeException {
    public IllegalRequestBodyException() {
        super("the data of request is not valid.");
    }
}
