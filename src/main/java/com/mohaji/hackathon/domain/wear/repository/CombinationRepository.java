package com.mohaji.hackathon.domain.wear.repository;

import com.mohaji.hackathon.domain.wear.entity.Combination;
import com.mohaji.hackathon.domain.wear.entity.CombinationWear;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CombinationRepository extends JpaRepository<Combination, Long> {
  long countByAccountIdAndViewed(Long accountId, boolean viewed);
  Combination findRandomByAccountId(Long accountId);

}
