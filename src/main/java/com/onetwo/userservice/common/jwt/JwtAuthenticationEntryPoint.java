package com.onetwo.userservice.common.jwt;

import com.onetwo.userservice.common.GlobalStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Exception exception = (Exception) request.getAttribute("exception");
        if (exception != null) {
            if (Arrays.stream(JwtCode.values()).anyMatch(e -> e.getValue().equals(exception.getMessage())))
                response.setHeader(GlobalStatus.TOKEN_VALIDATION_HEADER, exception.getMessage());

            response.sendError(HttpStatus.SC_UNAUTHORIZED, exception.getMessage());
        } else {
            if (Arrays.stream(JwtCode.values()).anyMatch(e -> e.getValue().equals(authException.getMessage())))
                response.addHeader(GlobalStatus.TOKEN_VALIDATION_HEADER, authException.getMessage());
            response.sendError(HttpStatus.SC_UNAUTHORIZED, authException.getMessage());
        }
    }
}
