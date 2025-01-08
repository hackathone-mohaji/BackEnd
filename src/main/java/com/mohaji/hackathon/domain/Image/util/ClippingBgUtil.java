package com.mohaji.hackathon.domain.Image.util;

import com.mohaji.hackathon.common.error.enums.ErrorCode;
import com.mohaji.hackathon.common.error.exception.BusinessException;
import com.mohaji.hackathon.domain.Image.dto.CustomMultipartFile;
import com.mohaji.hackathon.domain.Image.dto.RemoveBgJsonResponse;
import com.mohaji.hackathon.domain.Image.dto.RemoveBgJsonResponse.Data;
import jakarta.annotation.PostConstruct;
import java.util.Base64;
import java.util.Random;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;


@Component
@RequiredArgsConstructor
public class ClippingBgUtil {

  private static final int MAX_RETRIES = 5;
  private final RestClient restClient;

  @Value("${removebg.api.key}")
  private String apiKey;

  public MultipartFile removeBackground(MultipartFile inputFile)  {
    // MultipartBodyBuilder 생성
    MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
    bodyBuilder.part("image_file", inputFile.getResource());
    bodyBuilder.part("size", "auto");

    // API 호출 및 응답 받기
    RemoveBgJsonResponse response = restClient.post()
        .uri("https://api.remove.bg/v1.0/removebg")
        .header("X-Api-Key", apiKey)
        .body(bodyBuilder.build())
        .retrieve()
        .body(RemoveBgJsonResponse.class);

    if (response == null) {
      throw new BusinessException(ErrorCode.HTTP_MESSAGE_CONVERSION);
    }
    Data data = response.getData();
    if (data == null) {
      throw new BusinessException(ErrorCode.HTTP_MESSAGE_CONVERSION);
    }
    // Base64 문자열을 byte 배열로 변환
    byte[] imageBytes = Base64.getDecoder().decode(data.getResult_b64());

    return new CustomMultipartFile(
        imageBytes, // 바이트 데이터
        inputFile.getName(), // 원본 파일 이름
        inputFile.getOriginalFilename(), // 원본 파일의 원래 이름 (예: example.png)
        inputFile.getContentType()// 원본 파일의 MIME 타입
    );
  }

  @PostConstruct
  public void init() {
    try {
      callApiWithExponentialBackoff();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private void callApiWithExponentialBackoff() throws InterruptedException {
    boolean retry = true;
    int retries = 0;
    Random random = new Random();

    while (retry && retries < MAX_RETRIES) {
      double waitTime = Math.pow(2, retries) + random.nextDouble(); // random_number in seconds
      Thread.sleep((long) (waitTime * 1000)); // convert to milliseconds

      String result = getResult(); // Assume this function retrieves the result

      if ("SUCCESS".equals(result)) {
        retry = false;
      } else if ("ERROR".equals(result) || "THROTTLED".equals(result)) {
        retry = true;
      }

      retries++;
    }
  }

  public static String getResult() {
    // Placeholder for actual API call logic
    return "SUCCESS"; // Example return value
  }

}
