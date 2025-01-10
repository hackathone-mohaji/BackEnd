package com.mohaji.hackathon.domain.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherInfoDTO {
    private String precipitation;
    private String rainfall;
    private String skyCondition;
    private String windWarning;
    private String temperature;
    private String windSpeed;
    private String strongSunlight;
}