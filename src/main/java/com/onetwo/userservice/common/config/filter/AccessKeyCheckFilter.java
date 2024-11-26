package com.onetwo.userservice.common.config.filter;

import com.onetwo.userservice.common.GlobalStatus;
import com.onetwo.userservice.common.config.SecurityConfig;
import com.onetwo.userservice.common.exceptions.BadRequestException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
public class AccessKeyCheckFilter extends OncePerRequestFilter {

    @Value("${access-id}")
    private String accessId;

    @Value("${access-key}")
    private String accessKey;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestAccessId = request.getHeader(GlobalStatus.ACCESS_ID);
        String requestAccessKey = request.getHeader(GlobalStatus.ACCESS_KEY);

        boolean hasWhiteList = Arrays.stream(SecurityConfig.WHITE_LIST).anyMatch(e -> request.getRequestURI().contains(e));
        
        if (!hasWhiteList && (!accessId.equals(requestAccessId) || !accessKey.equals(requestAccessKey)))
            throw new BadRequestException("access-id or access-key does not matches");

        log.info("Server Access-id and Access-Key check passed");

        filterChain.doFilter(request, response);
    }
}
