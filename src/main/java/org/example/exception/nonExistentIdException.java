package org.example.exception;

public class nonExistentIdException extends RuntimeException{

    public nonExistentIdException(String msg) {
        super(msg);
    }
}
