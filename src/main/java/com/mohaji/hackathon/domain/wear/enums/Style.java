package com.mohaji.hackathon.domain.wear.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Style {

  CLASSIC("클래식", "Classic"),
  PREPPY("프레피", "Preppy"),
  MANISH("매니시", "Manish"),
  TOMBOY("톰보이", "Tomboy"),
  ELEGANCE("엘레강스", "Elegance"),
  SOPHISTICATED("소피스트케이티드", "Sophisticated"),
  GLAMOROUS("글래머러스", "Glamorous"),
  ETHNIC("에스닉", "Ethnic"),
  HIPPIE("히피", "Hippie"),
  ORIENTAL("오리엔탈", "Oriental"),
  MODERN("모던", "Modern"),
  MINIMAL("미니멀", "Minimal"),
  NATURAL("내추럴", "Natural"),
  COUNTRY("컨트리", "Country"),
  RESORT("리조트", "Resort"),
  ROMANTIC("로멘틱", "Romantic"),
  SEXY("섹시", "Sexy"),
  SPORTY("스포티", "Sporty"),
  ATHLEISURE("에슬레져", "Athleisure"),
  MILITARY("밀리터리", "Military"),
  NEWTRO("뉴트로", "Newtro"),
  HIPHOP("힙합", "HipHop"),
  KITSCH("키치", "Kitsch"),
  MAXIMUM("맥시멈", "Maximum"),
  PUNK("펑크", "Punk"),
  CASUAL("캐주얼", "Casual"),
  NORMCORE("놈코어", "Normcore");

  private final String koreanName;
  private final String englishName;
}
