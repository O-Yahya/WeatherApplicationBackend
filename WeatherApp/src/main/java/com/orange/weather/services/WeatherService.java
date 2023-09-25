package com.orange.weather.services;

import com.orange.weather.DTO.WeatherDTO;
import com.orange.weather.exception.InvalidCityNameException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherService {


    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
    private static final String key = "005e653ed0dc4d35ac7192041200411";
    private static final String apiUrl = "http://api.weatherapi.com/v1/current.json";

    // method for contacting Weather API and getting weather data for given city
    public WeatherDTO getWeather(String city) {
        logger.info("Method getWeather in " + getClass().getSimpleName() + " called");
        logger.debug("Parameters: String " + city);

        String requestCall = apiUrl + "?key=" + key + "&q=" + city + "&aqi=no";


        try {
            JsonNode response = new RestTemplate().getForObject(requestCall, JsonNode.class);
            JsonNode locationInfo = response.get("location");
            JsonNode tempInfo = response.get("current");

            String name = locationInfo.get("name").asText();
            String country = locationInfo.get("country").asText();
            String region = locationInfo.get("region").asText();
            float temp_c = tempInfo.get("temp_c").floatValue();
            float temp_f = tempInfo.get("temp_f").floatValue();

            return new WeatherDTO(name, region, country, temp_c, temp_f);
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode() == HttpStatus.BAD_REQUEST){
                logger.error("InvalidCityNameException: No matching location found.");
                throw new InvalidCityNameException("No matching location found.");
            }
            return null;
        }
    }
}
