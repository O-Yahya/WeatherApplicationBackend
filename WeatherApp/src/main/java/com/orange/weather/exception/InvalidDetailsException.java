package com.orange.weather.exception;

// Exception for when a user enters any form of invalid details
// includes: invalid data type, empty required fields, trying to register with a previously used email
public class InvalidDetailsException extends RuntimeException{

    public InvalidDetailsException(String message) {
        super(message);
    }

    public InvalidDetailsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDetailsException(Throwable cause) {
        super(cause);
    }
}
