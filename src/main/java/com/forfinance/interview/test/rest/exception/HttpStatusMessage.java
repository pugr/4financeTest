package com.forfinance.interview.test.rest.exception;

public final class HttpStatusMessage {

    private final String message;
    
    public HttpStatusMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
