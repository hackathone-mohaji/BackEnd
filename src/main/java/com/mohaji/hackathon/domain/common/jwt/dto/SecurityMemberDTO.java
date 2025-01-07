package com.mohaji.hackathon.domain.common.jwt.dto;

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
    private final UUID id;
    @Setter
    private final String email;
    private final String username;

    public static SecurityMemberDTO fromClaims(Claims claims) {
        return SecurityMemberDTO.builder().id(UUID.fromString(claims.getId())).email(claims.get("email", String.class)).username(claims.get("name", String.class)).build();
    }
}
