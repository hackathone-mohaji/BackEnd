//package com.mohaji.hackathon.domain.wear.enums.Att;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//@AllArgsConstructor
//@Getter
//public enum Item {
//  CHANEL_JACKET("샤넬 재킷"),
//  TRENCH_COAT("트렌치 코트"),
//  SHIRT("셔츠"),
//  OVERSIZED_T_SHIRT("옵저바이드 티"),
//  SHIRT_DRESS("셔츠 드레스"),
//  CARDIGAN("가디건"),
//  BLAZER("블레이저"),
//  POLO_SHIRT("폴로 셔츠"),
//  CABLE_KNIT("케이블 니트"),
//  V_NECK_SWEATER("V넥 스웨터"),
//  DUFFLE_COAT("더플 코트"),
//  TENNIS_SKIRT("테니스 스커트"),
//  CHESTERFIELD_COAT("체스터필드 코트"),
//  BOX_COAT("박스코트"),
//  VEST("베스트"),
//  SLACKS("슬렉스"),
//  PINTUCK_PANTS("핀턱팬츠"),
//  LOOSE_FIT_PANTS("루즈핏 팬츠"),
//  STRAIGHT_PANTS("스트릿 팬츠"),
//  SKINNY_PANTS("스키니 팬츠"),
//  CARGO_PANTS("카고 팬츠"),
//  MILITARY_JACKET("밀리터리 재킷"),
//  T_SHIRT("티셔츠"),
//  JEANS("청바지"),
//  HOODIE("후드티"),
//  OVERALLS("오버롤즈"),
//  LONG_DRESS("롱 드레스"),
//  LONG_SKIRT("롱 스커트"),
//  ASYMMETRIC_DRESS("에시매트릭 드레스"),
//  BLOUSE("블라우스"),
//  LOOSE_FIT_BLOUSE("루즈핏 블라우스"),
//  FLARE_SKIRT("플레어 스커트"),
//  MERMAID_DRESS("머메이드 드레스"),
//  H_LINE_SKIRT("H라인 스커트"),
//  PENCIL_SKIRT("펜슬 스커트"),
//  DRAPE_DRESS("드레이프 드레스"),
//  TIGHT_DRESS("타이트 드레스"),
//  DRAPE_JACKET("드레이프 재킷"),
//  PONCHO("판초"),
//  ROBE("로브"),
//  CAPE("케이프"),
//  STAND_COLLAR_JACKET("스탠드 칼라 재킷"),
//  CHINA_COLLAR_JACKET("차이나 칼라 재킷"),
//  OVERSIZED_JACKET("옵저바이드 재킷"),
//  NO_COLLAR_JACKET("노칼라 재킷"),
//  LEATHER_JACKET("래더 재킷"),
//  HAREM_PANTS("하렘팬츠"),
//  CHEMISE_DRESS("슈미즈 드레스"),
//  SLEEVELESS("슬리브리스");
//
//  private final String koreanName;
//  public static String toFormattedString() {
//    return Stream.of(Item.values())
//            .map(Enum::name)
//            .collect(Collectors.joining(", "));
//  }
//}


package com.mohaji.hackathon.domain.wear.enums.Att;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum Item {
  CHANEL_JACKET("샤넬 재킷"),
  TRENCH_COAT("트렌치 코트"),
  JUMPER("점퍼"),
  SHIRT("셔츠"),
  OVERSIZED_T_SHIRT("옵저바이드 티"),
  SHIRT_DRESS("셔츠 드레스"),
  CARDIGAN("가디건"),
  BLAZER("블레이저"),
  DRESS("드레스"),
  POLO_SHIRT("폴로 셔츠"),
  CABLE_KNIT("케이블 니트"),
  V_NECK_SWEATER("V넥 스웨터"),
  DUFFLE_COAT("더플 코트"),
  TENNIS_SKIRT("테니스 스커트"),
  CHESTERFIELD_COAT("체스터필드 코트"),
  BOX_COAT("박스코트"),
  VEST("베스트"),
  SLACKS("슬렉스"),
  PINTUCK_PANTS("핀턱팬츠"),
  LOOSE_FIT_PANTS("루즈핏 팬츠"),
  STRAIGHT_PANTS("스트릿 팬츠"),
  SKINNY_PANTS("스키니 팬츠"),
  CARGO_PANTS("카고 팬츠"),
  MILITARY_JACKET("밀리터리 재킷"),
  T_SHIRT("티셔츠"),
  JEANS("청바지"),
  HOODIE("후드티"),
  OVERALLS("오버롤즈"),
  LONG_DRESS("롱 드레스"),
  LONG_SKIRT("롱 스커트"),
  ASYMMETRIC_DRESS("에시매트릭 드레스"),
  BLOUSE("블라우스"),
  SKIRT("스커트"),
  LOOSE_FIT_BLOUSE("루즈핏 블라우스"),
  FLARE_SKIRT("플레어 스커트"),
  MERMAID_DRESS("머메이드 드레스"),
  H_LINE_SKIRT("H라인 스커트"),
  PENCIL_SKIRT("펜슬 스커트"),
  DRAPE_DRESS("드레이프 드레스"),
  TIGHT_DRESS("타이트 드레스"),
  DRAPE_JACKET("드레이프 재킷"),
  PONCHO("판초"),
  ROBE("로브"),
  CAPE("케이프"),
  STAND_COLLAR_JACKET("스탠드 칼라 재킷"),
  CHINA_COLLAR_JACKET("차이나 칼라 재킷"),
  OVERSIZED_JACKET("옵저바이드 재킷"),
  NO_COLLAR_JACKET("노칼라 재킷"),
  LEATHER_JACKET("래더 재킷"),
  HAREM_PANTS("하렘팬츠"),
  CHEMISE_DRESS("슈미즈 드레스"),
  SLEEVELESS("슬리브리스"),
  JACKET("재킷"),
  SINGLE_COAT("싱글코트"),
  WIDE_PANTS("와이드 팬츠"),
  SWEATER("스웨터"),
  BLOUSON("블루종"),
  TRAINING_PANTS("트레이닝 팬츠"),
  SWEATERSHIRT("맨투맨"),
  ANORAK("아노락"),
  SLIM_PANTS("슬림 팬츠"),
  DENIM_JACKET("데님 자켓"),
  SUIT("정장"),
  DRESS_SHOES("드레스 슈즈"),
  SNEAKERS("스니커즈"),
  CHUNKY_SNEAKERS("청키 슈즈"),
  LOAFERS("로퍼"),
  DERBY_SHOES("더비 슈즈"),
  OXFORD_SHOES("옥스포드 슈즈"),
  RUNNING_SHOES("러닝 슈즈"),
  TRAINERS("트레이너"),
  SLIP_ONS("슬립온");

  private final String koreanName;

  public static String toFormattedString() {
    return Stream.of(Item.values())
            .map(Enum::name)
            .collect(Collectors.joining(", "));
  }
}
