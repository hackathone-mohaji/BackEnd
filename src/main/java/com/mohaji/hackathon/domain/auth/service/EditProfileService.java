package com.mohaji.hackathon.domain.auth.service;

import com.mohaji.hackathon.domain.Image.entity.Image;
import com.mohaji.hackathon.domain.Image.util.ImageUtil;
import com.mohaji.hackathon.domain.auth.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EditProfileService {

    private final ImageUtil imageUtil;

    @Transactional
    public void setProfile(MultipartFile profile) throws IOException {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        imageUtil.addImage(account,profile);
    }


    public String getProfile() throws IOException {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Image image = account.getImages().get(0);
        return imageUtil.imageUrl(image,account);
    }



}
