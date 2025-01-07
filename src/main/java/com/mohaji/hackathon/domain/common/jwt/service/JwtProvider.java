package com.mohaji.hackathon.domain.common.jwt.service;



import com.mohaji.hackathon.domain.auth.entity.Account;
import com.mohaji.hackathon.domain.auth.repository.AccountRepository;
import com.mohaji.hackathon.domain.common.error.error.enums.ErrorCode;
import com.mohaji.hackathon.domain.common.error.error.exception.BusinessException;
import com.mohaji.hackathon.domain.common.jwt.config.JwtProperties;
import com.mohaji.hackathon.domain.common.jwt.dto.GeneratedTokenDTO;
import com.mohaji.hackathon.domain.common.jwt.dto.SecurityMemberDTO;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.Getter;
import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;



@Slf4j
@Service
@RequiredArgsConstructor
public class JwtProvider {
    private final JwtProperties jwtConfig;
    private final AccountRepository accountRepository;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @Getter
    private Key signingKey;
    private JwtParser jwtParser;

    //    private static final Long ACCESS_TOKEN_PERIOD = 1000L * 60L * 60L; // 1시간
    private static final Long ACCESS_TOKEN_PERIOD = 1000L * 60L * 60L * 24L * 31L; // 1시간
    private static final Long REFRESH_TOKEN_PERIOD = 1000L * 60L * 60L * 24L * 14L; // 2주

    @PostConstruct
    protected void init() {
        String secretKey = Base64.getEncoder().encodeToString(jwtConfig.getSecretKey().getBytes());
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        jwtParser = Jwts.parserBuilder().setSigningKey(signingKey).build();
    }

    @Transactional
    public GeneratedTokenDTO generateTokens(SecurityMemberDTO securityMemberDTO) {
        String accessToken = generateToken(securityMemberDTO, ACCESS_TOKEN_PERIOD);
        String refreshToken = generateToken(securityMemberDTO, REFRESH_TOKEN_PERIOD);

        saveRefreshToken(securityMemberDTO.getId(), refreshToken);

        return GeneratedTokenDTO.builder().accessToken(accessToken).refreshToken(refreshToken).status(200).build();
    }


    private String generateToken(SecurityMemberDTO securityMemberDTO, Long tokenPeriod) {
        Claims claims = Jwts.claims().setSubject("id");
        claims.put("email", securityMemberDTO.getEmail());
        claims.setId(String.valueOf(securityMemberDTO.getId()));
        Date now = new Date();

        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(new Date(now.getTime() + tokenPeriod)).signWith(signingKey, signatureAlgorithm).compact();
    }

    @Transactional
    public GeneratedTokenDTO reissueToken(String refreshToken) {
        GeneratedTokenDTO generatedTokenDTO;
        String reissuedRefreshToken;
        String reissuedAccessToken;
        Claims claims = verifyToken(refreshToken);
        SecurityMemberDTO securityMemberDTO = SecurityMemberDTO.fromClaims(claims);

        Optional<Account> findMember = accountRepository.findById(securityMemberDTO.getId());

        if (findMember.isEmpty()) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }

        Account member = findMember.get();

        if (member.getRefreshToken() == null) {
            throw new BusinessException(ErrorCode.MISMATCH_REFRESH_TOKEN);
        }

        if (!member.getRefreshToken().equals(refreshToken)) {
            throw new BusinessException(ErrorCode.MISMATCH_REFRESH_TOKEN);
        }

        reissuedRefreshToken = generateToken(securityMemberDTO, REFRESH_TOKEN_PERIOD);
        reissuedAccessToken = generateToken(securityMemberDTO, ACCESS_TOKEN_PERIOD);
        member.setRefreshToken(reissuedRefreshToken);

        accountRepository.save(member);

        generatedTokenDTO = GeneratedTokenDTO.builder().accessToken(reissuedAccessToken).refreshToken(reissuedRefreshToken).status(200).build();

        return generatedTokenDTO;
    }

    public Claims verifyToken(String token) {
        try {
            return jwtParser.parseClaimsJws(token).getBody();
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new JwtException("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            throw new JwtException("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            throw new JwtException("잘못된 JWT 토큰입니다.");
        }
    }

    private void saveRefreshToken(UUID id, String refreshToken) {
        Optional<Account> findMember = accountRepository.findById(id);
        findMember.ifPresent(member -> accountRepository.updateRefreshToken(member.getId(), refreshToken));
    }


}
