package com.mohaji.hackathon.domain.wear.entity;

import com.mohaji.hackathon.domain.auth.entity.Account;
import com.mohaji.hackathon.domain.wear.enums.Style;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Combination {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


/**
   * 스타일
   * *
*/

  @Enumerated(EnumType.STRING)
  private Style style;

  private boolean viewed;

  private boolean bookmarked;

  @ManyToOne
  private Account account;


}
