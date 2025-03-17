package com.mohaji.hackathon.domain.wear.service;


import com.mohaji.hackathon.common.error.enums.ErrorCode;
import com.mohaji.hackathon.common.error.exception.BusinessException;
import com.mohaji.hackathon.common.security.SecurityUtil;
import com.mohaji.hackathon.domain.Image.util.ClippingBgUtil;
import com.mohaji.hackathon.domain.Image.util.ImageUtil;
import com.mohaji.hackathon.domain.auth.entity.Account;
import com.mohaji.hackathon.domain.auth.repository.AccountRepository;
import com.mohaji.hackathon.domain.wear.dto.SwipeDto;
import com.mohaji.hackathon.domain.wear.dto.SwipeDto.wearDto;
import com.mohaji.hackathon.domain.wear.dto.WearDTO;
import com.mohaji.hackathon.domain.wear.dto.WearListResponseDto.WearResponseDto;
import com.mohaji.hackathon.domain.wear.entity.Combination;
import com.mohaji.hackathon.domain.wear.entity.CombinationWear;
import com.mohaji.hackathon.domain.wear.entity.Wear;
import com.mohaji.hackathon.domain.wear.enums.Att.Category;
import com.mohaji.hackathon.domain.wear.repository.CombinationRepository;
import com.mohaji.hackathon.domain.wear.repository.CombinationWearRepository;
import com.mohaji.hackathon.domain.wear.repository.WearRepository;
import com.mohaji.hackathon.domain.openai.service.GPTService;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
@RequiredArgsConstructor
@Slf4j
public class WearService {

  private final WearRepository wearRepository;
  private final GPTService gptService;
  private final ClippingBgUtil clippingBgUtil;
  private final ImageUtil imageUtil;
  private final CombinationWearRepository CombinationWearRepository;
  private final CombinationWearRepository combinationWearRepository;
  private final CombinationRepository combinationRepository;
  private final AccountRepository accountRepository;
  private final OutfitRecommendationService outfitRecommendationService;
  private final SecurityUtil securityUtil;


  @Transactional
  public void saveImageAndAnalyzeDate(MultipartFile imageFile) throws IOException {
    try {
      Account account = securityUtil.getAccount();

      //todo 이미지 누끼 주석 풀기
      // 1. 이미지 누끼 땀
      MultipartFile removeBackground = clippingBgUtil.removeBackground(imageFile);
      // 2. GPT에서 분석 결과 받기
      WearDTO wearDto = gptService.analyzeImage(removeBackground);
      log.info("Mapped WearDTO: {}", wearDto);

      // 3. Wear 엔티티 생성
      Wear wear = Wear.builder()
          .account(account)
          .color(wearDto.getColor())
          .category(wearDto.getCategory())
          .item(wearDto.getItem())
          .prints(wearDto.getPrint())
          .build();

      // 4. Wear 엔티티 저장 및 반환
      wearRepository.save(wear);

      // 5. 이미지 저장
      imageUtil.addImage(wear, removeBackground);

      if (!account.swipable) {
        boolean hasTop = false;
        boolean hasBottom = false;
        boolean hasOuterwear = false;
        boolean hasShoes = false;

        List<Wear> wears = wearRepository.findAllByAccountId(account.getId());
        wears.add(wear);
        for (Wear checkWear : wears) {
          if (checkWear.getCategory().equals(Category.TOP)) {
            hasTop = true;
          } else if (checkWear.getCategory().equals(Category.BOTTOM)) {
            hasBottom = true;
          } else if (checkWear.getCategory().equals(Category.OUTERWEAR)) {
            hasOuterwear = true;
          } else if (checkWear.getCategory().equals(Category.SHOES)) {
            hasShoes = true;
          }
        }
        if (hasTop && hasBottom && hasOuterwear && hasShoes) {

          outfitRecommendationService.recommendOutfit(1,account.getId());
          account.swipable = true;
          accountRepository.save(account);
        }
      }


    } catch (Exception e) {
      log.error("Error while mapping JSON to WearDTO", e);
      throw new RuntimeException("JSON 데이터 매핑 실패", e);
    }
  }

  @Transactional
  public void deleteWear(Long wearId) {
    //해당 옷이 해당 사용자의 소유인지 확인
    Wear wear = wearRepository.findById(wearId)
        .orElseThrow(() -> new BusinessException(ErrorCode.WEAR_NULL));

    Account account = securityUtil.getAccount();

    if (!account.getId().equals(wear.getAccount().getId())) {
      throw new BusinessException(ErrorCode.NOT_WEAR_OWN);
    }

    //콤비 옷 중간테이블 삭제
    CombinationWearRepository.deleteAllByWearId(wearId);
    //옷 이미지 삭제
    imageUtil.deleteImage(wear);
    //옷 삭제
    wearRepository.deleteById(wearId);

    //account의 swipable이 true일 경우
    //사용자로 wear 전체 조회
    //리스트에 현재 제거하려는 wear가 있다면 제거
    //반복 순회로 카테고리 조회
    //한 종류의 카테고리가 존재하면 상태 변수를 true로 바꿈
    //반복문 모두 순회
    //하나라도 false가 있다면, swipable false로 변경
    if (account.swipable) {
      boolean hasTop = false;
      boolean hasBottom = false;
      boolean hasOuterwear = false;
      boolean hasShoes = false;

      List<Wear> wears = wearRepository.findAllByAccountId(account.getId());
      wears.add(wear);
      for (Wear checkWear : wears) {
        if (checkWear.getCategory().equals(Category.TOP)) {
          hasTop = true;
        } else if (checkWear.getCategory().equals(Category.BOTTOM)) {
          hasBottom = true;
        } else if (checkWear.getCategory().equals(Category.OUTERWEAR)) {
          hasOuterwear = true;
        } else if (checkWear.getCategory().equals(Category.SHOES)) {
          hasShoes = true;
        }
      }
      if (!hasTop || !hasBottom || !hasOuterwear || !hasShoes) {
        account.swipable = false;
        accountRepository.save(account);
      }
    }

  }


  public List<WearResponseDto> listWearImage(String category) {

    Account account = securityUtil.getAccount();
    List<Wear> wears;
    if (category == null || category.isBlank()) {
      wears = wearRepository.findAllByAccountId(account.getId());
    } else {
      Category categoryEnum = Category.valueOf(category);
      wears = wearRepository.findAllByAccountIdAndCategory(account.getId(), categoryEnum);
    }

    return wears.stream()
        .map(wear -> new WearResponseDto(wear.getId(),
            imageUtil.imageUrl(wear.getImages().get(0), wear))).toList();


  }

  public Wear getWearInfo(Long wearId) {
    return wearRepository.findById(wearId).orElseThrow(()->new BusinessException(ErrorCode.WEAR_NULL));
  }


  @Transactional
  public SwipeDto swipe() {
    //로그인된 계정조회
    Account account = securityUtil.getAccount();

    if (!account.swipable) {
      return null;
    }

    // 자신의 옷 조합 중 DB에 미리 저장된 옷 조합에서 랜덤으로 하나 가져오기
    List<Combination> combinations = combinationRepository.findAllByAccountId(
        account.getId());

    Random random = new Random();

// 리스트 크기 내에서 무작위 인덱스 생성
    int randomIndex = random.nextInt(combinations.size());

// 무작위로 선택된 요소
    Combination combination = combinations.get(randomIndex);

    if (combinations == null) {
      throw new BusinessException(ErrorCode.COMBINATION_NULL);
    }

    combination.setViewed(true);

    //자신의 전체 옷 조합중 viewed가 true 인 것 수 a
    long viewedCount = combinationRepository.countByAccountIdAndViewed(account.getId(), true);

    //자신의 전체 옷 조합중 viewed false 인 것 수 b
    long unviewedCount = combinationRepository.countByAccountIdAndViewed(account.getId(), false);

    // a+b가 짝수일때 b-a 가 0 이거나 a+b가 홀수 일때 b-a 가 1 이라면 animation 키워드 반환
    long totalCount = viewedCount + unviewedCount;
    long difference = unviewedCount - viewedCount;

    List<Wear> wears = combinationWearRepository.findAllByCombinationId(combination.getId())
        .stream()
        .map(
            CombinationWear::getWear).toList();
    List<SwipeDto.wearDto> wearDtos = wears.stream()
        .map(wear -> new SwipeDto.wearDto(wear, imageUtil.imageUrl(wear.getImages().get(0), wear)))
        .toList();
    SwipeDto swipeDto = SwipeDto.builder()
        .wears(wearDtos)
        .reason(combination.getReason())
        .bookmarked(combination.bookmarked)
        .combinationId(combination.getId())
        .totalCount(totalCount)
        .build();

    // b-a 가 음수라면 gpt api를 통해 새로운 옷 조합 10개 생성 후 저장
    if (difference < 0) {

        outfitRecommendationService.recommendOutfit(10,account.getId());

    }

    // 옷 조합 반환

    return swipeDto;
  }

}






