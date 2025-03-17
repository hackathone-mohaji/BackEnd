package com.mohaji.hackathon.domain.wear.repository;

import com.mohaji.hackathon.domain.auth.entity.Account;
import com.mohaji.hackathon.domain.wear.entity.Wear;
import com.mohaji.hackathon.domain.wear.enums.Att.Category;
import java.util.List;
import java.util.Optional;
import jdk.dynalink.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WearRepository extends JpaRepository<Wear, Long> {

  List<Wear> findAllByAccountId(Long accountId);
  List<Wear> findAllByAccountIdAndCategory(Long account_id, Category category);
}
