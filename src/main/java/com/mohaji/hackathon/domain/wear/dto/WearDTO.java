package com.mohaji.hackathon.domain.wear.dto;

import com.mohaji.hackathon.domain.wear.enums.Attributes.*;
import com.mohaji.hackathon.domain.wear.enums.Classification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WearDTO {
    private UUID id;
    private Classification classification;
    private Length length;
    private SleeveLength sleeveLength;
    private Color color;
    private Category category;
    private Set<Detail> details;
    private Set<Matter> matters;
    private Print prints;
    private Neckline neckline;
    private Fit fit;
    private List<ImageDTO> images;
}