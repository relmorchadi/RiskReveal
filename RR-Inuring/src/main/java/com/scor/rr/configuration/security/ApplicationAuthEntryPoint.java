package com.scor.rr.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;

@Component
public class ApplicationAuthEntryPoint implements AuthenticationEntryPoint {

    private @Autowired
    ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString());
        response.getWriter().write(
                this.objectMapper.writeValueAsString(
                        "Authentication error"
                )
        );
    }
}