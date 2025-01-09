package com.mohaji.hackathon.domain.wear.service;


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


    //    public Wear saveImageAndAnalyzeDate(MultipartFile imageFile) throws IOException {
//        try {
//            // 1. 이미지 누끼 땀
//            MultipartFile removeBackground = clippingBgUtil.removeBackground(imageFile);
//            // 2. gpt 한테 분석
//            String json = gptService.analyzeImage(removeBackground);
//
//            // 3. 분석 결과 (wear) 저장
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(json);
//            String content = rootNode.get("choices").get(0).get("message").get("content").asText();
//            content = content.replaceAll("```json", "").replaceAll("```", "").trim();
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



    public Wear saveImageAndAnalyzeDate(MultipartFile imageFile) throws IOException {
//        try {
        // 1. 이미지 배경 제거
//        MultipartFile removeBackground = clippingBgUtil.removeBackground(imageFile);

        // 2. GPT에서 분석 결과 받기
        String json = gptService.analyzeImage(imageFile);

        log.info("Received JSON from GPT: {}", json); // 디버깅을 위한 로그 추가

        // 2. content 필드 내부 JSON 추출
        String regex = "```json\\n\\{.*?\\}\\n```";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(json);

        String extractedContent;
        if (matcher.find()) {
            extractedContent = matcher.group();
            // "```json\n"와 "\n```" 제거
            extractedContent = extractedContent.replaceAll("```json\\n|\\n```", "");
            log.info("Extracted Content: {}", extractedContent); // 디버깅을 위한 로그 추가
        } else {
            log.error("No valid JSON content found in the response");
            throw new RuntimeException("Failed to extract JSON content from GPT response");
        }

        // 3. JSON 파싱 및 WearDTO로 변환
        WearDTO wearDto = objectMapper.readValue(extractedContent, WearDTO.class);
        log.info("Parsed WearDTO: {}", wearDto);

        // 4. Wear 엔티티 생성
        Wear wear = Wear.builder()
                .color(wearDto.getColor())
                .category(wearDto.getCategory())
                .item(wearDto.getItem())
                .prints(wearDto.getPrint())
                .build();

        // 5. 엔티티 저장 및 반환
        return wearRepository.save(wear);

//        } catch (IOException e) {
//            log.error("이미지 처리 중 오류 발생", e);
//            throw new RuntimeException("이미지 처리 중 오류 발생", e);
//        } catch (Exception e) {
//            log.error("데이터 처리 중 오류 발생", e);
//            throw new RuntimeException("데이터 처리 중 오류 발생", e);
//        }
    }
}






