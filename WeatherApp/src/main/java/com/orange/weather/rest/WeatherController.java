package com.orange.weather.rest;

import com.orange.weather.DTO.WeatherDTO;
import com.orange.weather.services.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping
    // default GetMapping for "/api/weather" gets weather data for Cairo
    public ResponseEntity<WeatherDTO> getWeather (){
        WeatherDTO weatherDTO = weatherService.getWeather("Cairo");
        return ResponseEntity.status(HttpStatus.FOUND).body(weatherDTO);
    }

    @GetMapping("/{city}")
    public ResponseEntity<WeatherDTO> getWeather(@PathVariable String city){
        WeatherDTO weatherDTO = weatherService.getWeather(city);
        return ResponseEntity.status(HttpStatus.FOUND).body(weatherDTO);
    }
}
