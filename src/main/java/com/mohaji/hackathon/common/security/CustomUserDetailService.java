package com.mohaji.hackathon.common.security;


import com.mohaji.hackathon.domain.auth.dto.LoginRequestDTO;
import com.mohaji.hackathon.domain.auth.entity.Account;
import com.mohaji.hackathon.domain.auth.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
  private final AccountRepository accountRepository;
  @Override
  public Account loadUserByUsername(String email) throws UsernameNotFoundException {
    return accountRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다"));
  }


}
