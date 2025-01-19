package com.mohaji.hackathon.common.security;

import com.mohaji.hackathon.common.error.enums.ErrorCode;
import com.mohaji.hackathon.common.error.exception.BusinessException;
import com.mohaji.hackathon.domain.auth.entity.Account;
import com.mohaji.hackathon.domain.auth.repository.AccountRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SecurityUtil {

  private final AccountRepository accountRepository;

  public Account getAccount() {
    return getCurrentEmail().flatMap(
        accountRepository::findByEmail).orElseThrow(()->new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
  }

  public static Optional<String> getCurrentEmail() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null) {
      log.debug("Security Context에 인증 정보가 없습니다.");
      return Optional.empty();
    }

    String email = null;
    if (authentication.getPrincipal() instanceof Account springSecurityUser) {
      email = springSecurityUser.getEmail();
    } else if (authentication.getPrincipal() instanceof String e) {
      email = e;
    }

    return Optional.ofNullable(email);
  }


}
