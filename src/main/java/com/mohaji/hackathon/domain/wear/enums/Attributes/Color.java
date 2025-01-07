package com.mohaji.hackathon.domain.wear.enums.Attributes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Color {
  BLACK("블랙", "black"),
  KHAKI("카키", "khaki"),
  WHITE("화이트", "white"),
  MINT("민트", "mint"),
  GREY("그레이", "grey"),
  BLUE("블루", "blue"),
  RED("레드", "red"),
  NAVY("네이비", "navy"),
  PINK("핑크", "pink"),
  SKYBLUE("스카이블루", "skyblue"),
  ORANGE("오렌지", "orange"),
  PURPLE("퍼플", "purple"),
  BEIGE("베이지", "beige"),
  LAVENDER("라벤더", "lavender"),
  BROWN("브라운", "brown"),
  WINE("와인", "wine"),
  YELLOW("옐로우", "yellow"),
  NEON("네온", "neon"),
  GREEN("그린", "green"),
  GOLD("골드", "gold");

  private final String koreanName;
  private final String englishName;
}
