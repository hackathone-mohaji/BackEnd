package com.mohaji.hackathon.domain.wear.enums;

import com.mohaji.hackathon.domain.wear.enums.Att.Category;
import com.mohaji.hackathon.domain.wear.enums.Att.Color;
import com.mohaji.hackathon.domain.wear.enums.Att.Item;
import com.mohaji.hackathon.domain.wear.enums.Att.Print;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Style {
  CLASSIC_CLASSIC(
      "클래식",
      Set.of(Category.TOP, Category.COAT, Category.JACKET, Category.PANTS, Category.SKIRT,
          Category.DRESS),
      Set.of(Item.SHIRT, Item.TRENCH_COAT, Item.SHIRT_DRESS, Item.CARDIGAN),
      Set.of(Color.BURGUNDY, Color.BEIGE, Color.GRAY, Color.BROWN),
      Set.of(Print.CHECK, Print.STRIPE)
  ),

  CLASSIC_PREPPY(
      "프레피",
      Set.of(Category.TOP, Category.COAT, Category.JACKET, Category.PANTS, Category.SKIRT),
      Set.of(Item.POLO_SHIRT, Item.BLAZER, Item.V_NECK_SWEATER, Item.TENNIS_SKIRT),
      Set.of(Color.WHITE, Color.BEIGE, Color.BURGUNDY),
      Set.of(Print.CHECK, Print.STRIPE)
  ),

  MANNISH(
      "매니시",
      Set.of(Category.TOP, Category.COAT, Category.JACKET, Category.PANTS),
      Set.of(Item.CHESTERFIELD_COAT, Item.VEST, Item.SLACKS, Item.PINTUCK_PANTS),
      Set.of(Color.BROWN, Color.GRAY, Color.DARK_TONE),
      Set.of(Print.STRIPE, Print.CHECK)
  ),

  TOMBOY(
      "톰보이",
      Set.of(Category.TOP, Category.PANTS, Category.JUMPER),
      Set.of(Item.LOOSE_FIT_PANTS, Item.JEANS, Item.T_SHIRT),
      Set.of(Color.BLACK, Color.GRAY, Color.DARK_TONE),
      Set.of(Print.CHECK, Print.STRIPE)
  ),

  ELEGANT(
      "엘레강스",
      Set.of(Category.TOP, Category.DRESS, Category.SKIRT),
      Set.of(Item.LONG_DRESS, Item.LONG_SKIRT, Item.BLOUSE, Item.MERMAID_DRESS),
      Set.of(Color.PURPLE, Color.BEIGE, Color.CREAM),
      Set.of(Print.SOLID, Print.FLORAL)
  ),

  SOPHISTICATED(
      "소피스트케이티드",
      Set.of(Category.TOP, Category.COAT, Category.JACKET, Category.DRESS),
      Set.of(Item.SHIRT_DRESS, Item.BLAZER, Item.DRAPE_DRESS),
      Set.of(Color.GRAY, Color.BLACK, Color.DEEP_TONE),
      Set.of(Print.SOLID)
  ),

  GLAMOROUS(
      "글래머러스",
      Set.of(Category.TOP, Category.DRESS),
      Set.of(Item.TIGHT_DRESS, Item.BLOUSE, Item.DRAPE_DRESS),
      Set.of(Color.GRAY, Color.SILVER, Color.CREAM),
      Set.of(Print.SOLID)
  ),

  ETHNIC(
      "에스닉",
      Set.of(Category.TOP, Category.DRESS, Category.SKIRT),
      Set.of(Item.PONCHO, Item.LONG_DRESS, Item.LOOSE_FIT_BLOUSE),
      Set.of(Color.RED, Color.ORANGE, Color.YELLOW),
      Set.of(Print.GEOMETRIC, Print.FLORAL)
  ),

  HIPPIE(
      "히피",
      Set.of(Category.TOP, Category.DRESS),
      Set.of(Item.PONCHO, Item.LOOSE_FIT_BLOUSE, Item.LONG_DRESS),
      Set.of(Color.BEIGE, Color.ORANGE, Color.RED),
      Set.of(Print.TIE_DYE, Print.FLORAL)
  ),

  ORIENTAL(
      "오리엔탈",
      Set.of(Category.TOP, Category.DRESS),
      Set.of(Item.CHINA_COLLAR_JACKET, Item.STAND_COLLAR_JACKET),
      Set.of(Color.RED, Color.GOLD, Color.BLACK),
      Set.of(Print.FLORAL, Print.GEOMETRIC)
  ),

  MODERN(
      "모던",
      Set.of(Category.TOP, Category.DRESS, Category.PANTS),
      Set.of(Item.SHIRT, Item.STRAIGHT_PANTS, Item.NO_COLLAR_JACKET),
      Set.of(Color.BLACK, Color.WHITE, Color.GRAY),
      Set.of(Print.SOLID, Print.GEOMETRIC)
  ),

  MINIMAL(
      "미니멀",
      Set.of(Category.TOP, Category.DRESS, Category.PANTS),
      Set.of(Item.SHIRT, Item.STRAIGHT_PANTS, Item.SLEEVELESS),
      Set.of(Color.WHITE, Color.BLACK, Color.GRAY),
      Set.of(Print.SOLID)
  ),

  NATURAL_CASUAL(
      "내추럴",
      Set.of(Category.TOP, Category.PANTS, Category.SKIRT),
      Set.of(Item.CARDIGAN, Item.LOOSE_FIT_PANTS, Item.SHIRT),
      Set.of(Color.BEIGE, Color.BROWN, Color.GRAY),
      Set.of(Print.CHECK, Print.STRIPE)
  ),

  RESORT(
      "리조트",
      Set.of(Category.TOP, Category.DRESS),
      Set.of(Item.LOOSE_FIT_BLOUSE, Item.LONG_DRESS),
      Set.of(Color.WHITE, Color.SKY_BLUE, Color.BEIGE),
      Set.of(Print.FLORAL, Print.STRIPE)
  ),

  ROMANTIC(
      "로맨틱",
      Set.of(Category.TOP, Category.DRESS, Category.SKIRT),
      Set.of(Item.FLARE_SKIRT, Item.DRAPE_DRESS, Item.BLOUSE),
      Set.of(Color.PINK, Color.WHITE, Color.CREAM),
      Set.of(Print.FLORAL, Print.SOLID)
  ),

  SEXY(
      "섹시",
      Set.of(Category.TOP, Category.DRESS),
      Set.of(Item.TIGHT_DRESS, Item.SLEEVELESS),
      Set.of(Color.BLACK, Color.RED, Color.WHITE),
      Set.of(Print.SOLID, Print.ANIMAL)
  ),

  SPORTY(
      "스포티",
      Set.of(Category.TOP, Category.PANTS),
      Set.of(Item.T_SHIRT, Item.LOOSE_FIT_PANTS),
      Set.of(Color.RED, Color.BLUE, Color.WHITE),
      Set.of(Print.STRIPE, Print.SOLID)
  ),

  ACTIVE_CASUAL(
      "액슬레저",
      Set.of(Category.TOP, Category.PANTS),
      Set.of(Item.T_SHIRT, Item.LOOSE_FIT_PANTS),
      Set.of(Color.BLACK, Color.GRAY, Color.WHITE),
      Set.of(Print.SOLID)
  ),

  MILITARY(
      "밀리터리",
      Set.of(Category.TOP, Category.PANTS, Category.JACKET),
      Set.of(Item.CARGO_PANTS, Item.MILITARY_JACKET),
      Set.of(Color.KHAKI, Color.BROWN, Color.BLACK),
      Set.of(Print.SOLID, Print.CAMOUFLAGE)
  ),

  FUTURE(
      "뉴트로",
      Set.of(Category.TOP, Category.PANTS, Category.JACKET),
      Set.of(Item.OVERSIZED_JACKET, Item.LOOSE_FIT_PANTS),
      Set.of(Color.SILVER, Color.WHITE, Color.BLACK),
      Set.of(Print.GEOMETRIC, Print.SOLID)
  ),

  HIPHOP(
      "힙합",
      Set.of(Category.TOP, Category.PANTS),
      Set.of(Item.OVERSIZED_T_SHIRT, Item.CARGO_PANTS),
      Set.of(Color.BLACK, Color.WHITE),
      Set.of(Print.GEOMETRIC, Print.SOLID)
  ),

  KITSCH(
      "키치/키덜트",
      Set.of(Category.TOP, Category.DRESS),
      Set.of(Item.T_SHIRT),
      Set.of(Color.YELLOW, Color.RED, Color.BLUE),
      Set.of(Print.GEOMETRIC, Print.STRIPE)
  ),

  PUNK_ROCK(
      "펑크/로커",
      Set.of(Category.TOP, Category.PANTS, Category.JACKET),
      Set.of(Item.LEATHER_JACKET, Item.SKINNY_PANTS),
      Set.of(Color.BLACK, Color.RED),
      Set.of(Print.SOLID, Print.CHECK)
  ),

  CASUAL(
      "캐주얼",
      Set.of(Category.TOP, Category.PANTS, Category.JACKET),
      Set.of(Item.T_SHIRT, Item.JEANS, Item.CARDIGAN),
      Set.of(Color.BLUE, Color.WHITE, Color.GRAY),
      Set.of(Print.SOLID, Print.STRIPE)
  ),

  AVANT_GARDE(
      "아방가르드",
      Set.of(Category.TOP, Category.PANTS, Category.DRESS),
      Set.of(Item.ASYMMETRIC_DRESS, Item.OVERSIZED_JACKET),
      Set.of(Color.BLACK, Color.WHITE),
      Set.of(Print.GEOMETRIC, Print.ABSTRACT)
  ),

  STREET(
      "스트릿",
      Set.of(Category.TOP, Category.PANTS),
      Set.of(Item.HOODIE, Item.CARGO_PANTS, Item.OVERSIZED_T_SHIRT),
      Set.of(Color.BLACK, Color.WHITE, Color.GRAY),
      Set.of(Print.GEOMETRIC, Print.SOLID)
  );


  private final String koreanName;
  private final Set<Category> categories;
  private final Set<Item> items;
  private final Set<Color> colors;
  private final Set<Print> prints;

}
