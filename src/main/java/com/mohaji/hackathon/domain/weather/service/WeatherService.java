//package com.mohaji.hackathon.domain.weather.service;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//public class WeatherService {
//
//    private final RestTemplate restTemplate;
//
//    @Value("${weather.api.key}")
//    private String apiKey;
//
//    public WeatherService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public Map<String, Object> getWeatherForecast(int nx, int ny) {
//        try {
//            URI uri = new URI(buildUrl(nx, ny));
//            String response = restTemplate.getForObject(uri, String.class);
//
//            JSONObject jsonObject = new JSONObject(response);
//            JSONArray items = jsonObject.getJSONObject("response")
//                    .getJSONObject("body")
//                    .getJSONObject("items")
//                    .getJSONArray("item");
//
//            Map<String, Object> weatherDetails = new HashMap<>();
//
//            for (int i = 0; i < items.length(); i++) {
//                JSONObject item = items.getJSONObject(i);
//                String category = item.getString("category");
//
//                switch (category) {
//                    case "TMP":
//                        weatherDetails.put("temperature", item.optString("fcstValue", "N/A") + "°C");
//                        break;
//                    case "PTY":
//                        weatherDetails.put("precipitation", getPrecipitationDescription(item.optString("fcstValue", "0")));
//                        break;
//                    case "RN1":
//                        weatherDetails.put("rainfall", item.optString("fcstValue", "0") + "mm");
//                        break;
//                    case "WSD":
//                        double windSpeed = Double.parseDouble(item.optString("fcstValue", "0"));
//                        weatherDetails.put("wind_speed", windSpeed + "m/s");
//                        weatherDetails.put("wind_warning", windSpeed >= 10);
//                        break;
//                    case "SKY":
//                        weatherDetails.put("sky_condition", getSkyCondition(item.optString("fcstValue", "4")));
//                        weatherDetails.put("strong_sunlight", "1".equals(item.optString("fcstValue", "4")));
//                        break;
//                    default:
//                        break;
//                }
//            }
//
//            // Add default values for missing keys
//            ensureDefaultValues(weatherDetails);
//
//            return weatherDetails;
//
//        } catch (Exception e) {
//            throw new RuntimeException("Error fetching weather details", e);
//        }
//    }
//
//    private void ensureDefaultValues(Map<String, Object> weatherDetails) {
//        weatherDetails.putIfAbsent("temperature", "N/A°C"); // 기온
//        weatherDetails.putIfAbsent("precipitation", "없음"); // 강수 형태
//        weatherDetails.putIfAbsent("rainfall", "0mm"); // 강수량
//        weatherDetails.putIfAbsent("wind_speed", "0.0m/s"); //풍속
//        weatherDetails.putIfAbsent("wind_warning", false); // 강풍 경고 여부
//        weatherDetails.putIfAbsent("sky_condition", "알 수 없음"); //하늘 상태
//        weatherDetails.putIfAbsent("strong_sunlight", false); // 강한 햇빛 여부
//    }
//
//    private String buildUrl(int nx, int ny) {
//        return "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"
//                + "?serviceKey=" + apiKey
//                + "&pageNo=1"
//                + "&numOfRows=10"
//                + "&dataType=JSON"
//                + "&base_date=" + getBaseDate()
//                + "&base_time=" + getBaseTime()
//                + "&nx=" + nx
//                + "&ny=" + ny;
//    }
//
//    private static String getBaseDate() {
//        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//    }
//
//    private String getBaseTime() {
//        int hour = LocalDateTime.now().getHour();
//        if (hour < 2) return "2300";
//        else if (hour < 5) return "0200";
//        else if (hour < 8) return "0500";
//        else if (hour < 11) return "0800";
//        else if (hour < 14) return "1100";
//        else if (hour < 17) return "1400";
//        else if (hour < 20) return "1700";
//        else if (hour < 23) return "2000";
//        else return "2300";
//    }
//
//    private String getPrecipitationDescription(String fcstValue) {
//        switch (fcstValue) {
//            case "0":
//                return "없음";
//            case "1":
//                return "비";
//            case "2":
//                return "비/눈";
//            case "3":
//                return "눈";
//            case "4":
//                return "소나기";
//            default:
//                return "알 수 없음";
//        }
//    }
//
//    private String getSkyCondition(String fcstValue) {
//        switch (fcstValue) {
//            case "1":
//                return "맑음";
//            case "3":
//                return "구름 많음";
//            case "4":
//                return "흐림";
//            default:
//                return "알 수 없음";
//        }
//    }
//
////    public String formatWeatherDetails(Map<String, Object> weatherDetails) {
////        return weatherDetails.entrySet().stream()
////                .map(entry -> entry.getKey() + ": " + entry.getValue())
////                .collect(Collectors.joining("\n"));
////    }
//}

package com.mohaji.hackathon.domain.weather.service;

import com.mohaji.hackathon.domain.weather.dto.WeatherInfoDTO;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class WeatherService {

    private final RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private String apiKey;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherInfoDTO getWeatherForecast(int nx, int ny) {
        try {
            URI uri = new URI(buildUrl(nx, ny));
            String response = restTemplate.getForObject(uri, String.class);

            JSONObject jsonObject = new JSONObject(response);
            JSONArray items = jsonObject.getJSONObject("response")
                    .getJSONObject("body")
                    .getJSONObject("items")
                    .getJSONArray("item");

            WeatherInfoDTO.WeatherInfoDTOBuilder builder = WeatherInfoDTO.builder();

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                String category = item.getString("category");

                switch (category) {
                    case "TMP":
                        builder.temperature(item.optString("fcstValue", "N/A") + "°C");
                        break;
                    case "PTY":
                        builder.precipitation(getPrecipitationDescription(item.optString("fcstValue", "0")));
                        break;
                    case "RN1":
                        builder.rainfall(item.optString("fcstValue", "0") + "mm");
                        break;
                    case "WSD":
                        double windSpeed = Double.parseDouble(item.optString("fcstValue", "0"));
                        builder.windSpeed(windSpeed + "m/s");
                        builder.windWarning(windSpeed >= 10 ? "true" : "false");
                        break;
                    case "SKY":
                        builder.skyCondition(getSkyCondition(item.optString("fcstValue", "4")));
                        builder.strongSunlight("1".equals(item.optString("fcstValue", "4")) ? "true" : "false");
                        break;
                    default:
                        break;
                }
            }

            // Set default values for missing fields
            return setDefaultValues(builder).build();

        } catch (Exception e) {
            throw new RuntimeException("Error fetching weather details", e);
        }
    }

    private WeatherInfoDTO.WeatherInfoDTOBuilder setDefaultValues(WeatherInfoDTO.WeatherInfoDTOBuilder builder) {
        return builder
                .temperature(builder.build().getTemperature() == null ? "N/A°C" : builder.build().getTemperature())
                .precipitation(builder.build().getPrecipitation() == null ? "없음" : builder.build().getPrecipitation())
                .rainfall(builder.build().getRainfall() == null ? "0mm" : builder.build().getRainfall())
                .windSpeed(builder.build().getWindSpeed() == null ? "0.0m/s" : builder.build().getWindSpeed())
                .windWarning(builder.build().getWindWarning() == null ? "false" : builder.build().getWindWarning())
                .skyCondition(builder.build().getSkyCondition() == null ? "알 수 없음" : builder.build().getSkyCondition())
                .strongSunlight(builder.build().getStrongSunlight() == null ? "false" : builder.build().getStrongSunlight());
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

    private String getPrecipitationDescription(String fcstValue) {
        switch (fcstValue) {
            case "0":
                return "없음";
            case "1":
                return "비";
            case "2":
                return "비/눈";
            case "3":
                return "눈";
            case "4":
                return "소나기";
            default:
                return "알 수 없음";
        }
    }

    private String getSkyCondition(String fcstValue) {
        switch (fcstValue) {
            case "1":
                return "맑음";
            case "3":
                return "구름 많음";
            case "4":
                return "흐림";
            default:
                return "알 수 없음";
        }
    }
}