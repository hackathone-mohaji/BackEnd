package com.mohaji.hackathon.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohaji.hackathon.common.error.enums.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


import org.springframework.http.MediaType;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
  private final static ObjectMapper objectMapper = new ObjectMapper();

  //인증되지 않은 사용자에 대한 처리
  @Override
  public void commence(HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException) throws IOException {
    ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

    String jsonResponse = objectMapper.writeValueAsString(errorCode);


    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
    response.getWriter().write(jsonResponse);
    response.getWriter().flush();
    response.getWriter().close();
  }


}
