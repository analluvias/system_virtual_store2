package org.example.exception;

public class CartInUseException extends RuntimeException {

    public CartInUseException(String message) {
        super(message);
    }
}
