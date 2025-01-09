package com.mohaji.hackathon.domain.openai.service;

import com.mohaji.hackathon.domain.openai.config.GptConfig;
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
    private final GptConfig gptConfig;


    public GPTService(RestClient.Builder builder, GptConfig gptConfig) {
        this.restClient = builder
                .baseUrl("https://api.openai.com/v1/chat/completions")
//                .baseUrl("https://api.openai.com/v1")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + gptConfig.getSecretKey())
                .build();
        this.gptConfig = gptConfig;
    }


    public String getGPTResponse(String prompt) {
        // 요청 바디 생성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", gptConfig.getModel());
        requestBody.put("messages", List.of(Map.of("role", "user", "content", prompt)));

        // 요청 전송 및 응답 받기
        String response = restClient.post()
                .body(requestBody)
                .retrieve()
                .body(String.class);

        return response;
    }

//    public String analyzeImage(MultipartFile image) throws IOException {
//        String prompt = "\"아래 제공된 사진을 기반으로 각 분류에 해당하는 값을 판단하여 JSON 형태로 정리해 주세요. 분류는 다음과 같이 나뉩니다:\n" +
//                "\n" +
//                "1. **카테고리 분류**:  \n" +
//                "`TOP`, `COAT`, `JACKET`, `PANTS`, `SKIRT`, `DRESS`, `JUMPER`, `JUMPSUIT`\n" +
//                "\n" +
//                "2. **색깔 분류**:  \n" +
//                "`BURGUNDY`, `CAMEL`, `NAVY`, `BROWN`, `GRAY`, `DEEP_TONE`, `BLUE`, `WHITE`, `SKY_BLUE`, `MONOTONE`, `PURPLE`, `BEIGE`, `PINK`, `CREAM`, `SILVER`, `GOLD`, `BLACK`, `KHAKI`, `GREEN`, `DARK_TONE`, `GRAYISH_TONE`, `YELLOW`, `VIVID_TONE`, `ORANGE`, `RED`, `WINE`\n" +
//                "\n" +
//                "3. **아이템 분류**:  \n" +
//                "`CHANEL_JACKET`, `TRENCH_COAT`, `SHIRT`, `OVERSIZED_T_SHIRT`, `SHIRT_DRESS`, `CARDIGAN`, `BLAZER`, `POLO_SHIRT`, `CABLE_KNIT`, `V_NECK_SWEATER`, `DUFFLE_COAT`, `TENNIS_SKIRT`, `CHESTERFIELD_COAT`, `BOX_COAT`, `VEST`, `SLACKS`, `PINTUCK_PANTS`, `LOOSE_FIT_PANTS`, `STRAIGHT_PANTS`, `SKINNY_PANTS`, `CARGO_PANTS`, `MILITARY_JACKET`, `T_SHIRT`, `JEANS`, `HOODIE`, `OVERALLS`, `LONG_DRESS`, `LONG_SKIRT`, `ASYMMETRIC_DRESS`, `BLOUSE`, `LOOSE_FIT_BLOUSE`, `FLARE_SKIRT`, `MERMAID_DRESS`, `H_LINE_SKIRT`, `PENCIL_SKIRT`, `DRAPE_DRESS`, `TIGHT_DRESS`, `DRAPE_JACKET`, `PONCHO`, `ROBE`, `CAPE`, `STAND_COLLAR_JACKET`, `CHINA_COLLAR_JACKET`, `OVERSIZED_JACKET`, `NO_COLLAR_JACKET`, `LEATHER_JACKET`, `HAREM_PANTS`, `CHEMISE_DRESS`, `SLEEVELESS`\n" +
//                "\n" +
//                "4. **프린트 분류**:  \n" +
//                "`CHECK`, `HERRINGBONE`, `STRIPE`, `SOLID`, `HOUNDSTOOTH`, `GEOMETRIC`, `ANIMAL`, `FLORAL`, `TIE_DYE`, `LEOPARD`, `ZEBRA`, `OP_ART`, `CAMOUFLAGE`, `ABSTRACT`, `CUBISM`\n" +
//                "\n" +
//                "결과는 아래 예시처럼 JSON 형태로 작성해 주세요:\n" +
//                "\n" +
//                "```json\n" +
//                "{\n" +
//                "  \\\"category\\\": \\\"JACKET\\\",\n" +
//                "  \\\"color\\\": \\\"NAVY\\\",\n" +
//                "  \\\"item\\\": \\\"BLAZER\\\",\n" +
//                "  \\\"print\\\": \\\"SOLID\\\"\n" +
//                "}";

    //        // 이미지 파일을 Base64로 인코딩
//        byte[] fileBytes = image.getBytes();
//        String base64Image = Base64.getEncoder().encodeToString(fileBytes);
//        String dataUriImage = "data:image/jpeg;base64," + base64Image;
//
//        // 메시지 내용 생성
//        Map<String, Object> textContent = new HashMap<>();
//        textContent.put("type", "text");
//        textContent.put("text", prompt);
//
//        Map<String, Object> imageContent = new HashMap<>();
//        imageContent.put("type", "image_url");
//        imageContent.put("image_url", Map.of("url", dataUriImage));
//
//        // 사용자 메시지 생성
//        Map<String, Object> userMessage = new HashMap<>();
//        userMessage.put("role", "user");
//        userMessage.put("content", List.of(textContent, imageContent));
//
//        // 요청 본문 구성
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("model", fineTunedModelId); // 파인튜닝된 모델 ID 사용
//        requestBody.put("messages", List.of(userMessage));
//
//        return restClient.post()
//                .uri("/chat/completions")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(requestBody))
//                .retrieve()
//                .body(String.class);
//    }
//    public String analyzeImage(MultipartFile image) throws IOException {
//        String prompt = "\"아래 제공된 사진을 기반으로 각 분류에 해당하는 값을 판단하여 JSON 형태로 정리해 주세요. 분류는 다음과 같이 나뉩니다:\n" +
//                "\n" +
//                "1. **카테고리 분류**:  \n" +
//                "`TOP`, `COAT`, `JACKET`, `PANTS`, `SKIRT`, `DRESS`, `JUMPER`, `JUMPSUIT`\n" +
//                "\n" +
//                "2. **색깔 분류**:  \n" +
//                "`BURGUNDY`, `CAMEL`, `NAVY`, `BROWN`, `GRAY`, `DEEP_TONE`, `BLUE`, `WHITE`, `SKY_BLUE`, `MONOTONE`, `PURPLE`, `BEIGE`, `PINK`, `CREAM`, `SILVER`, `GOLD`, `BLACK`, `KHAKI`, `GREEN`, `DARK_TONE`, `GRAYISH_TONE`, `YELLOW`, `VIVID_TONE`, `ORANGE`, `RED`, `WINE`\n" +
//                "\n" +
//                "3. **아이템 분류**:  \n" +
//                "`CHANEL_JACKET`, `TRENCH_COAT`, `SHIRT`, `OVERSIZED_T_SHIRT`, `SHIRT_DRESS`, `CARDIGAN`, `BLAZER`, `POLO_SHIRT`, `CABLE_KNIT`, `V_NECK_SWEATER`, `DUFFLE_COAT`, `TENNIS_SKIRT`, `CHESTERFIELD_COAT`, `BOX_COAT`, `VEST`, `SLACKS`, `PINTUCK_PANTS`, `LOOSE_FIT_PANTS`, `STRAIGHT_PANTS`, `SKINNY_PANTS`, `CARGO_PANTS`, `MILITARY_JACKET`, `T_SHIRT`, `JEANS`, `HOODIE`, `OVERALLS`, `LONG_DRESS`, `LONG_SKIRT`, `ASYMMETRIC_DRESS`, `BLOUSE`, `LOOSE_FIT_BLOUSE`, `FLARE_SKIRT`, `MERMAID_DRESS`, `H_LINE_SKIRT`, `PENCIL_SKIRT`, `DRAPE_DRESS`, `TIGHT_DRESS`, `DRAPE_JACKET`, `PONCHO`, `ROBE`, `CAPE`, `STAND_COLLAR_JACKET`, `CHINA_COLLAR_JACKET`, `OVERSIZED_JACKET`, `NO_COLLAR_JACKET`, `LEATHER_JACKET`, `HAREM_PANTS`, `CHEMISE_DRESS`, `SLEEVELESS`\n" +
//                "\n" +
//                "4. **프린트 분류**:  \n" +
//                "`CHECK`, `HERRINGBONE`, `STRIPE`, `SOLID`, `HOUNDSTOOTH`, `GEOMETRIC`, `ANIMAL`, `FLORAL`, `TIE_DYE`, `LEOPARD`, `ZEBRA`, `OP_ART`, `CAMOUFLAGE`, `ABSTRACT`, `CUBISM`\n" +
//                "\n" +
//                "결과는 아래 예시처럼 JSON 형태로 작성해 주세요:\n" +
//                "\n" +
//                "```json\n" +
//                "{" +
//                "  \\\"category\\\": \\\"JACKET\\\",\n" +
//                "  \\\"color\\\": \\\"NAVY\\\",\n" +
//                "  \\\"item\\\": \\\"BLAZER\\\",\n" +
//                "  \\\"print\\\": \\\"SOLID\\\"\n" +
//                "}";
//
//
//        // 이미지 파일을 Base64로 인코딩
//        byte[] fileBytes = image.getBytes();
//        String base64Image = Base64.getEncoder().encodeToString(fileBytes);
//        String dataUriImage = "data:image/jpeg;base64," + base64Image;
//
//        // 메시지 내용 생성
//        Map<String, Object> textContent = new HashMap<>();
//        textContent.put("type", "text");
//        textContent.put("text", prompt);
//
//        Map<String, Object> imageContent = new HashMap<>();
//        imageContent.put("type", "image_url");
//        imageContent.put("image_url", Map.of("url", dataUriImage));
//
//        // 사용자 메시지 생성
//        Map<String, Object> userMessage = new HashMap<>();
//        userMessage.put("role", "user");
//        userMessage.put("content", List.of(textContent, imageContent));
//
//        // 요청 본문 구성
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("model", model);
//        requestBody.put("messages", List.of(userMessage));
//
//        // REST API 요청 전송
//        return restClient.post()
//                .body(requestBody)
//                .retrieve()
//                .body(String.class);
//    }
    public String analyzeImage(MultipartFile image) throws IOException {
        String prompt = "\"아래 제공된 사진을 기반으로 각 분류에 해당하는 값을 판단하여 JSON 형태로 정리해 주세요. 특히 색깔을 신경써서 확인해주세요 분류는 다음과 같이 나뉩니다:\n" +
                "\n" +
                "1. **카테고리 분류**:  \n" +
                "`TOP`, `COAT`, `JACKET`, `PANTS`, `SKIRT`, `DRESS`, `JUMPER`, `JUMPSUIT`\n" +
                "\n" +
                "2. **색깔 분류**:  \n" +
                "`BURGUNDY`, `CAMEL`, `NAVY`, `BROWN`, `GRAY`, `DEEP_TONE`, `BLUE`, `WHITE`, `SKY_BLUE`, `MONOTONE`, `PURPLE`, `BEIGE`, `PINK`, `CREAM`, `SILVER`, `GOLD`, `BLACK`, `KHAKI`, `GREEN`, `DARK_TONE`, `GRAYISH_TONE`, `YELLOW`, `VIVID_TONE`, `ORANGE`, `RED`, `WINE`\n" +
                "\n" +
                "3. **아이템 분류**:  \n" +
                "`CHANEL_JACKET`, `TRENCH_COAT`, `SHIRT`, `OVERSIZED_T_SHIRT`, `SHIRT_DRESS`, `CARDIGAN`, `BLAZER`, `POLO_SHIRT`, `CABLE_KNIT`, `V_NECK_SWEATER`, `DUFFLE_COAT`, `TENNIS_SKIRT`, `CHESTERFIELD_COAT`, `BOX_COAT`, `VEST`, `SLACKS`, `PINTUCK_PANTS`, `LOOSE_FIT_PANTS`, `STRAIGHT_PANTS`, `SKINNY_PANTS`, `CARGO_PANTS`, `MILITARY_JACKET`, `T_SHIRT`, `JEANS`, `HOODIE`, `OVERALLS`, `LONG_DRESS`, `LONG_SKIRT`, `ASYMMETRIC_DRESS`, `BLOUSE`, `LOOSE_FIT_BLOUSE`, `FLARE_SKIRT`, `MERMAID_DRESS`, `H_LINE_SKIRT`, `PENCIL_SKIRT`, `DRAPE_DRESS`, `TIGHT_DRESS`, `DRAPE_JACKET`, `PONCHO`, `ROBE`, `CAPE`, `STAND_COLLAR_JACKET`, `CHINA_COLLAR_JACKET`, `OVERSIZED_JACKET`, `NO_COLLAR_JACKET`, `LEATHER_JACKET`, `HAREM_PANTS`, `CHEMISE_DRESS`, `SLEEVELESS`\n" +
                "\n" +
                "4. **프린트 분류**:  \n" +
                "`CHECK`, `HERRINGBONE`, `STRIPE`, `SOLID`, `HOUNDSTOOTH`, `GEOMETRIC`, `ANIMAL`, `FLORAL`, `TIE_DYE`, `LEOPARD`, `ZEBRA`, `OP_ART`, `CAMOUFLAGE`, `ABSTRACT`, `CUBISM`\n" +
                "\n" +
                "결과는 아래 예시처럼 JSON 형태로 작성해 주세요:\n" +
                "\n" +
                "```json\n" +
                "{" +
                "  \\\"category\\\": \\\"JACKET\\\",\n" +
                "  \\\"color\\\": \\\"NAVY\\\",\n" +
                "  \\\"item\\\": \\\"BLAZER\\\",\n" +
                "  \\\"print\\\": \\\"SOLID\\\"\n" +
                "}";

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
        requestBody.put("model", gptConfig.getModel());
        requestBody.put("messages", List.of(userMessage));

        // REST API 요청 전송 및 응답 받기
        String response = restClient.post()
                .body(requestBody)
                .retrieve()
                .body(String.class);

        // 이스케이프된 JSON 문자열을 디코딩
        String decodedResponse = response.replace("\\\"", "\"");

        return decodedResponse;
    }
}
