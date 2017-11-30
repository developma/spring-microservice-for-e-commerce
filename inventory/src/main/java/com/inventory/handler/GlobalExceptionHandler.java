package com.inventory.handler;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Component
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorInformation handle(final NoHandlerFoundException e) {
        return new ErrorInformation
                .Builder("SVR_URI_002", "an invalid url was specified for path of URI.").build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInformation handle(final MethodArgumentTypeMismatchException e) {
        return new ErrorInformation
                .Builder("SVR_URI_001", "an invalid parameter was specified for path of URI.").build();
    }

    static class ErrorInformation {

        private final String errorId;
        private final String errorMessage;
        private final String countermeasure;

        private ErrorInformation(final Builder builder) {
            errorId = builder.errorId;
            errorMessage = builder.errorMessage;
            countermeasure = builder.countermeasure;
        }

        public String getErrorId() {
            return errorId;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public String getCountermeasure() {
            return countermeasure;
        }

        static class Builder {

            private final String errorId;
            private final String errorMessage;
            private String countermeasure;

            Builder(final String errorId, final String errorMessage) {
                this.errorId = errorId;
                this.errorMessage = errorMessage;
            }

            public Builder countermeasure(final String countermeasure) {
                this.countermeasure = countermeasure;
                return this;
            }

            ErrorInformation build() {
                return new ErrorInformation(this);
            }
        }
    }
}
