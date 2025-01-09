package com.mohaji.hackathon.domain.wear.enums.Attributes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Classification {

  TOP("상의", "top"),
  BOTTOM("하의", "bottom"),
  ONE_PIECE("원피스", "one-piece"),
  OUTERWEAR("아우터", "outerwear");


  private final String koreanName;
  private final String englishName;
}
