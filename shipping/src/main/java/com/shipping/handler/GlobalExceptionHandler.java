package com.shipping.handler;

import com.common.domain.ErrorInformation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Component
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInformation handle(final MethodArgumentNotValidException e) {
        return new ErrorInformation.Builder("SHIPPING_SVR_001", "an invalid value was specified for posted json.").build();
    }
}
