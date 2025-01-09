package com.mohaji.hackathon.domain.openai.service;

import com.mohaji.hackathon.domain.openai.config.GptConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.client.RestClient;

@Service
public class OpenAIModelService {

    private final RestClient restClient;



    public OpenAIModelService(RestClient.Builder builder, GptConfig gptConfig) {
        this.restClient = builder
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + gptConfig.getSecretKey())
                .build();
    }

    public String generateResponse(String fineTunedModelId, String prompt) {
        String requestBody = """
            {
                "model": "%s",
                "prompt": "%s",
                "max_tokens": 100
            }
            """.formatted(fineTunedModelId, prompt);

        String response = restClient.post()
                .uri("/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .body(String.class);

        return response; // GPT 응답 반환
    }
}