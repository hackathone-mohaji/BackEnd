package com.mohaji.hackathon.domain.entity.classification;

import com.mohaji.hackathon.common.enums.Attributes.Category;
import com.mohaji.hackathon.common.enums.Attributes.Color;
import com.mohaji.hackathon.common.enums.Attributes.Detail;
import com.mohaji.hackathon.common.enums.Attributes.Fit;
import com.mohaji.hackathon.common.enums.Attributes.Matter;
import com.mohaji.hackathon.common.enums.Attributes.Neckline;
import com.mohaji.hackathon.common.enums.Attributes.Print;
import com.mohaji.hackathon.common.enums.Attributes.SleeveLength;
import jakarta.persistence.Entity;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OnePiece extends Classification {

  /**
   * 기장
   **/
  private String length;

  /**
   * 색상
   **/
  private Color color;

  /**
   * 서브색상
   **/
  private Color subColor;

  /**
   * 카테고리
   **/
  private Category category;

  /**
   * 디테일
   **/
  private List<Detail> details;


  /**
   * 소매기장
   **/
  private SleeveLength sleeveLength;

  /**
   * 소재
   **/
  private List<Matter> matters;

  /**
   * 프린트
   **/
  private List<Print> prints;

  /**
   * 넥라인
   **/
  private Neckline neckline;

  /**
   * 핏
   **/
  private Fit fit;
}
