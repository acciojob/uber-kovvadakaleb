package com.driver.exception;

public class TripNotFound extends RuntimeException{
    public TripNotFound(String message) {
        super(message);
    }
}
