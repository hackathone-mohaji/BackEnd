package com.mohaji.hackathon.domain.wear.service;

import com.mohaji.hackathon.common.security.SecurityUtil;
import com.mohaji.hackathon.domain.auth.entity.Account;
import com.mohaji.hackathon.domain.wear.entity.Combination;
import com.mohaji.hackathon.domain.wear.repository.CombinationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

  private final CombinationRepository combinationRepository;
  private final SecurityUtil securityUtil;

  @Transactional
  public void setBookmark(Long combinationId) {
    // 현재 인증된 사용자 가져오기
    Account account = securityUtil.getAccount();
    // combinationId로 Combination 조회
    Combination combination = combinationRepository.findById(combinationId)
        .orElseThrow(() -> new RuntimeException("Combination not found with id: " + combinationId));

    // 사용자 권한 확인 (필요 시)
    if (!combination.getAccount().getId().equals(account.getId())) {
      throw new RuntimeException("You are not authorized to modify this combination.");
    }

    // bookmarked 필드 업데이트
    combination.setBookmarked(true);

    // 업데이트된 Combination 저장
    combinationRepository.save(combination);
  }

  public List<Combination> getBookMark() {
    Account account = securityUtil.getAccount();

    return combinationRepository.findAllByAccountIdAndBookmarkedTrue(account.getId());
  }

  @Transactional
  public List<Combination> unsetBookmark(Long combinationId) {
    // 현재 인증된 사용자 가져오기
    Account account = securityUtil.getAccount();

    // combinationId로 Combination 조회
    Combination combination = combinationRepository.findById(combinationId)
        .orElseThrow(() -> new RuntimeException("Combination not found with id: " + combinationId));

    // 사용자 권한 확인 (필요 시)
    if (!combination.getAccount().getId().equals(account.getId())) {
      throw new RuntimeException("You are not authorized to modify this combination.");
    }

    // bookmarked 필드 해제
    combination.setBookmarked(false);

    // 업데이트된 Combination 저장
    combinationRepository.save(combination);

    // 남아있는 북마크된 Combination 조회 및 반환
    return combinationRepository.findAllByAccountIdAndBookmarkedTrue(account.getId());
  }

}
