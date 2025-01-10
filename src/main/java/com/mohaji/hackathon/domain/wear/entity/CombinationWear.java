package com.mohaji.hackathon.domain.wear.entity;

import com.mohaji.hackathon.domain.auth.entity.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Entity
@Setter
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

  @ManyToOne
  private Account account;
}
