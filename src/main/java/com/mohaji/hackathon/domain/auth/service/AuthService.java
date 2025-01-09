package com.mohaji.hackathon.domain.auth.service;


import com.mohaji.hackathon.domain.auth.dto.FindRequestDTO;
import com.mohaji.hackathon.domain.auth.dto.LoginRequestDTO;
import com.mohaji.hackathon.domain.auth.dto.SignUpRequestDTO;
import com.mohaji.hackathon.domain.auth.dto.SignUpResponseDTO;
import com.mohaji.hackathon.domain.auth.entity.Account;
import com.mohaji.hackathon.domain.auth.repository.AccountRepository;
import com.mohaji.hackathon.common.error.enums.ErrorCode;
import com.mohaji.hackathon.common.error.exception.BusinessException;
import com.mohaji.hackathon.common.jwt.dto.GeneratedTokenDTO;
import com.mohaji.hackathon.common.jwt.dto.SecurityMemberDTO;
import com.mohaji.hackathon.common.jwt.provider.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AccountRepository accountRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public SignUpResponseDTO signUp(SignUpRequestDTO signUpRequestDTO) {
        Optional<Account> findEmail = accountRepository.findByEmail(signUpRequestDTO.getEmail());
        if (findEmail.isPresent()) {
            throw new BusinessException(ErrorCode.MEMBER_PROFILE_DUPLICATION);
        }

        String hashedPassword = passwordEncoder.encode(signUpRequestDTO.getPassword());
        Account account = Account.builder()
                .username(signUpRequestDTO.getName())
                .email(signUpRequestDTO.getEmail())
                .password(hashedPassword)
                .build();

        accountRepository.save(account);
        return SignUpResponseDTO.builder()
                .name(account.getUsername())
                .email(account.getEmail())
                .status(200)
                .build();
    }

    @Transactional
    public GeneratedTokenDTO login(LoginRequestDTO loginRequestDTO) {
        Optional<Account> findEmail = accountRepository.findByEmail(loginRequestDTO.getEmail());

        if (findEmail.isPresent()) {
            Account account = findEmail.get();
            if (passwordEncoder.matches(loginRequestDTO.getPassword(), account.getPassword())) {
                SecurityMemberDTO securityMemberDTO = SecurityMemberDTO.builder()
                        .id(account.getId())
                        .email(account.getEmail())
                        .username(account.getUsername())
                        .build();

                return jwtProvider.generateTokens(securityMemberDTO);
            } else {
                throw new BusinessException(ErrorCode.INVALID_PASSWORD);
            }

        } else {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }

    @Transactional
    public boolean duplicateEmail(String requsetEmail) {
        Optional<Account> findEmail = accountRepository.findByEmail(requsetEmail);

        return findEmail.isPresent();

    }

//    @Transactional
//    public FindEmailResponseDTO findEmail(String name, String phoneNumber) {
//        Optional<Member> findMember = memberRepository.findByNameAndPhoneNumber(name, phoneNumber);
//
//        if (findMember.isPresent()) {
//            Member findEmailMember = findMember.get();
//            return FindEmailResponseDTO.builder()
//                    .email(findEmailMember.getEmail())
//                    .status(200)
//                    .build();
//        } else {
//            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
//        }
//
//    }

    @Transactional
    public void resetPassword(FindRequestDTO.ResetPassword passwordRequestDTO) {
        Optional<Account> findEmail = accountRepository.findByEmail(passwordRequestDTO.getEmail());

        findEmail.ifPresent(account -> {
            String hashedPassword = passwordEncoder.encode(passwordRequestDTO.getPassword());
            account.changePassword(hashedPassword);
            accountRepository.save(account);
        });
        if (findEmail.isEmpty()) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }


//    @Transactional
//    public void deleteMember() throws ExecutionException, InterruptedException {
//        Long memberId = memberService.getCurrentMember().getId();
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_MEMBER_ID));
//
//        memberService.deleteMember();
//    }


//    @Transactional
//    public void logout() {
//        Long currentMember = memberService.getCurrentMember().getId();
//
//        accountRepository.invalidateRefreshToken(currentMember);
//    }
}
