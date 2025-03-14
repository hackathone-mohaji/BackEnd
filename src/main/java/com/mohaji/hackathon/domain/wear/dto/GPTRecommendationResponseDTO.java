package com.mohaji.hackathon.domain.wear.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mohaji.hackathon.domain.wear.enums.Att.Category;
import com.mohaji.hackathon.domain.wear.enums.Att.Color;
import com.mohaji.hackathon.domain.wear.enums.Att.Item;
import com.mohaji.hackathon.domain.wear.enums.Att.Print;
import com.mohaji.hackathon.domain.wear.enums.Style;
import com.mohaji.hackathon.domain.wear.enums.Weather;
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
        private Category category;

        @JsonProperty("item") // JSON 키 "item"와 매핑
        private Item item;

        @JsonProperty("color") // JSON 키 "color"와 매핑
        private Color color;

        @JsonProperty("print") // JSON 키 "print"와 매핑
        private Print print;

        @JsonProperty("style") // JSON 키 "style"와 매핑
        private String style;

        @JsonProperty("season") // JSON 키 "season"와 매핑
        private Weather season;

        @JsonProperty("imageUrl") // 이미지 URL을 매핑
        private String imageUrl;
    }
}