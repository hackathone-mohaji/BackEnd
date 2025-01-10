package com.mohaji.hackathon.domain.wear.repository;

import com.mohaji.hackathon.domain.auth.entity.Account;
import com.mohaji.hackathon.domain.wear.entity.Wear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WearRepository extends JpaRepository<Wear, Long> {


}
