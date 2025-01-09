package com.mohaji.hackathon.domain.weather.controller;


import com.mohaji.hackathon.domain.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;



    @GetMapping("/weather")
    public Map<String, Object> getWeather(

            @RequestParam int nx,
            @RequestParam int ny) {
        return weatherService.getWeatherForecast(nx, ny);
    }
}
