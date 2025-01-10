package com.mohaji.hackathon.domain.wear.repository;

import com.mohaji.hackathon.domain.wear.entity.Combination;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CombinationRepository extends JpaRepository<Combination, Long> {
  long countByAccountIdAndViewed(Long accountId, boolean viewed);
  Combination findRandomByAccountId(Long accountId);
    List<Combination> findAllByAccountId(Long accountId);
    long countByAccountId(Long accountId);

    List<Combination> findAllByAccountIdAndBookmarkedTrue(Long accountId);
}
