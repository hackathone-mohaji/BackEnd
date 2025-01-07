package com.mohaji.hackathon.domain.web.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@AllArgsConstructor
@Builder
public class SignUpResponseDTO {

    private String email;
    private String name;
    private int status;

}
