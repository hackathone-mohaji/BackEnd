//package com.mohaji.hackathon.domain.wear.enums.Att;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//@AllArgsConstructor
//@Getter
//public enum Category {
//  TOP("탑"),
//  COAT("코트"),
//  JACKET("재킷"),
//  PANTS("팬츠"),
//  SKIRT("스커트"),
//  DRESS("드레스"),
//  JUMPER("점퍼"),
//  JUMPSUIT("점프수트");
//
//  private final String koreanName;
//
//  public static String toFormattedString() {
//    return Stream.of(Category.values())
//            .map(Enum::name)
//            .collect(Collectors.joining(", "));
//  }
//
//}

package com.mohaji.hackathon.domain.wear.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum Weather {
  SPRING("봄"),
  SUMMER("여름"),
  AUTUMN("가을"),
  WINTER("겨울");

  private final String koreanName;

  public static String toFormattedString() {
    return Stream.of(Weather.values())
            .map(Enum::name)
            .collect(Collectors.joining(", "));
  }
}