package com.mohaji.hackathon.domain.wear.controller;


import com.mohaji.hackathon.common.error.enums.ErrorCode;
import com.mohaji.hackathon.common.error.exception.BusinessException;
import com.mohaji.hackathon.domain.wear.dto.SwipeDto;
import com.mohaji.hackathon.domain.wear.dto.WearListResponseDto;
import com.mohaji.hackathon.domain.wear.dto.WearResponseDto;
import com.mohaji.hackathon.domain.wear.entity.Combination;
import com.mohaji.hackathon.domain.wear.entity.Wear;
import com.mohaji.hackathon.domain.wear.service.BookmarkService;
import com.mohaji.hackathon.domain.wear.service.OutfitRecommendationService;
import com.mohaji.hackathon.domain.wear.service.TenService;
import com.mohaji.hackathon.domain.wear.service.WearService;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/wear")
public class WearController {

  private final WearService wearService;
  private final BookmarkService bookmarkService;


  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(summary = "이미지  리스트 저장, MULTIPART_FORM_DATA로 이미지 보내면 누끼 따서 분석하고 저장함")
  @Secured("ROLE_USER")
  public void createWear(@RequestPart List<MultipartFile> files) throws IOException {
    wearService.saveImageAndAnalyzeDate(files);
  }

  @DeleteMapping("/{wearId}")
  @Secured("ROLE_USER")
  @Operation(summary = "리스트 조회할때 반환받은 wearId를 \\/ 와 같이 뒤에 붙여서 보내면 해당 옷이 삭제 됨")
  public void deleteWear(@PathVariable Long wearId) {
    wearService.deleteWear(wearId);
  }

  @GetMapping
  @Secured("ROLE_USER")
  @Operation(summary = "로그인한 사용자의 이미지를 반환함, 쿼리 파라미터로 category를 넘기면 필터링됨")
  public ResponseEntity<WearListResponseDto> listWearImage(
      @RequestParam(required = false) String category) {

    List<WearListResponseDto.WearResponseDto> wearResponseDtos = wearService.listWearImage(category);
    WearListResponseDto wearListResponseDto = new WearListResponseDto(wearResponseDtos);

    return ResponseEntity.ok(wearListResponseDto);
  }


  @GetMapping("/{wearId}")
  @Secured("ROLE_USER")
  @Operation(summary = "옷 개별 조회")
  public ResponseEntity<WearResponseDto> getWearInfo(@RequestParam Long wearId) {

    Wear wear =  wearService.getWearInfo(wearId);
    WearResponseDto wearResponseDto = new WearResponseDto(wear);

    return ResponseEntity.ok(wearResponseDto);

  }


  @PatchMapping
  @Secured("ROLE_USER")
  @Operation(summary = "조합 반환(swipe)")
  public ResponseEntity<SwipeDto> swipe() { // ResponseEntity<?>로 변경
    log.info("요청 들어옴");
    SwipeDto swipe = wearService.swipe();

    if (swipe == null) {
      throw new BusinessException(ErrorCode.WEAR_NULL);
    }
    return ResponseEntity.ok(swipe); // SwipeDto 반환
  }


  @PostMapping("/bookmark")
  public void setBookMark(@RequestParam("combinationId") Long combinationId) {
    bookmarkService.setBookmark(combinationId);
  }

  @PostMapping("/bookmark/unset")
  public List<Combination> unsetBookmark(@RequestParam("combinationId") Long combinationId) {
    return bookmarkService.unsetBookmark(combinationId);
  }

  @GetMapping("/bookmark")
  public List<Combination> getBookMark() {
    return bookmarkService.getBookMark();
  }


/*    @GetMapping("/test")
    public GPTRecommendationResponseDTO test(){
        return outfitRecommendationService.recommendOutfit();
    }

    @GetMapping("/test22222")
    public GPTRecommendationResponseDTO test2(){
        return tenService.TenrecommendOutfit();
    }*/
}

