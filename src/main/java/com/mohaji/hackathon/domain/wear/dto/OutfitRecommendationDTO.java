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
public class OutfitRecommendationDTO {
    private final Wear top;
    private final Wear bottom;
    private final Wear outer;
    private final String  reason;
}
