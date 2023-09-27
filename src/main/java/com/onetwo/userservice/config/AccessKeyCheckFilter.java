package com.onetwo.userservice.config;

import com.onetwo.userservice.common.GlobalStatus;
import com.onetwo.userservice.common.exceptions.BadRequestException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;

@Slf4j
@Component
public class AccessKeyCheckFilter extends OncePerRequestFilter {

    @Value("${access-id}")
    private String accessId;

    @Value("${access-key}")
    private String accessKey;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest requestToCache = new ContentCachingRequestWrapper(request);

        String requestAccessId = requestToCache.getHeader(GlobalStatus.ACCESS_ID);
        String requestAccessKey = requestToCache.getHeader(GlobalStatus.ACCESS_KEY);

        if (!accessId.equals(requestAccessId) || !accessKey.equals(requestAccessKey))
            throw new BadRequestException("access-id or access-key does not matches");

        filterChain.doFilter(request, response);
    }
}
