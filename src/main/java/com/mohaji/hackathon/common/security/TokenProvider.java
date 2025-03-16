package com.mohaji.hackathon.common.security;

import com.mohaji.hackathon.common.error.enums.ErrorCode;
import com.mohaji.hackathon.common.error.exception.BusinessException;
import com.mohaji.hackathon.domain.auth.entity.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {

  private final RedisTemplate<String, String> redisTemplate;


  public static final String TOKEN_HEADER_NAME = "Authorization";
  private static final String AUTHORITIES_KEY = "auth";


  @Value("${spring.jwt.secret-key}")
  private String secret;
  @Value("${jwt.access-token-validity-in-seconds}")
  private long AccessTokenValidityInMilliSeconds;
  @Value("${jwt.refresh-token-validity-in-seconds}")
  private long RefreshTokenValidityInMilliSeconds;

  private static Key key;


  @Override
  public void afterPropertiesSet() {
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  public String createAccessToken(Authentication authentication) {
    long now = (new Date()).getTime();
    Date validity = new Date(now + this.AccessTokenValidityInMilliSeconds);
    return createToken(authentication, validity, true);
  }


  public String createRefreshToken(Authentication authentication, Long accountId) {
    long now = (new Date()).getTime();
    Date validity = new Date(now + this.RefreshTokenValidityInMilliSeconds);
    String refreshToken = createToken(authentication, validity, false);
    redisTemplate.opsForValue()
        .set(accountId.toString(), refreshToken, RefreshTokenValidityInMilliSeconds / 1000,
            TimeUnit.SECONDS);
    return refreshToken;
  }


  private String createToken(Authentication authentication, Date validity, boolean isAccess) {

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String authorities = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));


    String email;

    if (authentication.getPrincipal() instanceof Account account) {
      email = account.getEmail();
    } else if (authentication.getPrincipal() instanceof UserDetails userDetails) {
      email = userDetails.getUsername();
    } else {
      email = authentication.getName();
    }

    return Jwts.builder()
        .setSubject(email)
        .claim(AUTHORITIES_KEY, authorities)
        .signWith(key, SignatureAlgorithm.HS256)
        .setExpiration(validity)
        .claim("isAccess", isAccess)
        .compact();
  }


  public static Authentication getAuthentication(String token) {
    Claims claims = Jwts
        .parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();

    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .toList();

    return new UsernamePasswordAuthenticationToken(claims.getSubject(), token, authorities);
  }


  public static boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

      return true;
    } catch (SecurityException | MalformedJwtException e) {
      log.info("wrong jwt assignment");
      log.info("expired jwt token");
    } catch (ExpiredJwtException e) {
      Claims claims = e.getClaims();
      Boolean isAccess = claims.get("isAccess", Boolean.class);
      if (isAccess) {
        throw new BusinessException(ErrorCode.EXPIRED_ACCESS_TOKEN);
      } else {
        throw new BusinessException(ErrorCode.EXPIRED_REFRESH_TOKEN);
      }
    } catch (UnsupportedJwtException e) {
      log.info("unsupported jwt token");
    } catch (IllegalArgumentException e) {
      log.info("wrong jwt token");

    }
    return false;
  }


  public  String reissueToken(Authentication authentication, Long accountId, String refreshToken) {
    // Refresh Token 검증
    if (!validateToken(refreshToken)) {
      throw new BusinessException(ErrorCode.INVALID_JWT_TOKEN);
    }

    // Redis에 저장된 Refresh Token과 비교 검증
    String storedRefreshToken = redisTemplate.opsForValue().get(accountId.toString());
    if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
      throw new BusinessException(ErrorCode.INVALID_JWT_TOKEN);
    }

    // 새 액세스 토큰 발급
    return createAccessToken(authentication);
  }
}
