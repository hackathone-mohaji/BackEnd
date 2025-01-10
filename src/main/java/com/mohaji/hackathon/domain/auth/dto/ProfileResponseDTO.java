package com.mohaji.hackathon.domain.auth.dto;


import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponseDTO {
    private String imageUrl;
    private String username;
}
