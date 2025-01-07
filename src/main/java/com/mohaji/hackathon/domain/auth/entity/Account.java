package com.mohaji.hackathon.domain.auth.entity;


import jakarta.persistence.*;

import java.util.UUID;

import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account {

  @Id
  private UUID id;

  private String email;

  private String password;

  private String username;

  @Setter
  private String refreshToken;

  public void changePassword(String newPassword) {
    this.password = newPassword;
  }
}
