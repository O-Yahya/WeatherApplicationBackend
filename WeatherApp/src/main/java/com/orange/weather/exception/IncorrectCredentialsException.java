package com.orange.weather.exception;

// Exception for when a user enters incorrect login credentials
public class IncorrectCredentialsException extends RuntimeException{

    public IncorrectCredentialsException(String message) {
        super(message);
    }

    public IncorrectCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectCredentialsException(Throwable cause) {
        super(cause);
    }
}
