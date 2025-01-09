package com.mohaji.hackathon.domain.wear.service;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.mohaji.hackathon.domain.Image.util.ClippingBgUtil;
import com.mohaji.hackathon.domain.wear.dto.CompletionResponseDTO;
import com.mohaji.hackathon.domain.wear.dto.WearDTO;
import com.mohaji.hackathon.domain.wear.entity.Wear;
import com.mohaji.hackathon.domain.wear.repository.WearRepository;
import com.mohaji.hackathon.domain.openai.service.GPTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class WearService {

    private final WearRepository wearRepository;
    private final GPTService gptService;
    private final ClippingBgUtil clippingBgUtil;
    private final ObjectMapper objectMapper;


//        public Wear saveImageAndAnalyzeDate(MultipartFile imageFile) throws IOException {
//        try {
//            // 1. 이미지 누끼 땀
////            MultipartFile removeBackground = clippingBgUtil.removeBackground(imageFile);
//            // 2. gpt 한테 분석
//            String json = gptService.analyzeImage(imageFile);
//
//            // 3. 분석 결과 (wear) 저장
//            log.info("Json"+json);
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(json);
//            String content = rootNode.get("choices").get(0).get("message").get("content").asText();
//
//
//            WearDTO wearDto = objectMapper.readValue(content, WearDTO.class);
//            System.out.println("wearDto = " + wearDto);
//            Wear wear = Wear.builder()
//                    .color(wearDto.getColor())
//                    .category(wearDto.getCategory())
//                    .item(wearDto.getItem())
//                    .prints(wearDto.getPrint())
//                    .build();
//
//            // 4. 이미지 저장
//            // 이미지랑 한꺼번에 저장
//            return wearRepository.save(wear);
//        } catch (Exception e) {
//            throw new RuntimeException("JSON 데이터 매핑 실패", e);
//        }
//    }

    public void saveImageAndAnalyzeDate(MultipartFile imageFile) throws IOException {
        try {
            // 1. GPT에서 분석 결과 받기
            WearDTO wearDto = gptService.processResponse(gptService.analyzeImage(imageFile));
            log.info("Mapped WearDTO: {}", wearDto);

            // 2. Wear 엔티티 생성
            Wear wear = Wear.builder()
                    .color(wearDto.getColor())
                    .category(wearDto.getCategory())
                    .item(wearDto.getItem())
                    .prints(wearDto.getPrint())
                    .build();

            // 3. Wear 엔티티 저장 및 반환
             wearRepository.save(wear);
        } catch (Exception e) {
            log.error("Error while mapping JSON to WearDTO", e);
            throw new RuntimeException("JSON 데이터 매핑 실패", e);
        }
    }

}






