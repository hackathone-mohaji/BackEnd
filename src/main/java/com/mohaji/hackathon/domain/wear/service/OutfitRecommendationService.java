package com.mohaji.hackathon.domain.wear.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohaji.hackathon.domain.Image.entity.Image;
import com.mohaji.hackathon.domain.Image.util.ImageUtil;
import com.mohaji.hackathon.domain.auth.entity.Account;
import com.mohaji.hackathon.domain.openai.service.GPTService;
import com.mohaji.hackathon.domain.wear.dto.GPTRecommendationResponseDTO;
import com.mohaji.hackathon.domain.wear.entity.Wear;
import com.mohaji.hackathon.domain.wear.enums.Style;
import com.mohaji.hackathon.domain.wear.enums.Weather;
import com.mohaji.hackathon.domain.wear.repository.WearRepository;
import com.mohaji.hackathon.domain.weather.dto.WeatherInfoDTO;
import com.mohaji.hackathon.domain.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutfitRecommendationService {
    private final WeatherService weatherService;
    private final WearRepository wearRepository;
    private final GPTService gptService;
    private final ImageUtil imageUtil;

    public GPTRecommendationResponseDTO recommendOutfit() {
        // 1. 회원 정보 불러오기
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 2. 디비에서 옷 정보 불러오기
        List<Wear> userWears = wearRepository.findAllByAccountId(account.getId());

        // 3. 날씨 정보 불러오기
        WeatherInfoDTO weatherInfo = weatherService.getWeatherForecast(60, 127);

        // 4. 질문 조건들 작성
        String prompt = createPrompt(userWears, weatherInfo);

        // 5. 프롬프트 작성 후 질문
        String gptResponse = gptService.getRecommendationFromGPT(prompt);

        // 6. 응답 값 가공 후 리턴
        return parseGPTResponse(gptResponse, userWears);

        // 7. 이미지 url 까지 첨부해서 json 작성
    }

//    private String createPrompt(List<Wear> userWears, WeatherInfoDTO weatherInfo) {
//        StringBuilder prompt = new StringBuilder("Here is the user's wardrobe data and the current weather information. Please recommend an outfit that matches the conditions.\n\n");
//
//        prompt.append("1. User's wardrobe data:\n");
//
//        for (Wear wear : userWears) {
//            prompt.append(String.format("- [ID: %d, Category: %s, Item: %s, Color: %s, Print: %s]\n",
//                    wear.getId(), wear.getCategory(), wear.getItem(), wear.getColor(), wear.getPrints()));
//        }
//
//        prompt.append("\n2. Weather information:\n");
//        prompt.append(String.format("- Temperature: %s°C\n", weatherInfo.getTemperature()));
//        prompt.append(String.format("- Precipitation: %s\n", weatherInfo.getPrecipitation()));
//        prompt.append(String.format("- Sky condition: %s\n", weatherInfo.getSkyCondition()));
//        prompt.append(String.format("- Wind speed: %s\n", weatherInfo.getWindSpeed()));
//        if (!"false".equalsIgnoreCase(weatherInfo.getWindWarning())) {
//            prompt.append("- Special condition: Strong wind\n");
//        }
//
//        prompt.append("\nRecommendation criteria:\n");
//        prompt.append("- The recommended outfit must be selected from the user's wardrobe data.\n");
//        prompt.append("- At least one top and one bottom, or a dress, must be included.\n");
//        prompt.append("- The outfit should match the temperature and weather conditions.\n");
//        prompt.append("- Weather conditions such as heatwaves, heavy rain, and snowstorms must be considered.\n");
//        prompt.append("- **Ensure the JSON format is valid and properly structured**.\n");
//        prompt.append("- Each recommended outfit item must include the Entity ID, Category, Item, Color, and Print.\n");
//        prompt.append("- Provide a detailed explanation for the recommendation in JSON format as a separate field.\n");
//        prompt.append("- Please provide the JSON content without the enclosing json\\\\n at the beginning, \\\\n at the end, and also remove all newline characters (\\\\n) along with extra spaces and whitespace.\n");
//        prompt.append("- The output must follow this exact JSON structure:\n");
//        prompt.append("- The output must follow this exact JSON structure, ensuring that the items are ordered as top, bottom, and outerwear (if included):\n");
//        prompt.append("{\n");
//        prompt.append("  \"outfit\": [\n");
//        prompt.append("    {\"id\": [Entity ID], \"category\": \"[Category]\", \"item\": \"[Item]\", \"color\": \"[Color]\", \"print\": \"[Print]\"},\n");
//        prompt.append("    {\"id\": [Entity ID], \"category\": \"[Category]\", \"item\": \"[Item]\", \"color\": \"[Color]\", \"print\": \"[Print]\"},\n");
//        prompt.append("    ...\n");
//        prompt.append("  ],\n");
//        prompt.append("  \"explanation\": \"[Detailed explanation for the recommendation]\"\n");
//        prompt.append("}\n");
//
//        return prompt.toString();
//    }

    private String createPrompt(List<Wear> userWears, WeatherInfoDTO weatherInfo) {
        StringBuilder prompt = new StringBuilder("Here is the user's wardrobe data, current weather information, and the available styles. Please generate random outfit combinations based on the user's wardrobe, and refine them based on the provided style and weather information.\n\n");

        // User's wardrobe data
        prompt.append("1. User's wardrobe data:\n");
        for (Wear wear : userWears) {
            prompt.append(String.format("- [ID: %d, Category: %s, Item: %s, Color: %s, Print: %s]\n",
                    wear.getId(), wear.getCategory(), wear.getItem(), wear.getColor(), wear.getPrints()));
        }

        // Weather information
        prompt.append("\n2. Weather information:\n");
        prompt.append(String.format("- Temperature: %s°C\n", weatherInfo.getTemperature()));
        prompt.append(String.format("- Precipitation: %s\n", weatherInfo.getPrecipitation()));
        prompt.append(String.format("- Sky condition: %s\n", weatherInfo.getSkyCondition()));
        prompt.append(String.format("- Wind speed: %s\n", weatherInfo.getWindSpeed()));
        if (!"false".equalsIgnoreCase(weatherInfo.getWindWarning())) {
            prompt.append("- Special condition: Strong wind\n");
        }
        prompt.append(String.format("- Season: %s\n", determineSeason(Double.parseDouble(weatherInfo.getTemperature()))));

        // Style information
        prompt.append("\n3. Style information:\n");
        for (Style style : Style.values()) {
            prompt.append(String.format("- Style: %s (%s)\n", style.name(), style.getKoreanName()));
            prompt.append("  Categories: ");
            style.getCategories().forEach(category -> prompt.append(category + ", "));
            prompt.setLength(prompt.length() - 2); // Remove trailing comma
            prompt.append("\n  Items: ");
            style.getItems().forEach(item -> prompt.append(item + ", "));
            prompt.setLength(prompt.length() - 2); // Remove trailing comma
            prompt.append("\n  Colors: ");
            style.getColors().forEach(color -> prompt.append(color + ", "));
            prompt.setLength(prompt.length() - 2); // Remove trailing comma
            prompt.append("\n  Prints: ");
            style.getPrints().forEach(print -> prompt.append(print + ", "));
            prompt.setLength(prompt.length() - 2); // Remove trailing comma
            prompt.append("\n\n");
        }

        // Recommendation criteria
        prompt.append("\nRecommendation criteria:\n");
        prompt.append("- Generate random outfit combinations from the user's wardrobe.\n");
        prompt.append("- Refine the combinations based on the provided styles and weather information.\n");
        prompt.append("- Include the appropriate season (e.g., SPRING, SUMMER, AUTUMN, WINTER) based on the weather information.\n");
        prompt.append("- Each recommended outfit item must include the Entity ID, Category, Item, Color, Print, Style, and Season.\n");
        prompt.append("- Provide a detailed explanation for the recommendation in JSON format as a separate field.\n");
        prompt.append("- The output must follow this exact JSON structure, ensuring that the items are ordered as top, bottom, and outerwear (if included):\n");
        prompt.append("{\n");
        prompt.append("  \"outfit\": [\n");
        prompt.append("    {\"id\": [Entity ID], \"category\": \"[Category]\", \"item\": \"[Item]\", \"color\": \"[Color]\", \"print\": \"[Print]\", \"style\": \"[Style]\", \"season\": \"[Season]\"},\n");
        prompt.append("    ...\n");
        prompt.append("  ],\n");
        prompt.append("  \"explanation\": \"[Detailed explanation for the recommendation]\"\n");
        prompt.append("}\n");

        return prompt.toString();
    }

    private String determineSeason(double temperature) {
        if (temperature >= 28) {
            return Weather.SUMMER.name();
        } else if (temperature >= 19) {
            return Weather.SPRING.name();
        } else if (temperature >= 8) {
            return Weather.AUTUMN.name();
        } else {
            return Weather.WINTER.name();
        }
    }


    private GPTRecommendationResponseDTO parseGPTResponse(String response, List<Wear> userWears) {
        if (response == null || response.isEmpty()) {
            throw new IllegalArgumentException("GPT response is empty or null");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // JSON 응답을 DTO 객체로 변환
            GPTRecommendationResponseDTO dto = objectMapper.readValue(response, GPTRecommendationResponseDTO.class);

            for (GPTRecommendationResponseDTO.OutfitItemDTO item : dto.getOutfit()) {
                Wear matchedWear = userWears.stream()
                        .filter(wear -> wear.getId().equals(item.getId()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Wear with ID " + item.getId() + " not found in user's wardrobe"));

                if (matchedWear.getImages() != null && !matchedWear.getImages().isEmpty()) {
                    Image image = matchedWear.getImages().get(0);
                    String imageUrl = imageUtil.imageUrl(image, matchedWear);
                    item.setImageUrl(imageUrl);
                } else {
                    item.setImageUrl("No image available");
                }

            }

            return dto;
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse GPT response", e);
        }
    }
}