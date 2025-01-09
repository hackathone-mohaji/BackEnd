package com.mohaji.hackathon.domain.wear.repository;

import com.mohaji.hackathon.domain.wear.entity.Wear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WearRepository extends JpaRepository<Wear, Long> {
}
