package com.mohaji.hackathon.common.enums.Attributes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Print {
  CHECK("체크", "check"),
  FLORAL("플로럴", "floral"),
  STRIPE("스트라이프", "stripe"),
  LETTERING("레터링", "lettering"),
  ZIGZAG("지그재그", "zigzag"),
  SKULL("해골", "skull"),
  LEOPARD("호피", "leopard"),
  TIE_DYE("타이다이", "tie-dye"),
  ZEBRA("지브라", "zebra"),
  GRADATION("그라데이션", "gradation"),
  DOT("도트", "dot"),
  SOLID("무지", "solid"),
  CAMOUFLAGE("카무플라쥬", "camouflage"),
  GRAPHIC("그래픽", "graphic"),
  PAISLEY("페이즐리", "paisley"),
  HOUNDS_TOOTH("하운즈 투스", "Hound's touth"),
  ARGYLE("아가일", "argyle"),
  GINGHAM("깅엄", "gingham");

  private final String koreanName;
  private final String englishName;
}
