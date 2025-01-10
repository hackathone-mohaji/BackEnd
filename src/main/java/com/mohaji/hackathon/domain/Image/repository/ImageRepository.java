package com.mohaji.hackathon.domain.Image.repository;

import com.mohaji.hackathon.domain.Image.entity.Image;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image , Long> {

}
