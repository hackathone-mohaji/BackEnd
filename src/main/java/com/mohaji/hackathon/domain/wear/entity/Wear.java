package com.mohaji.hackathon.domain.wear.entity;

import com.mohaji.hackathon.domain.Image.entity.Image;
import com.mohaji.hackathon.domain.auth.entity.Account;

import com.mohaji.hackathon.domain.wear.enums.Att.Category;
import com.mohaji.hackathon.domain.wear.enums.Att.Color;
import com.mohaji.hackathon.domain.wear.enums.Att.Item;
import com.mohaji.hackathon.domain.wear.enums.Att.Print;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Entity
@Setter // todo 테스트 후 지우기
@AllArgsConstructor
@NoArgsConstructor
public class Wear implements ImageEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  /**
   * 옷 주인
   **/
  @ManyToOne
  private Account account;

  /**
   * 상하의 구분
   * **/
/*
  @Enumerated(EnumType.STRING)
  private Classification classification;
*/

  /**
   * 이미지 // 구현때문에 리스트 저장이고 실제로는 단일 이미지 저장
   **/
  @OneToMany
  @JoinTable
  @Setter
  private List<Image> images;

  //////////새부 속성/////////////
  /**
   * 기장
   * **/
/*  @Enumerated(EnumType.STRING)
  private Length length;*/

  /**
   * 소매기장, 하의일 경우 null
   * **/
/*  @Enumerated(EnumType.STRING)
  private SleeveLength sleeveLength;*/

  /**
   * 색상
   **/
  @Enumerated(EnumType.STRING)
  private Color color;

  /**
   * 카테고리
   **/
  @Enumerated(EnumType.STRING)
  private Category category;

  /**
   * 디테일
   * **/
/*  @ElementCollection(targetClass = Detail.class)
  @Enumerated(EnumType.STRING)
  private Set<Detail> details;*/

  /**
   * 소재
   * **/
/*
  @ElementCollection(targetClass = Matter.class)
  @Enumerated(EnumType.STRING)
  private Set<Matter> matters;
*/

  /**
   * 프린트
   **/

  @Enumerated(EnumType.STRING)
  private Print prints;

  /**
   * 넥라인
   * **/
/*  @Enumerated(EnumType.STRING)
  private Neckline neckline;*/

  /**
   * 핏
   * **/
/*  @Enumerated(EnumType.STRING)
  private Fit fit;*/


  /**
   * 아이템
   **/
  @Enumerated(EnumType.STRING)
  private Item item;


}
