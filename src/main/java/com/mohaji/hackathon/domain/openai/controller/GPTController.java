package com.mohaji.hackathon.domain.openai.controller;

import com.mohaji.hackathon.domain.openai.dto.ChatRequestDTO;
import com.mohaji.hackathon.domain.openai.service.FineTuneExample;
import com.mohaji.hackathon.domain.openai.service.GPTService;
import com.mohaji.hackathon.domain.wear.dto.WearDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class GPTController {

    private final GPTService gptService;
    private final FineTuneExample fineTuneExample;

    @PostMapping("/chat")
    public ResponseEntity<String> chatWithGPT(@RequestBody ChatRequestDTO request) {
        String prompt = request.getPrompt();
        String response = gptService.getGPTResponse(prompt);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/image_upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WearDTO> analyzeImage(@RequestPart MultipartFile image) throws IOException {

        WearDTO response = gptService.analyzeImage(image);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> fineTuneModel(@RequestPart MultipartFile file) {
        try {
            String fineTuningId = fineTuneExample.runFineTuning(file);
            return ResponseEntity.ok("Fine-Tuning ID is :" + fineTuningId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error occurred: " + e.getMessage());
        }
    }
}