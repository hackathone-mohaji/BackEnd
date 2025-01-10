package com.mohaji.hackathon.domain.wear.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CombinationWear {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @ManyToOne
  Combination combination;

  @ManyToOne
  Wear wear;
}
