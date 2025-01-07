package com.mohaji.hackathon.domain.web.dto;


import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class FindEmailResponseDTO {

    @Email
    private String email;
    private int status;
}
