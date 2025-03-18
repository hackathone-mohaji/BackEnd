package com.mohaji.hackathon.domain.openai.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohaji.hackathon.common.error.enums.ErrorCode;
import com.mohaji.hackathon.common.error.exception.BusinessException;
import com.mohaji.hackathon.domain.openai.config.GptConfig;
import com.mohaji.hackathon.domain.wear.dto.WearDTO;
import com.mohaji.hackathon.domain.wear.enums.Att.Category;
import com.mohaji.hackathon.domain.wear.enums.Att.Color;
import com.mohaji.hackathon.domain.wear.enums.Att.Item;
import com.mohaji.hackathon.domain.wear.enums.Att.Print;
import java.util.ArrayList;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class GPTService {


    private final RestClient restClient;
    private final GptConfig gptConfig;
    private final ObjectMapper objectMapper;


    public GPTService(RestClient.Builder builder, GptConfig gptConfig, ObjectMapper objectMapper) {
        this.restClient = builder
                .baseUrl("https://api.openai.com/v1/chat/completions")
//                .baseUrl("https://api.openai.com/v1")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + gptConfig.getSecretKey())
                .build();
        this.gptConfig = gptConfig;
        this.objectMapper = objectMapper;
    }


    public String getRecommendationFromGPT(String prompt) {
        // 요청 바디 생성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", gptConfig.getModel());
        requestBody.put("messages", List.of(Map.of("role", "user", "content", prompt)));

        // 요청 전송 및 응답 받기
        String response = restClient.post()
                .body(requestBody)
                .retrieve()
                .body(String.class);


        try {
            // 1. 원본 응답을 JsonNode로 파싱
            JsonNode rootNode = objectMapper.readTree(response.toString());
            log.info("RootNode: {}", rootNode.toString());

            // 2. content 값 추출
            String content = rootNode
                    .get("choices")
                    .get(0)
                    .get("message")
                    .get("content")
                    .asText();

            return content;

        } catch (Exception e) {
            throw new RuntimeException("JSON 데이터 처리 중 오류 발생", e);
        }
    }

    public static String generatePrompt(int imageCount) {
        String categories = Category.toFormattedString();
        String colors = Color.toFormattedString();
        String items = Item.toFormattedString();
        String prints = Print.toFormattedString();

        return String.format(
            "Based on the %d provided images, please determine the values corresponding to each category and organize them into a JSON format. " +
                "**Ensure that the JSON format is valid and properly structured. Provide the JSON content without the enclosing json, " +
                "without newline characters (\\n), and also remove all extra spaces and whitespace.** " +
                "The results should be structured as an array with %d objects, each containing the following fields:\n\n" +
                "1. **Category**: \n%s\n\n" +
                "2. **Color**: \n%s\n\n" +
                "3. **Item**: \n%s\n\n" +
                "4. **Print**: \n%s\n",
            imageCount, imageCount, categories, colors, items, prints
        );
    }

    public List<ResponseWear> analyzeImages(List<MultipartFile> images) throws IOException {
        if (images == null || images.isEmpty()) {
            throw new IllegalArgumentException("No images provided");
        }

        String prompt = generatePrompt(images.size());

        List<Map<String, Object>> contentList = new ArrayList<>();
        contentList.add(Map.of("type", "text", "text", prompt));

        List<MultipartFile> imageList = new ArrayList<>(images); // ✅ 원본 이미지 리스트 저장

        for (MultipartFile image : images) {
            byte[] fileBytes = image.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(fileBytes);
            String dataUriImage = "data:image/jpeg;base64," + base64Image;

            contentList.add(Map.of("type", "image_url", "image_url", Map.of("url", dataUriImage)));
        }

        Map<String, Object> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", contentList);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", gptConfig.getModel());
        requestBody.put("messages", List.of(userMessage));

        // 1. REST API 요청 전송
        String response = restClient.post()
            .body(requestBody)
            .retrieve()
            .body(String.class);

        try {
            // 1. 원본 응답을 JsonNode로 파싱
            JsonNode rootNode = objectMapper.readTree(response);

            // 2. content 값 추출
            String content = rootNode
                .get("choices")
                .get(0)
                .get("message")
                .get("content")
                .asText();

            // 3. JSON 배열로 변환하여 리스트로 매핑
            List<WearDTO> wearDtoList = Arrays.asList(objectMapper.readValue(content, WearDTO[].class));

            if (wearDtoList.size() != images.size()) {
                throw new BusinessException(ErrorCode.WRONG_IMAGE);
            }

            // ✅ 원본 이미지와 WearDTO를 함께 반환
            List<ResponseWear> responses = new ArrayList<>();
            for (int i = 0; i < images.size(); i++) {
                responses.add(new ResponseWear(wearDtoList.get(i), imageList.get(i)));
            }

            log.info("Mapped WearDTO List: {}", responses);
            return responses;
        } catch (JsonParseException e) {
            throw new BusinessException(ErrorCode.WRONG_IMAGE);
        }
    }


    public record ResponseWear (WearDTO wearDTO, MultipartFile multipartFile){}
}

