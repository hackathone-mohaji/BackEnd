package com.mohaji.hackathon.domain.wear.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohaji.hackathon.domain.auth.entity.Account;
import com.mohaji.hackathon.domain.openai.service.GPTService;
import com.mohaji.hackathon.domain.wear.dto.GPTRecommendationResponseDTO;
import com.mohaji.hackathon.domain.wear.dto.OutfitRecommendationDTO;
import com.mohaji.hackathon.domain.wear.entity.Wear;
import com.mohaji.hackathon.domain.wear.repository.WearRepository;
import com.mohaji.hackathon.domain.weather.dto.WeatherInfoDTO;
import com.mohaji.hackathon.domain.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OutfitRecommendationService {
    private final WeatherService weatherService;
    private final WearRepository wearRepository;
    private final GPTService gptService;

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

    private String createPrompt(List<Wear> userWears, WeatherInfoDTO weatherInfo) {
        StringBuilder prompt = new StringBuilder("Here is the user's wardrobe data and the current weather information. Please recommend an outfit that matches the conditions.\n\n");

        prompt.append("1. User's wardrobe data:\n");

        for (Wear wear : userWears) {
            prompt.append(String.format("- [ID: %d, Category: %s, Item: %s, Color: %s, Print: %s]\n",
                    wear.getId(), wear.getCategory(), wear.getItem(), wear.getColor(), wear.getPrints()));
        }

        prompt.append("\n2. Weather information:\n");
        prompt.append(String.format("- Temperature: %s°C\n", weatherInfo.getTemperature()));
        prompt.append(String.format("- Precipitation: %s\n", weatherInfo.getPrecipitation()));
        prompt.append(String.format("- Sky condition: %s\n", weatherInfo.getSkyCondition()));
        prompt.append(String.format("- Wind speed: %s\n", weatherInfo.getWindSpeed()));
        if (!"false".equalsIgnoreCase(weatherInfo.getWindWarning())) {
            prompt.append("- Special condition: Strong wind\n");
        }

        prompt.append("\nRecommendation criteria:\n");
        prompt.append("- The recommended outfit must be selected from the user's wardrobe data.\n");
        prompt.append("- At least one top and one bottom, or a dress, must be included.\n");
        prompt.append("- The outfit should match the temperature and weather conditions.\n");
        prompt.append("- Weather conditions such as heatwaves, heavy rain, and snowstorms must be considered.\n");
        prompt.append("- **Ensure the JSON format is valid and properly structured**.\n");
        prompt.append("- Each recommended outfit item must include the Entity ID, Category, Item, Color, and Print.\n");
        prompt.append("- Provide a detailed explanation for the recommendation in JSON format as a separate field.\n");
        prompt.append("- Please provide the JSON content without the enclosing json\\\\n at the beginning, \\\\n at the end, and also remove all newline characters (\\\\n) along with extra spaces and whitespace.\n");
        prompt.append("- The output must follow this exact JSON structure:\n");
        prompt.append("- The output must follow this exact JSON structure, ensuring that the items are ordered as top, bottom, and outerwear (if included):\n");
        prompt.append("{\n");
        prompt.append("  \"outfit\": [\n");
        prompt.append("    {\"id\": [Entity ID], \"category\": \"[Category]\", \"item\": \"[Item]\", \"color\": \"[Color]\", \"print\": \"[Print]\"},\n");
        prompt.append("    {\"id\": [Entity ID], \"category\": \"[Category]\", \"item\": \"[Item]\", \"color\": \"[Color]\", \"print\": \"[Print]\"},\n");
        prompt.append("    ...\n");
        prompt.append("  ],\n");
        prompt.append("  \"explanation\": \"[Detailed explanation for the recommendation]\"\n");
        prompt.append("}\n");

        return prompt.toString();
    }



    private GPTRecommendationResponseDTO parseGPTResponse(String response, List<Wear> userWears) {
        if (response == null || response.isEmpty()) {
            throw new IllegalArgumentException("GPT response is empty or null");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // JSON 응답을 DTO 객체로 변환
            GPTRecommendationResponseDTO dto = objectMapper.readValue(response, GPTRecommendationResponseDTO.class);

            // outfit 리스트를 userWears와 매핑하여 ID 기반으로 실제 Wear 엔티티를 찾음
            for (GPTRecommendationResponseDTO.OutfitItemDTO item : dto.getOutfit()) {
                Wear matchedWear = userWears.stream()
                        .filter(wear -> wear.getId().equals(item.getId()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Wear with ID " + item.getId() + " not found in user's wardrobe"));

                // 추가 작업: 매핑된 Wear 데이터를 확인하거나 필요한 처리 수행 가능
                System.out.println("Mapped Wear: " + matchedWear);
            }

            return dto;
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse GPT response", e);
        }
    }
}