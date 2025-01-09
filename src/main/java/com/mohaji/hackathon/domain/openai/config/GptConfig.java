package com.mohaji.hackathon.domain.openai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@ConfigurationProperties(prefix = "openai")
public class GptConfig {
    private String model;
    private String fineTunedModelId;
    private String secretKey;

    // Getters and Setters
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFineTunedModelId() {
        return fineTunedModelId;
    }

    public void setFineTunedModelId(String fineTunedModelId) {
        this.fineTunedModelId = fineTunedModelId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
