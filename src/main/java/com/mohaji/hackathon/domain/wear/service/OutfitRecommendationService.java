package com.mohaji.hackathon.domain.wear.service;

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
        List<Wear> userWears = wearRepository.findByAccount(account);

        // 3. 날씨 정보 불러오기
        WeatherInfoDTO weatherInfo = weatherService.getWeatherForecast(60, 127);

        // 4. 질문 조건들 작성
        String prompt = createPrompt(userWears, weatherInfo);

        // 5. 프롬프트 작성 후 질문
        String gptResponse = gptService.getRecommendationFromGPT(prompt);

        // 6. 응답 값 가공 후 리턴
        return parseGPTResponse(gptResponse, userWears);
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
        prompt.append("- Provide a detailed explanation for the recommendation.\n");

        prompt.append("\nOutput format:\n");
        prompt.append("- Top ID: [ID]\n");
        prompt.append("- Bottom ID: [ID]\n");
        prompt.append("- Outerwear ID: [ID] (optional)\n");
        prompt.append("- Reason for recommendation: [Reason]\n");

        return prompt.toString();
    }

    private GPTRecommendationResponseDTO parseGPTResponse(String response, List<Wear> userWears) {
        if (response == null || response.isEmpty()) {
            throw new IllegalArgumentException("GPT response is empty or null");
        }

        String[] lines = response.split("\n");
        Long topId = parseId(lines[0]);   // 상의 ID 파싱
        Long bottomId = parseId(lines[1]); // 하의 ID 파싱
        Long outerId = lines.length > 2 ? parseId(lines[2]) : null; // 외투 ID 파싱
        String reason = lines.length > 3 ? lines[3].replace("Reason for recommendation: ", "").trim() : "No reason provided.";

        return new GPTRecommendationResponseDTO(topId, bottomId, outerId, reason);
    }

    private Long parseId(String line) {
        try {
            // "Top ID: [1]" 또는 "Bottom ID: [2]" 형식에서 ID를 추출
            String idString = line.split(":")[1].trim().replace("[", "").replace("]", "");
            return Long.parseLong(idString);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse ID from response line: " + line, e);
        }
    }
}