package com.mohaji.hackathon.domain.wear.repository;

import com.mohaji.hackathon.domain.wear.entity.CombinationWear;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CombinationWearRepository extends JpaRepository<CombinationWear, Long> {
  void deleteAllByWearId(Long wearId);
}
