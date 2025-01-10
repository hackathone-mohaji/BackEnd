package com.mohaji.hackathon.domain.Image.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Where(clause = "state <> 100")
public class Image {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long parentId;

  private int kind;

  @Column(nullable = false)
  @ColumnDefault("0")
  private int state;

  @Column(nullable = false)
  private String originalFileName;

  @Column(nullable = false)
  private String storedFilePath;

  @Column(nullable = false)
  private Long fileSize;


  public void delete() {
    this.state = 100;
  }

}
