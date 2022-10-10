package org.example.exception;

public class ClientWithOpenedOrdersException extends RuntimeException{

    public ClientWithOpenedOrdersException(String message) {
        super(message);
    }

}
