/*
package com.mohaji.hackathon.domain.weather.controller;


import com.mohaji.hackathon.domain.weather.dto.WeatherInfoDTO;
import com.mohaji.hackathon.domain.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class TestWeatherController {
    private final WeatherService weatherService;



    @GetMapping
    public WeatherInfoDTO getWeather(

            @RequestParam int nx,
            @RequestParam int ny) {
        return weatherService.getWeatherForecast(nx, ny);
    }
}
*/
