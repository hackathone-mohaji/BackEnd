package com.mohaji.hackathon.common.enums.Attributes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Collar {
  SHIRT_COLLAR("셔츠칼라", "shirt-collar"),
  BOW_COLLAR("보우칼라", "bow-collar"),
  SAILOR_COLLAR("세일러칼라", "sailor-collar"),
  SHAWL_COLLAR("숄칼라", "shawl-collar"),
  POLO_COLLAR("폴로칼라", "Polo-collar"),
  PETER_PAN_COLLAR("피터팬칼라", "peterpan-collar"),
  NOTCHED_COLLAR("너치드칼라", "notched-collar"),
  STAND_UP_COLLAR("차이나칼라", "standup-collar"),
  TAILORED_COLLAR("테일러드칼라", "tailored-collar"),
  BAND_COLLAR("밴드칼라", "band collar");

  private final String koreanName;
  private final String englishName;
}
