package com.mohaji.hackathon.domain.auth.service;

import com.mohaji.hackathon.common.error.enums.ErrorCode;
import com.mohaji.hackathon.common.error.exception.BusinessException;
import com.mohaji.hackathon.domain.Image.entity.Image;
import com.mohaji.hackathon.domain.Image.util.ImageUtil;
import com.mohaji.hackathon.domain.auth.dto.ProfileResponseDTO;
import com.mohaji.hackathon.domain.auth.entity.Account;
import com.mohaji.hackathon.domain.auth.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ImageUtil imageUtil;
    private final AccountRepository accountRepository;

    @Transactional
    public void setProfile(MultipartFile profile) throws IOException {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        account = accountRepository.findById(account.getId()).orElseThrow(()-> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));


        imageUtil.addImage(account,profile);
    }


    public ProfileResponseDTO getProfile() throws IOException {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        account = accountRepository.findById(account.getId()).orElseThrow(()-> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
        Image image = account.getImages().get(0);
        String imageUrl = imageUtil.imageUrl(image, account);
        String username = account.getUsername();
        return ProfileResponseDTO.builder()
                .imageUrl(imageUrl)
                .username(username)
                .build();
    }




}
