package com.mohaji.hackathon.domain.auth.entity;


import jakarta.persistence.*;

import java.util.UUID;

import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String email;

  private String password;

  private String username;

  private String gender;

  @Setter
  private String refreshToken;

  public void changePassword(String newPassword) {
    this.password = newPassword;
  }
}
