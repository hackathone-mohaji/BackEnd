package com.mohaji.hackathon.domain.entity;

import com.mohaji.hackathon.common.enums.Classification;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Wear {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;


  /**
   * 옷 주인
   * **/
  @ManyToOne
  private Account account;

  /**
   * 스타일
   * **/

  private String style;


  /**
   * 상하의 구분
   * **/
  private Classification classification;

  /**
   * 이미지
   * **/
  @OneToOne
  private Image image;

}
