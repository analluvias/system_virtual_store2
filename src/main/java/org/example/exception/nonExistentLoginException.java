package org.example.exception;

public class nonExistentLoginException extends RuntimeException{

    public nonExistentLoginException(String message) {
        super(message);
    }
}
