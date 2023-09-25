package com.orange.weather.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Exception handler methods for created exceptions
@ControllerAdvice
public class DataErrorHandler {

    @ExceptionHandler
    public ResponseEntity<InvalidDataErrorResponse> handle(InvalidDetailsException exception){
        InvalidDataErrorResponse error = new InvalidDataErrorResponse();

        error.setMessage(exception.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setTimeStamp(System.currentTimeMillis());

        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler
    public ResponseEntity<InvalidDataErrorResponse> handle(InvalidCityNameException exception){
        InvalidDataErrorResponse error = new InvalidDataErrorResponse();

        error.setMessage(exception.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setTimeStamp(System.currentTimeMillis());

        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler
        public ResponseEntity<InvalidDataErrorResponse> handle(IncorrectCredentialsException exception){
        InvalidDataErrorResponse error = new InvalidDataErrorResponse();

        error.setMessage(exception.getMessage());
        error.setStatus(HttpStatus.FORBIDDEN.value());
        error.setTimeStamp(System.currentTimeMillis());

        return ResponseEntity.status(error.getStatus()).body(error);
    }
}
