package com.mohaji.hackathon.domain.wear.controller;

import com.mohaji.hackathon.domain.wear.service.WearService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WearController {

    private final WearService wearService;

    @PostMapping(value = "/test", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Secured("ROLE_USER")
    public void createWear(@RequestPart MultipartFile file) throws IOException {
         wearService.saveImageAndAnalyzeDate(file);
    }
}
