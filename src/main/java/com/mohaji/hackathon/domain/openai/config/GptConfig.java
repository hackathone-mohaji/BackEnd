package com.mohaji.hackathon.domain.openai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class GptConfig {
    @Value("${openai.secret-key}")
    private String secretKey;

    @Value("${openai.model}")
    private String model;


    public String getSecretKey() {
        return secretKey;
    }

    public String getModel() {
        return model;
    }
}
