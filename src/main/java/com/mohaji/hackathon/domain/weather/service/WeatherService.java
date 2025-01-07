package com.mohaji.hackathon.domain.weather.service;

import com.mohaji.hackathon.domain.common.error.error.enums.ErrorCode;
import com.mohaji.hackathon.domain.common.error.error.exception.BusinessException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private String apiKey;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getWeatherForecast(int nx, int ny) {
        try {
            URI uri = new URI(buildUrl(nx, ny));

            String response = restTemplate.getForObject(uri, String.class);
            JSONObject jsonObject = new JSONObject(response);
            JSONArray items = jsonObject.getJSONObject("response")
                    .getJSONObject("body")
                    .getJSONObject("items")
                    .getJSONArray("item");

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                String category = item.getString("category");

                if ("TMP".equals(category)) {
                    String fcstValue = item.getString("fcstValue");
                    return "현재 기온: " + fcstValue + "°C";
                }
            }

            throw new BusinessException(ErrorCode.WEATHER_INFORMATION_NOT_FOUND);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid URI syntax", e);
        }
    }

    private String buildUrl(int nx, int ny) {
        return "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"
                + "?serviceKey=" + apiKey
                + "&pageNo=1"
                + "&numOfRows=10"
                + "&dataType=JSON"
                + "&base_date=" + getBaseDate()
                + "&base_time=" + getBaseTime()
                + "&nx=" + nx
                + "&ny=" + ny;
    }

    private static String getBaseDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    private String getBaseTime() {
        int hour = LocalDateTime.now().getHour();
        if (hour < 2) return "2300";
        else if (hour < 5) return "0200";
        else if (hour < 8) return "0500";
        else if (hour < 11) return "0800";
        else if (hour < 14) return "1100";
        else if (hour < 17) return "1400";
        else if (hour < 20) return "1700";
        else if (hour < 23) return "2000";
        else return "2300";
    }

}