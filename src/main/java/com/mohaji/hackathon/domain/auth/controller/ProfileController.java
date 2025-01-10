package com.mohaji.hackathon.domain.auth.controller;

import com.mohaji.hackathon.domain.auth.service.EditProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProfileController {
    private final EditProfileService editProfileService;

    @PostMapping(value = "/set/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void setProfile(@RequestPart MultipartFile profile) throws IOException {
        editProfileService.setProfile(profile);
    }

    @GetMapping("/get/profile")
    public String getProfile() throws IOException {
        return editProfileService.getProfile();
    }
}
