package com.mohaji.hackathon.domain.wear.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Combination_Wear {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;


  @ManyToOne
  Combination combination;

  @ManyToOne
  Wear wear;
}
