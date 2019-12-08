package com.heycar.challenge.exceptions;

public class FileReadingException extends HeyCarException {

    public FileReadingException(String message,Exception ex) {
        super(message,ex);
    }
}
