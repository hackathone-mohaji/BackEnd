//package com.mohaji.hackathon.domain.wear.service;
//
//
//import com.mohaji.hackathon.domain.wear.dto.WearDTO;
//import com.mohaji.hackathon.domain.Image.util.ImageUtil;
//import com.mohaji.hackathon.domain.wear.dto.ImageDTO;
//import com.mohaji.hackathon.domain.wear.entity.Wear;
//import com.mohaji.hackathon.domain.wear.enums.Attributes.*;
//import com.mohaji.hackathon.domain.wear.enums.Classification;
//import com.mohaji.hackathon.domain.wear.repository.WearRepository;
//import com.mohaji.hackathon.domain.openai.service.GPTService;
//import lombok.RequiredArgsConstructor;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class WearService {
//
//    private final WearRepository wearRepository;
//    private final GPTService gptService;
//
//    public WearDTO saveWear(MultipartFile imageFile) throws IOException {
//        // 1. 이미지 저장
//        Wear wear = new Wear();
//        ImageUtil.addImage(wear, imageFile);
//
//        // 2. GPT 분석
//        String gptResponse = gptService.analyzeImage(imageFile);
//
//        // 3. GPT 응답을 Wear 엔티티에 매핑
//        mapGPTResponseToWear(wear, gptResponse);
//
//        // 4. Wear 엔티티 저장
//        Wear savedWear = wearRepository.save(wear);
//
//        // 5. DTO 반환
//        return mapToDTO(savedWear);
//    }
//
//    private void mapGPTResponseToWear(Wear wear, String gptResponse) {
//        // Parse GPT response JSON and map to Wear entity
//        // Example assumes the response is JSON format
//        JSONObject jsonResponse = new JSONObject(gptResponse);
//
//        wear.setClassification(Classification.valueOf(jsonResponse.getString("classification")));
//        wear.setLength(Length.valueOf(jsonResponse.getString("length")));
//        wear.setSleeveLength(SleeveLength.valueOf(jsonResponse.optString("sleeveLength", "NONE")));
//        wear.setColor(Color.valueOf(jsonResponse.getString("color")));
//        wear.setCategory(Category.valueOf(jsonResponse.getString("category")));
//        wear.setDetails(parseEnumSet(jsonResponse.getJSONArray("details"), Detail.class));
//        wear.setMatters(parseEnumSet(jsonResponse.getJSONArray("matters"), Matter.class));
//        wear.setPrints(Print.valueOf(jsonResponse.getString("prints")));
//        wear.setNeckline(Neckline.valueOf(jsonResponse.getString("neckline")));
//        wear.setFit(Fit.valueOf(jsonResponse.getString("fit")));
//    }
//
//    private <E extends Enum<E>> Set<E> parseEnumSet(JSONArray jsonArray, Class<E> enumClass) {
//        return jsonArray.toList().stream()
//                .map(item -> Enum.valueOf(enumClass, item.toString()))
//                .collect(Collectors.toSet());
//    }
//
//    private WearDTO mapToDTO(Wear wear) {
//        return WearDTO.builder()
//                .id(wear.getId())
//                .classification(wear.getClassification())
//                .length(wear.getLength())
//                .sleeveLength(wear.getSleeveLength())
//                .color(wear.getColor())
//                .category(wear.getCategory())
//                .details(wear.getDetails())
//                .matters(wear.getMatters())
//                .prints(wear.getPrints())
//                .neckline(wear.getNeckline())
//                .fit(wear.getFit())
//                .images(wear.getImages().stream()
//                        .map(image -> ImageDTO.builder()
//                                .id(image.getId())
//                                .originalFileName(image.getOriginalFileName())
//                                .url(ImageUtil.imageUrl(image, wear))
//                                .build())
//                        .collect(Collectors.toList()))
//                .build();
//    }
//
//}
