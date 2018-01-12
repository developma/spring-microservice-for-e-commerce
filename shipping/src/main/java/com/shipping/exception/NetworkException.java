package com.shipping.exception;

import org.springframework.web.client.ResourceAccessException;

public class NetworkException extends RuntimeException {
    public NetworkException(ResourceAccessException e) {
        super(e);
    }
}
