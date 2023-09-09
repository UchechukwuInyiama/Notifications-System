package com.messagingappdemo.demo.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String s)
    {
        super(s);
    }
}
