package com.orange.weather.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvalidDataErrorResponse {

    private String message;
    private int status;
    private long timeStamp;
}
