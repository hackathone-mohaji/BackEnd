package com.mohaji.hackathon.domain.wear.entity;

import com.mohaji.hackathon.domain.Image.entity.Image;
import com.mohaji.hackathon.domain.auth.entity.Account;
import com.mohaji.hackathon.domain.wear.enums.Attributes.Category;
import com.mohaji.hackathon.domain.wear.enums.Attributes.Color;
import com.mohaji.hackathon.domain.wear.enums.Attributes.Detail;
import com.mohaji.hackathon.domain.wear.enums.Attributes.Fit;
import com.mohaji.hackathon.domain.wear.enums.Attributes.Length;
import com.mohaji.hackathon.domain.wear.enums.Attributes.Matter;
import com.mohaji.hackathon.domain.wear.enums.Attributes.Neckline;
import com.mohaji.hackathon.domain.wear.enums.Attributes.Print;
import com.mohaji.hackathon.domain.wear.enums.Attributes.SleeveLength;
import com.mohaji.hackathon.domain.wear.enums.Classification;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.util.Set;
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
   * 상하의 구분
   * **/
  @Enumerated(EnumType.STRING)
  private Classification classification;

  /**
   * 이미지
   * **/
  @OneToOne
  private Image image;

  //////////새부 속성/////////////
  /**
   * 기장
   * **/
  @Enumerated(EnumType.STRING)
  private Length length;

  /**
   * 소매기장, 하의일 경우 null
   * **/
  @Enumerated(EnumType.STRING)
  private SleeveLength sleeveLength;

  /**
   * 색상
   * **/
  @Enumerated(EnumType.STRING)
  private Color color;

  /**
   * 카테고리
   * **/
  @Enumerated(EnumType.STRING)
  private Category category;


  /**
   * 디테일
   * **/
  @ElementCollection(targetClass = Detail.class)
  @Enumerated(EnumType.STRING)
  private Set<Detail> details;


  /**
   * 소재
   * **/
  @ElementCollection(targetClass = Matter.class)
  @Enumerated(EnumType.STRING)
  private Set<Matter> matters;

  /**
   * 프린트
   * **/

  @Enumerated(EnumType.STRING)
  private Print prints;

  /**
   * 넥라인
   * **/
  @Enumerated(EnumType.STRING)
  private Neckline neckline;

  /**
   * 핏
   * **/
  @Enumerated(EnumType.STRING)
  private Fit fit;


}