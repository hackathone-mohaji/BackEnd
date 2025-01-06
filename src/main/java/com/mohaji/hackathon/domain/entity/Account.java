package com.mohaji.hackathon.domain.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.Getter;

@Entity
@Getter
public class Account {

  @Id
  private UUID id;

  private String email;

  private String password;

  private String username;
}
