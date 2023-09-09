package com.messagingappdemo.demo.exception;

public class InvalidResourceException extends RuntimeException {
    public InvalidResourceException(String resource_not_found) {
        super(resource_not_found);
    }
}
