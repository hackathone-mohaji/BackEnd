package com.mohaji.hackathon.domain.auth.service;
import com.mohaji.hackathon.domain.auth.dto.FindRequestDTO;
import com.mohaji.hackathon.domain.auth.dto.LoginRequestDTO;
import com.mohaji.hackathon.domain.auth.dto.SignUpRequestDTO;
import com.mohaji.hackathon.domain.auth.dto.SignUpResponseDTO;
import com.mohaji.hackathon.domain.auth.entity.Account;
import com.mohaji.hackathon.domain.auth.repository.AccountRepository;
import com.mohaji.hackathon.common.error.enums.ErrorCode;
import com.mohaji.hackathon.common.error.exception.BusinessException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final HttpSessionSecurityContextRepository securityContextRepository;

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

    public void login(LoginRequestDTO loginRequestDTO, HttpServletRequest req, HttpServletResponse res) {
        try {
            UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                    loginRequestDTO.getEmail(),
                    loginRequestDTO.getPassword()
                );

            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
            context.setAuthentication(authentication);
            securityContextRepository.saveContext(context, req, res);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("로그인 인증에 실패했습니다");
        }
    }
 /*   @Transactional
    public void login(LoginRequestDTO loginRequestDTO) {
        Optional<Account> findEmail = accountRepository.findByEmail(loginRequestDTO.getEmail());

        if (findEmail.isPresent()) {
            Account account = findEmail.get();
            if (passwordEncoder.matches(loginRequestDTO.getPassword(), account.getPassword())) {
                SecurityMemberDTO securityMemberDTO = SecurityMemberDTO.builder()
                        .id(account.getId())
                        .email(account.getEmail())
                        .username(account.getUsername())
                        .build();


            } else {
                throw new BusinessException(ErrorCode.INVALID_PASSWORD);
            }

        } else {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }*/

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
