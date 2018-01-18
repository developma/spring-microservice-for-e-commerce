package com.shipping.exception;

public class NetworkException extends RuntimeException {
    public NetworkException(Throwable e) {
        super(e);
    }
}
