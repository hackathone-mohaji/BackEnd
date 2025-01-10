package com.mohaji.hackathon.domain.auth.controller;

import com.mohaji.hackathon.domain.auth.dto.ProfileResponseDTO;
import com.mohaji.hackathon.domain.auth.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "프로필 저장, 기존에 프로필이 있을경우 프로필을 새로 바꿔줌,MULTIPART_FORM_DATA로 이미지 넘겨야 함")
    public void setProfile(@RequestPart MultipartFile profile) throws IOException {
        profileService.setProfile(profile);
    }

    @GetMapping
    @Operation(summary = "로그인된 사용자의 프로필 이미지 url을 받음")
    public ProfileResponseDTO getProfile() throws IOException {
        return profileService.getProfile();
    }

}
