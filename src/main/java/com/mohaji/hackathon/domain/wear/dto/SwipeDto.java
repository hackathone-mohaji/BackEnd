package com.mohaji.hackathon.domain.wear.dto;


import com.mohaji.hackathon.domain.wear.entity.Wear;
import com.mohaji.hackathon.domain.wear.enums.Att.Category;
import com.mohaji.hackathon.domain.wear.enums.Att.Color;
import com.mohaji.hackathon.domain.wear.enums.Att.Item;
import com.mohaji.hackathon.domain.wear.enums.Att.Print;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SwipeDto {

  private List<SwipeDto.wearDto> wears;

  private Long combinationId;

  private String reason;

  private boolean bookmarked;
  @Setter
  private boolean animation;



  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class wearDto {

    public wearDto(Wear wear,String wearImageUrl) {
      this.wearImageUrl = wearImageUrl;
      this.color = wear.getColor();
      this.category = wear.getCategory();
      this.prints = wear.getPrints();
      this.item = wear.getItem();
    }

    private String wearImageUrl;
    private Color color;
    private Category category;
    private Print prints;
    private Item item;
  }


}
