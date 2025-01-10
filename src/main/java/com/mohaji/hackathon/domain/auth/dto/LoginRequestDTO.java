package com.mohaji.hackathon.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class LoginRequestDTO {

    @NotNull
    @Email(message = "잘못된 이메일 형식입니다")
    private final String email;

    @NotNull
    private final String password;


}
