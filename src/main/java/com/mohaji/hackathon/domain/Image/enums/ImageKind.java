package com.mohaji.hackathon.domain.Image.enums;

import com.mohaji.hackathon.domain.wear.entity.ImageEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageKind {
    DEFAULT(0,"image/default","기타 이미지"),
    WEAR(1,"image/wear","옷"),
    ;

    private Integer id;
    private String dirName;
    private String description;


    public static <T extends ImageEntity> ImageKind fromEntity(T entity) {
        if (entity == null) {
            return DEFAULT;
        }
        //자바 버전이 낮아서 switch-case 문에서 Class<?> 를 사용할 수 없고 대신 이름으로 해야 함
        return switch (entity.getClass().getSimpleName()) {
            case "Wear" -> WEAR;
            default -> DEFAULT;
        };
    }

}
