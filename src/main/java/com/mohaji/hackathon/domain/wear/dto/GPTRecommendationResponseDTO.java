package com.mohaji.hackathon.domain.wear.dto;

import com.mohaji.hackathon.domain.wear.entity.Wear;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class GPTRecommendationResponseDTO {

    private final Long topId;
    private final Long bottomId;
    private final Long outerId;
    private final String  reason;
}
