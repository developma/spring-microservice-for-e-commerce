package com.shipping.exception;

public class GenerateIdException extends RuntimeException {
    private static final String DEFAULTMESSAGE = "exception occurs during generating id.";
    public GenerateIdException() {
        super(DEFAULTMESSAGE);
    }

    public GenerateIdException(final RuntimeException e) {
        super(DEFAULTMESSAGE, e);
    }
}
