package com.scor.rr.proxy.config.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.kerberos.web.authentication.SpnegoEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestSpnegoEntryPoint  extends SpnegoEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)
            throws IOException, ServletException {
        response.addHeader("WWW-Authenticate", "Negotiate");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

}