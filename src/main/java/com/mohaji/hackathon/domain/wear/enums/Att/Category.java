package com.mohaji.hackathon.domain.wear.enums.Att;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category {
  TOP("탑"),
  COAT("코트"),
  JACKET("재킷"),
  PANTS("팬츠"),
  SKIRT("스커트"),
  DRESS("드레스"),
  JUMPER("점퍼"),
  JUMPSUIT("점프수트");

  private final String koreanName;


}