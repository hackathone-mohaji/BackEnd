package com.mohaji.hackathon.domain.wear.dto;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WearListResponseDto {

  private List<WearResponseDto> wears;

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  public static class WearResponseDto {
    private Long wearId;
    private String wearImageUrl;
  }

}
