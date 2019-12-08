package com.heycar.challenge.exceptions;

public abstract class HeyCarException extends RuntimeException {

    private final String message;
    private Exception exception;

    public HeyCarException(String message) {
        this.message = message;
    }

    public HeyCarException(String message, Exception exception) {
        this.message = message;
        this.exception = exception;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
