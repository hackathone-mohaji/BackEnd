package com.mohaji.hackathon.domain.wear.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohaji.hackathon.domain.Image.entity.Image;
import com.mohaji.hackathon.domain.Image.util.ImageUtil;
import com.mohaji.hackathon.domain.auth.entity.Account;
import com.mohaji.hackathon.domain.auth.repository.AccountRepository;
import com.mohaji.hackathon.domain.openai.service.GPTService;
import com.mohaji.hackathon.domain.wear.dto.GPTRecommendationResponseDTO;
import com.mohaji.hackathon.domain.wear.entity.Combination;
import com.mohaji.hackathon.domain.wear.entity.CombinationWear;
import com.mohaji.hackathon.domain.wear.entity.Wear;
import com.mohaji.hackathon.domain.wear.enums.Style;
import com.mohaji.hackathon.domain.wear.enums.Weather;
import com.mohaji.hackathon.domain.wear.repository.CombinationRepository;
import com.mohaji.hackathon.domain.wear.repository.CombinationWearRepository;
import com.mohaji.hackathon.domain.wear.repository.WearRepository;
import com.mohaji.hackathon.domain.weather.dto.WeatherInfoDTO;
import com.mohaji.hackathon.domain.weather.service.WeatherService;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutfitRecommendationService {

  private final WeatherService weatherService;
  private final WearRepository wearRepository;
  private final GPTService gptService;
  private final ImageUtil imageUtil;
  private final CombinationRepository combinationRepository;
  private final CombinationWearRepository combinationWearRepository;
  private final AccountRepository accountRepository;


  @Async("customTaskExecutor")
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void recommendOutfit(int count, Long accountId) {
    System.out.println("-------------비동기 매서드 실행 ------------ ");

    // 2. 디비에서 옷 정보 불러오기
    List<Wear> userWears = wearRepository.findAllByAccountId(accountId);

    // 3. 날씨 정보 불러오기
    WeatherInfoDTO weatherInfo = weatherService.getWeatherForecast(60, 127);

    // 4. 질문 조건들 작성
    String prompt = createPrompt(userWears, weatherInfo, count);

    // 5. 프롬프트 작성 후 질문
    String gptResponse = gptService.getRecommendationFromGPT(prompt);

    // 6. 응답 값 저장
    parseGPTResponse(gptResponse, userWears, accountId, count);

  }


  private String createPrompt(List<Wear> userWears, WeatherInfoDTO weatherInfo, int count) {
    StringBuilder prompt = new StringBuilder(
        "Here is the user's wardrobe data, current weather information, and the available styles. Please generate random outfit combinations based on the user's wardrobe, and refine them based on the provided style and weather information.\n\n");

    // User's wardrobe data
    prompt.append("1. User's wardrobe data:\n");
    for (Wear wear : userWears) {
      prompt.append(String.format("- [ID: %d, Category: %s, Item: %s, Color: %s, Print: %s]\n",
          wear.getId(), wear.getCategory(), wear.getItem(), wear.getColor(), wear.getPrints()));
    }

    // Weather information
    prompt.append("\n2. Weather information:\n");
    try {
      double temperature = parseTemperature(weatherInfo.getTemperature());
      prompt.append(String.format("- Temperature: %.1f°C\n", temperature));
      prompt.append(String.format("- Season: %s\n", determineSeason(temperature)));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          "Invalid temperature format: " + weatherInfo.getTemperature(), e);
    }
    prompt.append(String.format("- Precipitation: %s\n", weatherInfo.getPrecipitation()));
    prompt.append(String.format("- Sky condition: %s\n", weatherInfo.getSkyCondition()));
    prompt.append(String.format("- Wind speed: %s\n", weatherInfo.getWindSpeed()));
    if (!"false".equalsIgnoreCase(weatherInfo.getWindWarning())) {
      prompt.append("- Special condition: Strong wind\n");
    }

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
    prompt.append(
        String.format("- Generate %d random outfit combinations from the user's wardrobe.\n",
            count));
    prompt.append("- No new or hypothetical items should be included in the recommendations.\n");
    prompt.append(
        "- Refine the combinations based on the provided styles and weather information.\n");
    prompt.append(
        "- Include the appropriate season (e.g., SPRING, SUMMER, AUTUMN, WINTER) based on the weather information.\n");
    prompt.append(
        "- If there are multiple colors, please choose only one dominant color as the representative. Avoid including multiple colors. \n");
    prompt.append(
        "- Each recommended outfit item must include the Entity ID, Category, Item, Color, Print, Style, and Season.\n");
    prompt.append(
        "- Provide a detailed explanation for the recommendation in JSON format as a separate field.\n");
    prompt.append("- Please answer the reason why you chose this combination in Korean\n");
    prompt.append(
        "- **Ensure that the JSON format is valid and properly structured.Please provide the JSON content without the enclosing json\\\\n at the beginning, \\\\n at the end, and also remove all newline characters (\\\\n) along with extra spaces and whitespace.\n");
    prompt.append("- The output must be a JSON array of exactly ")
        .append(count)
        .append(
            " objects, each following this structure and ensuring that the items are ordered as top, bottom, outerwear, and shoes (if included):\n");
    prompt.append("[\n");
    prompt.append("  {\n");
    prompt.append("    \"outfit\": [\n");
    prompt.append(
        "      {\"id\": [Entity ID], \"category\": \"[Category]\", \"item\": \"[Item]\", \"color\": \"[Color]\", \"print\": \"[Print]\", \"style\": \"[Style]\", \"season\": \"[Season]\"},\n");
    prompt.append("      ...\n");
    prompt.append("    ],\n");
    prompt.append(
        "    \"explanation\": \"[Detailed explanation for the recommendation in Korean]\"\n");
    prompt.append("  },\n");
    prompt.append("  ...\n");
    prompt.append("]\n");
    return prompt.toString();
  }


  private double parseTemperature(String temperatureString) {
    // Removes "°C" and trims whitespace
    String cleanTemperature = temperatureString.replace("°C", "").trim();
    return Double.parseDouble(cleanTemperature);
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

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void parseGPTResponse(String response, List<Wear> userWears, Long accountId, int count) {
    if (response == null || response.isEmpty()) {
      throw new IllegalArgumentException("GPT response is empty or null");
    }

    ObjectMapper objectMapper = new ObjectMapper();
    try {

      String cleanedResponse = cleanJsonString(response);
      if (!isValidJson(cleanedResponse)) {
        throw new IllegalArgumentException("Invalid JSON format received from GPT");
      }
      // JSON 배열을 List<GPTRecommendationResponseDTO> 로 파싱
      List<GPTRecommendationResponseDTO> dtoList = objectMapper.readValue(
          cleanedResponse, new TypeReference<List<GPTRecommendationResponseDTO>>() {
          }
      );

      Account account = accountRepository.findById(accountId)
          .orElseThrow(() -> new IllegalArgumentException("Account not found"));

      int saveCount = Math.min(count, dtoList.size());

      for (int i = 0; i < saveCount; i++) {
        GPTRecommendationResponseDTO dto = dtoList.get(i);

        // 1️⃣ 새로운 Combination 엔티티 생성
        Combination combination = new Combination();
        combination.setAccount(account);
        combination.setStyle(getValidStyle(dto.getOutfit().get(0).getStyle()));
        combination.setWeather(
            Weather.valueOf(String.valueOf(dto.getOutfit().get(0).getSeason()))); // 첫 번째 아이템의 시즌 사용
        combination.setReason(dto.getExplanation());
        combination.setBookmarked(false);
        combination.setViewed(false);

        // Combination 저장
        log.info("save combination: {}", combination.getId()+","+combination.getReason());
        combination = combinationRepository.save(combination);

        // 2️⃣ 해당 코디의 모든 옷 정보를 CombinationWear로 저장
        for (GPTRecommendationResponseDTO.OutfitItemDTO item : dto.getOutfit()) {
          // 사용자 옷장에서 ID 매칭하여 Wear 찾기
          Wear matchedWear = userWears.stream()
              .filter(wear -> wear.getId().equals(item.getId()))
              .findFirst()
              .orElse(null);  // 기존 throw에서 null로 변경



          if (matchedWear == null) {
            log.warn("Wear with ID {} not found, skipping this item", item.getId());
            continue;  // 해당 아이템 스킵
          }

          // CombinationWear 엔티티 생성 및 저장
          CombinationWear combinationWear = new CombinationWear();
          combinationWear.setCombination(combination);
          combinationWear.setWear(matchedWear);
          combinationWear.setAccount(account);
          combinationWearRepository.save(combinationWear);


        }
      }

    } catch (Exception e) {
      log.error(e.getMessage());
      throw new IllegalArgumentException("Failed to parse GPT response", e);
    }
  }


  private String cleanJsonString(String json) {
    if (json.startsWith("```json")) {
      json = json.replace("```json", "").trim(); // 불필요한 JSON 코드 블록 제거
    }
    if (json.endsWith("```")) {
      json = json.replace("```", "").trim();
    }
    return json;
  }

  private Style getValidStyle(String styleName) {
    try {
      return Style.valueOf(styleName.toUpperCase()); // 존재하는 값이면 반환
    } catch (IllegalArgumentException e) {
      log.warn("❗️ 유효하지 않은 스타일 값: {} → 기본값 적용", styleName);
      return Style.CASUAL;  // ❗ 기본값을 추가하여 예외 방지
    }
  }

  private boolean isValidJson(String json) {
    try {
      new ObjectMapper().readTree(json);
      return true;
    } catch (Exception e) {
      log.error("Invalid JSON format: {}", json, e);
      return false;
    }
  }



}