package com.orange.weather.exception;

// Exception for when user tries to get weather for a city which does not exist in Weather API
public class InvalidCityNameException extends RuntimeException{

    public InvalidCityNameException(String message) {
        super(message);
    }

    public InvalidCityNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCityNameException(Throwable cause) {
        super(cause);
    }
}
