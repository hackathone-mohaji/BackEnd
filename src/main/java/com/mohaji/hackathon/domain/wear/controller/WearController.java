package com.mohaji.hackathon.domain.wear.controller;

import com.mohaji.hackathon.domain.wear.dto.GPTRecommendationResponseDTO;
import com.mohaji.hackathon.domain.wear.dto.OutfitRecommendationDTO;
import com.mohaji.hackathon.domain.wear.service.OutfitRecommendationService;
import com.mohaji.hackathon.domain.wear.service.WearService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/wear")
public class WearController {

    private final WearService wearService;
    private final OutfitRecommendationService outfitRecommendationService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Secured("ROLE_USER")
    public void createWear(@RequestPart MultipartFile file) throws IOException {
         wearService.saveImageAndAnalyzeDate(file);
    }

    @DeleteMapping("{wearId}")
    @Secured("ROLE_USER")
    public void deleteWear(@RequestParam Long wearId) throws IOException {
         wearService.deleteWear(wearId);
    }
}
