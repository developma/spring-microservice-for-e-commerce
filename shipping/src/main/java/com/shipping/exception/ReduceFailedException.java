package com.shipping.exception;

public class ReduceFailedException extends RuntimeException {
    public ReduceFailedException() {
        super("your order was not processed.");
    }
}
