package com.mohaji.hackathon.domain.wear.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mohaji.hackathon.domain.wear.enums.Att.Category;
import com.mohaji.hackathon.domain.wear.enums.Att.Color;
import com.mohaji.hackathon.domain.wear.enums.Att.Item;
import com.mohaji.hackathon.domain.wear.enums.Att.Print;
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

    @JsonProperty("Category") // JSON 키 "Category"와 매핑
    private Category category;

    @JsonProperty("Color") // JSON 키 "Color"와 매핑
    private Color color;

    @JsonProperty("Item") // JSON 키 "Item"과 매핑
    private Item item;

    @JsonProperty("Print") // JSON 키 "Print"와 매핑
    private Print print;
}