package com.driver.exception;

public class AdminNotFound extends RuntimeException{
    public AdminNotFound(String message) {
        super(message);
    }
}
