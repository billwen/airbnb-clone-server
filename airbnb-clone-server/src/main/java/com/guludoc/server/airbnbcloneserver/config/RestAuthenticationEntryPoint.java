package com.guludoc.server.airbnbcloneserver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guludoc.server.airbnbcloneserver.entity.dto.RestErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.OutputStream;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("WWW-Authenticate", "Basic realm=SignIn");

        RestErrorResponse re = RestErrorResponse.of(
                HttpStatus.UNAUTHORIZED.toString(),
                authException.getLocalizedMessage(),
                ExceptionUtils.getStackTrace(authException));

        ObjectMapper mapper = new ObjectMapper();
        OutputStream outputStream = response.getOutputStream();
        mapper.writeValue(outputStream, re);
        outputStream.flush();
    }
}
