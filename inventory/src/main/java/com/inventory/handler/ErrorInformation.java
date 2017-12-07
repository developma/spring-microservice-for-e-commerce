package com.inventory.handler;

import lombok.Getter;

@Getter
public class ErrorInformation {

    private final String errorId;
    private final String errorMessage;
    private final String solution;

    private ErrorInformation(final Builder builder) {
        errorId = builder.errorId;
        errorMessage = builder.errorMessage;
        solution = builder.solution;
    }

    public static class Builder {

        private final String errorId;
        private final String errorMessage;
        private String solution;

        public Builder(final String errorId, final String errorMessage) {
            this.errorId = errorId;
            this.errorMessage = errorMessage;
        }

        public Builder countermeasure(final String solution) {
            this.solution = solution;
            return this;
        }

        public ErrorInformation build() {
            return new ErrorInformation(this);
        }
    }
}
