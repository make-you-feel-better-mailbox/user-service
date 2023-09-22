package com.onetwo.userservice.jwt;

import com.onetwo.userservice.common.exceptions.TokenValidationException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private TokenProvider tokenProvider;

    public JwtTokenFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    /**
     * JWT를 검증하는 필터
     * HttpServletRequest 의 Authorization 헤더에서 JWT token을 찾고 그것이 맞는지 확인
     * UsernamePasswordAuthenticationFilter 앞에서 작동
     * (JwtTokenFilterConfigurer 참고)
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveToken(request, AUTHORIZATION_HEADER);
        String contentType = request.getContentType();

        try {
            if (jwt != null && tokenProvider.validateToken(jwt)) {
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("set Authentication to security context for '{}', uri: {}", authentication.getName(), request.getRequestURI());
            } else if ("application/json".equals(contentType) && jwt == null) {
                throw new TokenValidationException("JWT 토큰이 잘못되었습니다.");
            }
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", e);
            log.info("ExpiredJwtException : {}", e.getMessage());
        } catch (JwtException | IllegalArgumentException e) {
            request.setAttribute("exception", e);
            log.info("jwtException : {}", e.getMessage());
        } catch (TokenValidationException e) { // CustomErrorAttributes 에서 Response JSON 을 설정하여 응답
            request.setAttribute("TokenValidationException", e); // ResponseException 객체 CustomErrorAttributes 에게 전달
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request, String header) {
        String bearerToken = request.getHeader(header);
        if (bearerToken != null) return bearerToken;
        return null;
    }


}
