package com.mohaji.hackathon.common.enums.Attributes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Fit {
  NORMAL("노멀", "normal"),
  LOOSE("루즈", "loose"),
  OVERSIZED("오버사이즈", "oversized"),
  SKINNY("스키니", "skinny"),
  WIDE("와이드", "wide"),
  TIGHT("타이트", "tight");

  private final String koreanName;
  private final String englishName;

}
