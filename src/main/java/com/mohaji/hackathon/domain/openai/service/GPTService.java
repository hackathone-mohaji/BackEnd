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

    public static String generatePrompt() {
        String categories = Category.toFormattedString();
        String colors = Color.toFormattedString();
        String items = Item.toFormattedString();
        String prints = Print.toFormattedString();
        // Prompt 생성
        return "Based on the provided image, please determine the values corresponding to each category and organize them into a JSON format. " +
                "**Ensure that the JSON format is valid and properly structured.Please provide the JSON content without the enclosing json\\\\n at the beginning, \\\\n at the end, and also remove all newline characters (\\\\n) along with extra spaces and whitespace.** The results should be written according to the following categories: \n\n" +
                "1. **Category**: \n" + categories + "\n\n" +
                "2. **Color**: \n" + colors + "\n\n" +
                "3. **Item**: \n" + items + "\n\n" +
                "4. **Print**: \n" + prints + "\n";
    }


    public WearDTO analyzeImage(MultipartFile image) throws IOException {
        String prompt = generatePrompt();
        // 프롬프트 및 요청 구성은 기존 코드 유지
//        String prompt = "Based on the provided image, please determine the values corresponding to each category and organize them into a JSON format. **Ensure that the JSON format is valid and properly structured.Please provide the JSON content without the enclosing json\\n at the beginning, \\n at the end, and also remove all newline characters (\\n) along with extra spaces and whitespace. The results should be written according to the following categories: \n" +
//                "\n" +
//                "1. **Category **:  \n" +
//                "TOP, COAT, JACKET, PANTS, SKIRT, DRESS, JUMPER, JUMPSUIT\n" +
//                "\n" +
//                "2. **Color **:  \n" +
//                "BURGUNDY, CAMEL, NAVY, BROWN, GRAY, DEEP_TONE, BLUE, WHITE, SKY_BLUE, MONOTONE, PURPLE, BEIGE, PINK, CREAM, SILVER, GOLD, BLACK, KHAKI, GREEN, DARK_TONE, GRAYISH_TONE, YELLOW, VIVID_TONE, ORANGE, RED, WINE\n" +
//                "\n" +
//                "3. **Item **:  \n" +
//                "CHANEL_JACKET, TRENCH_COAT, SHIRT, OVERSIZED_T_SHIRT, SHIRT_DRESS, CARDIGAN, BLAZER, POLO_SHIRT, CABLE_KNIT, V_NECK_SWEATER, DUFFLE_COAT, TENNIS_SKIRT, CHESTERFIELD_COAT, BOX_COAT, VEST, SLACKS, PINTUCK_PANTS, LOOSE_FIT_PANTS, STRAIGHT_PANTS, SKINNY_PANTS, CARGO_PANTS, MILITARY_JACKET, T_SHIRT, JEANS, HOODIE, OVERALLS, LONG_DRESS, LONG_SKIRT, ASYMMETRIC_DRESS, BLOUSE, LOOSE_FIT_BLOUSE, FLARE_SKIRT, MERMAID_DRESS, H_LINE_SKIRT, PENCIL_SKIRT, DRAPE_DRESS, TIGHT_DRESS, DRAPE_JACKET, PONCHO, ROBE, CAPE, STAND_COLLAR_JACKET, CHINA_COLLAR_JACKET, OVERSIZED_JACKET, NO_COLLAR_JACKET, LEATHER_JACKET, HAREM_PANTS, CHEMISE_DRESS, SLEEVELESS\n" +
//                "\n" +
//                "4. **Print **:  \n" +
//                "CHECK, HERRINGBONE, STRIPE, SOLID, HOUNDSTOOTH, GEOMETRIC, ANIMAL, FLORAL, TIE_DYE, LEOPARD, ZEBRA, OP_ART, CAMOUFLAGE, ABSTRACT, CUBISM`\n";
        byte[] fileBytes = image.getBytes();
        String base64Image = Base64.getEncoder().encodeToString(fileBytes);
        String dataUriImage = "data:image/jpeg;base64," + base64Image;

        Map<String, Object> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", List.of(
                Map.of("type", "text", "text", prompt),
                Map.of("type", "image_url", "image_url", Map.of("url", dataUriImage))
        ));

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
            JsonNode rootNode = objectMapper.readTree(response.toString());

            // 2. content 값 추출
            String content = rootNode
                    .get("choices")
                    .get(0)
                    .get("message")
                    .get("content")
                    .asText();

            // 3. content 값을 JSON으로 바로 파싱하여 DTO로 매핑
            WearDTO wearDto = objectMapper.readValue(content, WearDTO.class);
            log.info("Mapped WearDTO: {}", wearDto);

            return wearDto;
        } catch (JsonParseException e) {
            throw new BusinessException(ErrorCode.WRONG_IMAGE);
        }
    }
}
