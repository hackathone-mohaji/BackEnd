package com.mohaji.hackathon.common.security;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohaji.hackathon.common.error.enums.ErrorCode;
import com.mohaji.hackathon.common.error.exception.BusinessException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@Slf4j
public class JwtFilter extends GenericFilterBean {


  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    String jwt = resolveToken(httpServletRequest);
    String requestURI = httpServletRequest.getRequestURI();

    if ("/api/v1/account/reissue".equals(requestURI)) {
      chain.doFilter(request, response);
      return;
    }

    try {
      if (StringUtils.hasText(jwt)) {
        if (TokenProvider.validateToken(jwt)) {
          Authentication authentication = TokenProvider.getAuthentication(jwt);
          SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
          log.info("유효한 Access 토큰이 없습니다, uri: {}", requestURI);
        }
      }
    } catch (BusinessException e) {
      ErrorCode errorCode = e.getErrorCode();

      // 응답에 상태 코드와 Content-Type 설정
      httpServletResponse.setStatus(
          HttpServletResponse.SC_BAD_REQUEST); // 예시로 BAD_REQUEST 사용, 필요에 따라 다른 코드로 변경 가능
      httpServletResponse.setContentType("application/json");
      httpServletResponse.setCharacterEncoding("UTF-8");

      // JSON 응답 작성
      String jsonResponse = new ObjectMapper().writeValueAsString(errorCode);
      httpServletResponse.getWriter().write(jsonResponse);
      httpServletResponse.getWriter().flush();

      return;
    }

    chain.doFilter(request, response);
  }

  // Request Header 에서 토큰 정보를 꺼내오기 위한 resolveToken 메소드
  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(TokenProvider.TOKEN_HEADER_NAME);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
