package com.mohaji.hackathon.domain.wear.repository;

import com.mohaji.hackathon.domain.wear.entity.CombinationWear;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CombinationWearRepository extends JpaRepository<CombinationWear, Long> {
  void deleteAllByWearId(Long wearId);
  List<CombinationWear> findAllByCombinationId(Long combinationId);
  @Query("SELECT cw.combination.id FROM CombinationWear cw WHERE cw.wear.id = :wearId")
  Optional<Long> findCombinationIdByWearId(@Param("wearId") Long wearId);
}
