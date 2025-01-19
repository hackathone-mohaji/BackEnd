package com.mohaji.hackathon.domain.auth.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class AccountLoginResponseDto {



  private String accessToken;

  private String refreshToken;


  public AccountLoginResponseDto(String accessToken, String refreshToken) {

    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }
}
