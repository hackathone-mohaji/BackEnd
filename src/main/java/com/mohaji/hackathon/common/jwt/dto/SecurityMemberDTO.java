package com.mohaji.hackathon.common.jwt.dto;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class SecurityMemberDTO {
    private final Long id;
    @Setter
    private final String email;
    private final String username;

    public static SecurityMemberDTO fromClaims(Claims claims) {
        return SecurityMemberDTO.builder().id(Long.parseLong(claims.getId())).email(claims.get("email", String.class)).username(claims.get("name", String.class)).build();
    }
}
