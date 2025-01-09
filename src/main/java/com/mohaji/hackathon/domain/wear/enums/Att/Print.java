package com.mohaji.hackathon.domain.wear.enums.Att;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Print {
  CHECK("체크"),
  HERRINGBONE("헤링본"),
  STRIPE("스트라이프"),
  SOLID("무지"),
  HOUNDSTOOTH("하운드투스"),
  GEOMETRIC("기하학"),
  ANIMAL("애니멀"),
  FLORAL("플로럴"),
  TIE_DYE("타이다이"),
  LEOPARD("호피"),
  ZEBRA("지브라"),
  OP_ART("옵아트"),
  CAMOUFLAGE("카무플라주"),
  ABSTRACT("추상"),
  CUBISM("큐비즘");

  private final String koreanName;
}
