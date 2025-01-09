package com.mohaji.hackathon.domain.openai.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GPTService {


    private final RestClient restClient;


    @Value("${openai.secret-key}")
    private String secretKey;

    @Value("${openai.fine-tuned-model-id}")
    private String fineTunedModelId;

    @Value("${openai.model}")
    private String model;



    public GPTService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + secretKey)
                .build();
    }


    public String getGPTResponse(String prompt) {
        // 요청 바디 생성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", List.of(Map.of("role", "user", "content", prompt)));

        // 요청 전송 및 응답 받기
        String response = restClient.post()
                .body(requestBody)
                .retrieve()
                .body(String.class);

        return response;
    }

    public String analyzeImage(MultipartFile image) throws IOException {
        String prompt = "Please analyze the attached clothes photo with the value of the enum you learned in advance. Please select one for each enum";

        // 이미지 파일을 Base64로 인코딩
        byte[] fileBytes = image.getBytes();
        String base64Image = Base64.getEncoder().encodeToString(fileBytes);
        String dataUriImage = "data:image/jpeg;base64," + base64Image;

        // 메시지 내용 생성
        Map<String, Object> textContent = new HashMap<>();
        textContent.put("type", "text");
        textContent.put("text", prompt);

        Map<String, Object> imageContent = new HashMap<>();
        imageContent.put("type", "image_url");
        imageContent.put("image_url", Map.of("url", dataUriImage));

        // 사용자 메시지 생성
        Map<String, Object> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", List.of(textContent, imageContent));

        // 요청 본문 구성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", fineTunedModelId); // 파인튜닝된 모델 ID 사용
        requestBody.put("messages", List.of(userMessage));

        return restClient.post()
                .uri("/chat/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .body(String.class);
    }


}
