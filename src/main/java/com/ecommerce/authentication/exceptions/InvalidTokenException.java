package com.ecommerce.authentication.exceptions;

public class InvalidTokenException extends Exception {
    private static final long serialVersionUID = 1L;
    public InvalidTokenException(String message) {
        super(message);
    }
}
