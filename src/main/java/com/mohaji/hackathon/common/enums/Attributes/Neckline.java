package com.mohaji.hackathon.common.enums.Attributes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Neckline {
  ROUND_NECK("라운드넥", "round-neck"),
  SQUARE_NECK("스퀘어넥", "square-neck"),
  U_NECK("유넥", "u-neck"),
  COLLARLESS("노카라", "collarless"),
  V_NECK("브이넥", "v-neck"),
  HOOD("후드", "hood"),
  HALTER_NECK("홀터넥", "halter-neck"),
  TURTLE_NECK("터틀넥", "turtleneck"),
  OFF_SHOULDER("오프숄더", "off-shoulder"),
  BOAT_NECK("보트넥", "Boat neck"),
  ONE_SHOULDER("원 숄더", "one-shoulder"),
  SWEETHEART("스위트하트", "sweetheart");

  private final String koreanName;
  private final String englishName;
}
