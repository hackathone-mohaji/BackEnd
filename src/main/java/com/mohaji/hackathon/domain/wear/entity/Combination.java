package com.mohaji.hackathon.domain.wear.entity;

import com.mohaji.hackathon.domain.auth.entity.Account;
import com.mohaji.hackathon.domain.wear.enums.Weather;
import com.mohaji.hackathon.domain.wear.enums.Style;
import jakarta.persistence.*;
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

  public boolean viewed;

  public boolean bookmarked;

  @ManyToOne
  private Account account;


  @Column(length = 10000)
  private String  reason;

  private Weather weather;


}
