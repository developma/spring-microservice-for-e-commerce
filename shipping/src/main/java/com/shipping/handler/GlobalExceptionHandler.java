package com.shipping.handler;

import com.shipping.exception.ItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Component
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInformation handle(final MethodArgumentNotValidException e) {
        return new ErrorInformation.Builder("SHIPPING_SVR_001", "an invalid value was specified for posted json.").build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInformation handle(final ItemNotFoundException e) {
        return new ErrorInformation.Builder("SHIPPING_SVR_002", e.getMessage()).build();
    }
}
