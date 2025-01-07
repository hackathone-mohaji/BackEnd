package com.mohaji.hackathon.domain.entity.classification;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Getter
public abstract class Classification {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;


  private com.mohaji.hackathon.common.enums.Classification classification;

}
