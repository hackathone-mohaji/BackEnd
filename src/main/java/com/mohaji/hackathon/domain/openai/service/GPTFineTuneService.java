package com.mohaji.hackathon.domain.openai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GPTFineTuneService {
    private final WebClient restClient;



    public GPTFineTuneService(WebClient.Builder builder, @Value("${openai.secret-key}") String secretKey) {
        this.restClient = builder
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + secretKey) // Bearer와 키 사이에 공백 필요
                .build();
    }

    public String uploadFineTuneData(String filePath) {
        // Multipart 데이터 생성
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("file", new FileSystemResource(filePath));
        bodyBuilder.part("purpose", "fine-tune"); // OpenAI에서 요구하는 필드

        // API 요청
        return restClient.post()
                .uri("/files")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(bodyBuilder.build())) // Multipart 데이터 삽입
                .retrieve()
                .bodyToMono(String.class) // 응답을 Mono<String>으로 매핑
                .block(); // 동기적으로 결과 반환
    }
    //todo 여기서 부터 테스트
    public String startFineTune(String fileId) {
        // 요청 바디 생성
        String requestBody = """
            {
                "training_file": "%s",
                "model": "davinci"
            }
            """.formatted(fileId);

        // API 요청
        return restClient.post()
                .uri("/fine_tuning/jobs")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody)) // JSON 요청 바디 삽입
                .retrieve()
                .bodyToMono(String.class) // 응답을 Mono<String>으로 매핑
                .block(); // 동기적으로 결과 반환
    }


    //todo 여기서 부터 테스트
    public String checkFineTuneStatus(String fineTuneId) {
        // 상태 확인 요청
        return restClient.get()
                .uri("/fine_tuning/jobs/" + fineTuneId)
                .retrieve()
                .bodyToMono(String.class) // 응답을 Mono<String>으로 매핑
                .block(); // 동기적으로 결과 반환
    }
}