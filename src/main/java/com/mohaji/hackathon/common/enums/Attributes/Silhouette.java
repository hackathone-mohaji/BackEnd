package com.mohaji.hackathon.common.enums.Attributes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Silhouette {
  // 상의/드레스용 실루엣
  PEPLUM("페플럼", "peplum"),
  MERMAID("머메이드", "mermaid"),
  ASYMMETRICAL("비대칭", "asymmetrical"),
  UNKNOWN("알수없음", "unknown"),
  PENCIL("펜슬", "pencil"),
  A_LINE("A라인", "a-line"),
  H_LINE("H라인", "h-line"),

  // 바지용 실루엣
  BELL_BOTTOM_FLARE("벨보텀/플레어", "bell-bottom/flare"),
  BOOTS_CUT("부츠컷", "boots-cut"),
  TAPERED("테이퍼드", "tapered"),
  STRAIGHT("스트레이트", "straight");

  private final String koreanName;
  private final String englishName;
}
