package com.onetwo.userservice.common.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

public interface TokenProvider {

    String createAccessToken(String userId);

    String createRefreshToken(Long uuid);

    Authentication getAuthentication(String token);

    Claims getClaimsByToken(String token);

    boolean validateToken(String token);

    boolean refreshTokenValidation(String token);
}
