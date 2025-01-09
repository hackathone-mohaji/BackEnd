//package com.mohaji.hackathon.domain.wear.controller;
//
//import com.mohaji.hackathon.domain.wear.dto.WearDTO;
//import com.mohaji.hackathon.domain.wear.service.WearService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestPart;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@RestController
//@RequiredArgsConstructor
//public class WearController {
//
//    private final WearService wearService;
//
//    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<WearDTO> uploadWear(
//            @RequestPart(value = "image") MultipartFile imageFile) throws IOException {
//        WearDTO wearDTO = wearService.saveWear(imageFile);
//        return ResponseEntity.ok(wearDTO);
//    }
//}
