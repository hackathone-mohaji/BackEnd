package com.mohaji.hackathon.domain.wear.entity.classification;

import com.mohaji.hackathon.domain.wear.enums.Attributes.Color;
import com.mohaji.hackathon.domain.wear.enums.Attributes.Detail;
import com.mohaji.hackathon.domain.wear.enums.Attributes.Fit;
import com.mohaji.hackathon.domain.wear.enums.Attributes.Matter;
import com.mohaji.hackathon.domain.wear.enums.Attributes.Neckline;
import com.mohaji.hackathon.domain.wear.enums.Attributes.Print;
import com.mohaji.hackathon.domain.wear.enums.Attributes.SleeveLength;
import com.mohaji.hackathon.domain.wear.enums.Attributes.Category;
import jakarta.persistence.Entity;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Tops extends Classification {

  /**
   * 기장
   * **/
  private String length;

  /**
   * 색상
   * **/
  private Color color;

  /**
   * 카테고리
   * **/
  private Category category;

  /**
   * 옷깃
   * **/
  private String lapel;

  /**
   * 디테일
   * **/
  private List<Detail> details;


  /**
   * 소매기장
   * **/
  private SleeveLength sleeveLength;

  /**
   * 소재
   * **/
  private List<Matter> matters;

  /**
   * 프린트
   * **/
  private List<Print> prints;

  /**
   * 넥라인
   * **/
  private Neckline neckline;

  /**
   * 핏
   * **/
  private Fit fit;
}
