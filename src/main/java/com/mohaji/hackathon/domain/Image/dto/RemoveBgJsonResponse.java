package com.mohaji.hackathon.domain.Image.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveBgJsonResponse {

  private Data data;

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Data {
    private String result_b64;
    private int foreground_top;
    private int foreground_left;
    private int foreground_width;
    private int foreground_height;
  }
}
