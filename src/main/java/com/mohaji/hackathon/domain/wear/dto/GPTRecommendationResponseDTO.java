package com.mohaji.hackathon.domain.wear.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GPTRecommendationResponseDTO {

    @JsonProperty("outfit") // JSON의 "outfit" 배열을 매핑
    private List<OutfitItemDTO> outfit;

    @JsonProperty("explanation") // JSON의 "explanation" 필드를 매핑
    private String explanation;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OutfitItemDTO {
        @JsonProperty("id") // JSON 키 "id"와 매핑
        private Long id;

        @JsonProperty("category") // JSON 키 "category"와 매핑
        private String category;

        @JsonProperty("item") // JSON 키 "item"와 매핑
        private String item;

        @JsonProperty("color") // JSON 키 "color"와 매핑
        private String color;

        @JsonProperty("print") // JSON 키 "print"와 매핑
        private String print;
    }
}