package com.mohaji.hackathon.domain.Image.util;


import com.mohaji.hackathon.domain.Image.config.ImageConfig;
import com.mohaji.hackathon.domain.Image.enums.ImageKind;
import com.mohaji.hackathon.domain.Image.repository.ImageRepository;
import com.mohaji.hackathon.domain.wear.entity.ImageEntity;
import com.mohaji.hackathon.domain.Image.entity.Image;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
@RequiredArgsConstructor
public  class ImageUtil {


    private final ImageRepository imageRepository;
    private final ClippingBgUtil clippingBgUtil;




    /**
     * 이미지 추가 (이미지 리스트)// 수정로직에서 사용하면 기존에 존재하는 이미지를 삭제처리하고 추가
     **/
    @Transactional
    public  <T extends ImageEntity> void addImage(
            T entity,
            List<MultipartFile> multipartFiles) throws IOException {
        for (MultipartFile multipartFile : multipartFiles) {
            validateImage(multipartFile);
        }
        ImageKind imageKind = ImageKind.fromEntity(entity);

        if (entity == null || entity.getId() == null) {
            return;
        }

        List<Image> imageList = parseImageInfo(entity.getId(), multipartFiles, imageKind);

        if (entity.getImages() == null) {
            entity.setImages(new ArrayList<>());
        }
        if (!imageList.isEmpty()) {
            entity.getImages().addAll(imageList);
        }

    }
    /**
     *  이미지 추가 (단일 이미지)// 수정로직에서 사용하면 기존에 존재하는 이미지를 논리적 삭제처리하고 추가
     **/
    //배경 지워짐
    @Transactional
    public  <T extends ImageEntity> void addImage(
            T entity,
            MultipartFile multipartFile) throws IOException {
        validateImage(multipartFile);

        ImageKind imageKind = ImageKind.fromEntity(entity);

        if (entity == null || entity.getId() == null) {
            return;
        }

        Image image = parseImageInfo(entity.getId(), multipartFile, imageKind);

        if (entity.getImages() == null) {
            entity.setImages(new ArrayList<>());
        }
        if (!multipartFile.isEmpty()) {
            entity.getImages().add(image);
        }

    }


    // 이미지 조회 url 만들어서 반환하는 메서드
    // todo: 이미지는 항상 리스트임으로 삭제된 항목 제거후 조회하는 매서드 만들고 나서 리스트로 반환
    // fixme:  parentId와 filename이 같다면 잘못된 이미지 조회가 일어날 수 있다.// 이미지를 저장할때 parentId를 암호화 한다면 보안 + 이 문제가 해결된다
    public  <T extends ImageEntity> String imageUrl(Image image, T entity) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        String extension = StringUtils.getFilenameExtension(image.getOriginalFileName());
        String hashedFilename = hashFileName(
                image.getId().toString() + "_" + image.getParentId() + "_" + image.getOriginalFileName());
        String newFilename = hashedFilename + (extension != null ? "." + extension : "");
        return baseUrl + "/gen/" + ImageKind.fromEntity(entity).getDirName() + "/" + newFilename;
    }


    //이미지  삭제시키는 메서드
    //todo: 실제 이미지도 삭제되도록 해야함
    public  <T extends ImageEntity> void deleteImage(T entity) {
        if (entity != null) {
            imageRepository.deleteAll(entity.getImages());
        }
    }




    /*----------------------private 매서드-------------------------*/


    // 이미지객체로 변환, db저장(이미지 리스트)
    private  List<Image> parseImageInfo(Long parentId, List<MultipartFile> multipartFiles,
                                        ImageKind imageKind) throws IOException {


        // if empty, return null
        if (multipartFiles == null || multipartFiles.isEmpty()) {
            return new ArrayList<>();
        }

        // new files
        List<Image> newFileList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                Image image = Image.builder()
                        .parentId(parentId)
                        .kind(imageKind.getId())
                        .originalFileName(multipartFile.getOriginalFilename())
                        .storedFilePath(imageKind.getDirName())
                        .fileSize(multipartFile.getSize())
                        .build();
                imageRepository.save(image);
                newFileList.add(image);
                saveFile(parentId, multipartFile, image);
            }
        }
        return newFileList;
    }

    // 이미지객체로 변환, db저장(단일 이미지)
    private Image parseImageInfo(Long parentId, MultipartFile multipartFile,
                                 ImageKind imageKind) throws IOException {


        // if empty, return null
        if (multipartFile == null || multipartFile.isEmpty()) {
            return new Image();
        }

        // new files
        Image image = Image.builder()
                .parentId(parentId)
                .kind(imageKind.getId())
                .originalFileName(multipartFile.getOriginalFilename())
                .storedFilePath(imageKind.getDirName())
                .fileSize(multipartFile.getSize())
                .build();
        imageRepository.save(image);
        saveFile(parentId, multipartFile, image);

        return image;
    }

    //확장자 및 파일 크기 검사
    private  void validateImage(MultipartFile multipartFile) throws IOException {
        String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        //확장자 검사
        if (extension == null || !ImageConfig.getAllowedExtensions()
                .contains(extension.toLowerCase())) {
            throw new IOException(
                    "확장자 오류(업로드 가능 이미지 확장자 : " + ImageConfig.getAllowedExtensions() + " ,현재 파일 확장자 : "
                            + extension + ")");
        }
        //파일 크기 검사
        if (multipartFile.getSize() > ImageConfig.getMaxFileSize()) {
            throw new IOException(
                    "파일 크기 오류, 업로드 가능 최대 파일 크기 :" + ImageConfig.getMaxFileSize() + ",현재 파일 크기 :  "
                            + multipartFile.getSize() + ")");
        }
    }

    //실제 파일 저장
    private  void saveFile(Long parentId, MultipartFile multipartFile, Image image)
            throws IOException {

        Path dirPath = Paths.get(ImageConfig.getImageDirectory(), image.getStoredFilePath());
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        if (!multipartFile.isEmpty()) {
            String originalFilename = multipartFile.getOriginalFilename();
            String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
            String hashedFilename = hashFileName(
                    image.getId().toString() + "_" + parentId + "_" + originalFilename);
            String newFilename = hashedFilename + (extension != null ? "." + extension : "");
            Path filePath = dirPath.resolve(newFilename);
            Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        }
    }

    // 해싱 메서드 (SHA-256 기반)
    private  String hashFileName(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing file name", e);
        }
    }

}