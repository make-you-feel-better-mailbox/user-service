package com.onetwo.userservice.application.port.out.token;

import com.onetwo.userservice.domain.token.RefreshToken;

import java.util.Optional;

public interface ReadRefreshTokenPort {

    Optional<RefreshToken> findRefreshTokenById(Long uuid);

    Optional<RefreshToken> findRefreshTokenByAccessToken(String accessToken);
}
