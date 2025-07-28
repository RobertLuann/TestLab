package com.ufersa.testlab.exceptions;

public class EmailInvalidoException extends IllegalArgumentException {
    public EmailInvalidoException(String message) {
        super(message);
    }

    public EmailInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }
}
