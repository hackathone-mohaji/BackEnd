package com.mohaji.hackathon.domain.wear.entity;

import com.mohaji.hackathon.domain.Image.entity.Image;
import java.util.List;
import java.util.UUID;

public interface ImageEntity {
    Long getId();
    List<Image> getImages();
    void setImages(List<Image> images);

}
