package com.mohaji.hackathon.domain.auth.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class GeneratedTokenDTO {



  private String accessToken;



  public GeneratedTokenDTO(String accessToken) {

    this.accessToken = accessToken;
  }
}
