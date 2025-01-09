package com.mohaji.hackathon.domain.openai.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohaji.hackathon.domain.openai.service.GPTFineTuneService;
import com.mohaji.hackathon.domain.openai.service.OpenAIModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class FineTuneExample {

    private final GPTFineTuneService fineTuneService;
    private final OpenAIModelService modelService;

    @Autowired
    public FineTuneExample(GPTFineTuneService fineTuneService, OpenAIModelService modelService) {
        this.fineTuneService = fineTuneService;
        this.modelService = modelService;
    }

    public String  runFineTuning(MultipartFile file) throws Exception {
        // 1. MultipartFile을 로컬 임시 파일로 저장
        Path tempFilePath = Files.createTempFile("fine-tune-", ".jsonl");
        Files.copy(file.getInputStream(), tempFilePath, StandardCopyOption.REPLACE_EXISTING);

        // 2. 데이터 업로드
        String fileId = fineTuneService.uploadFineTuneData(tempFilePath.toString());
        System.out.println("Uploaded File ID: " + fileId);

        // 3. 파인튜닝 작업 시작
        String fineTuneJob = fineTuneService.startFineTune(fileId);
        System.out.println("Fine-tuning started: " + fineTuneJob);

        // 4. 작업 상태 확인 (필요시 추가 작업)
        String fineTuneStatus = fineTuneService.checkFineTuneStatus(fineTuneJob);
        System.out.println("Fine-tuning status: " + fineTuneStatus);

        return getFineTunedModelId(fineTuneJob);
    }
    public String getFineTunedModelId(String fineTuneJobId) {
        String statusResponse = fineTuneService.checkFineTuneStatus(fineTuneJobId);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode root = objectMapper.readTree(statusResponse);
            String fineTunedModelId = root.path("fine_tuned_model").asText();
            return fineTunedModelId;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse fine-tuning status response", e);
        }
    }
}