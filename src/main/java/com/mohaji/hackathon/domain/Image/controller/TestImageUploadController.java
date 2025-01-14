//package com.mohaji.hackathon.domain.Image.controller;
//
//
//import com.mohaji.hackathon.domain.Image.util.ImageUtil;
//import com.mohaji.hackathon.domain.wear.entity.Wear;
//import com.mohaji.hackathon.domain.wear.repository.WearRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/images")
//@RequiredArgsConstructor
//public class TestImageUploadController {
//
//    private final ImageUtil imageUtil;
//    private final WearRepository wearRepository;
//
////    private static final UUID wearId = UUID.fromString("3e21b7c1-51f8-4bd8-80d6-5bfa22c4728b");
//    private static final Long wearId = Long.valueOf(1);
//
//
//
////    @PostMapping(value = "/multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
////    public ResponseEntity<String> addMultipleImages(
////            @RequestParam("files") List<MultipartFile> files) {
////        try {
////            // 엔티티 객체를 ID로 가져오는 로직 필요
////            Wear entity = wearRepository.findById(wearId).orElse(null);
////
////            if (entity == null) {
////                return ResponseEntity.badRequest().body("Entity not found");
////            }
////
////            imageUtil.addImage(entity, files);
////            return ResponseEntity.ok("Images added successfully");
////        } catch (IOException e) {
////            return ResponseEntity.status(500).body("Error adding images: " + e.getMessage());
////        }
////    }
////
////    /**
////     * 단일 이미지 추가 API
////     *
////     * @param file 이미지 파일
////     * @return 성공 메시지
////     */
////
////
////    @PostMapping(value = "/single", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
////    public ResponseEntity<String> addSingleImage(
////            @RequestParam("file") MultipartFile file) {
////        try {
////            // 엔티티 객체를 ID로 가져오는 로직 필요
////            Wear entity = wearRepository.findById(wearId).orElse(null);
////            if (entity == null) {
////                return ResponseEntity.badRequest().body("Entity not found");
////            }
////
////            imageUtil.addImage(entity, file);
////            return ResponseEntity.ok("Image added successfully");
////        } catch (IOException e) {
////            return ResponseEntity.status(500).body("Error adding image: " + e.getMessage());
////        }
////    }
//
//    // 이미지 URL 조회
//    @GetMapping
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public ResponseEntity<List<String>> getImageUrls() {
//        // parentId에 해당하는 Wear 엔티티 가져오기
//        Wear entity = wearRepository.findById(wearId).orElse(null);
//        if (entity == null) {
//            return ResponseEntity.badRequest().body(Collections.emptyList());
//        }
//        System.out.println("entity.getImages() = " + entity.getImages());
//        // 이미지 URL 리스트 생성
//        List<String> imageUrls = entity.getImages().stream()
//                .map(image -> imageUtil.imageUrl(image, entity))
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(imageUrls);
//    }
//}
