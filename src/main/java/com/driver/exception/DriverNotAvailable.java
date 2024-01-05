package com.driver.exception;

public class DriverNotAvailable extends RuntimeException{
    public DriverNotAvailable(String message) {
        super(message);
    }
}
