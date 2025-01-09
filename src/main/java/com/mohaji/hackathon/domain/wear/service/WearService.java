package com.mohaji.hackathon.domain.wear.service;


import com.mohaji.hackathon.domain.Image.controller.ImageUploadTestController;
import com.mohaji.hackathon.domain.Image.util.ImageUtil;
import com.mohaji.hackathon.domain.wear.dto.ImageDTO;
import com.mohaji.hackathon.domain.wear.entity.Wear;
import com.mohaji.hackathon.domain.wear.repository.WearRepository;
import com.mohaji.hackathon.domain.openai.service.GPTService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WearService {

    private final WearRepository wearRepository;
    private final GPTService gptService;
    private final ImageUploadTestController imageUploadTestController;


    public void saveImageAndAnalyzeDate(MultipartFile imageFile) throws IOException {

        //1. 이미지 저장
        imageUploadTestController.addSingleImage(imageFile);
        //2. url 조회
        imageUploadTestController.getImageUrls();
        //3. gpt 한테 사진 보내서 옷 사진 분석
        gptService.analyzeImage(imageFile);
        //4. 데이터 가공 해서 저장


    }


}
