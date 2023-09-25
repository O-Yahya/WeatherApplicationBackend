package com.orange.weather.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDTO {

    private String city;
    private String region;
    private String country;
    private float temp_c;
    private float temp_f;
}
