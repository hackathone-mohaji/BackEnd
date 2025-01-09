package com.mohaji.hackathon.domain.wear.enums.Att;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum Color {
  BURGUNDY("버건디"),
  CAMEL("카멜"),
  NAVY("네이비"),
  BROWN("브라운"),
  GRAY("그레이"),
  DEEP_TONE("딥톤"),
  BLUE("블루"),
  WHITE("화이트"),
  SKY_BLUE("스카이블루"),
  MONOTONE("모노톤"),
  PURPLE("퍼플"),
  BEIGE("베이지"),
  PINK("핑크"),
  CREAM("크림"),
  SILVER("실버"),
  GOLD("골드"),
  BLACK("블랙"),
  KHAKI("카키"),
  GREEN("그린"),
  DARK_TONE("다크 톤"),
  GRAYISH_TONE("그레이쉬 톤"),
  YELLOW("옐로우"),
  VIVID_TONE("비비드 톤"),
  ORANGE("오렌지"),
  RED("레드"),
  WINE("와인");

  private final String koreanName;

  public static String toFormattedString() {
    return Stream.of(Color.values())
            .map(Enum::name)
            .collect(Collectors.joining(", "));
  }
}
