package com.mohaji.hackathon.domain.wear.dto;


import com.mohaji.hackathon.domain.Image.util.ImageUtil;
import com.mohaji.hackathon.domain.wear.entity.Wear;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WearResponseDto {

  private Long wearId;
  private String wearImageUrl;
  private String color;
  private String print;
  private String item;

  public WearResponseDto(Wear wear) {
    this.wearId = wear.getId();
    this.wearImageUrl = ImageUtil.imageUrl(wear.getImages().get(0),wear);
    this.color=wear.getColor().getKoreanName();
    this.print=wear.getPrints().getKoreanName();
    this.item=wear.getItem().getKoreanName();
  }
}
