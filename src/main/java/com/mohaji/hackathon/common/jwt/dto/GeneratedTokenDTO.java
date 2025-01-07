package com.mohaji.hackathon.common.jwt.dto;


import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class GeneratedTokenDTO {
    private String accessToken;
    private String refreshToken;
    private int status;
}