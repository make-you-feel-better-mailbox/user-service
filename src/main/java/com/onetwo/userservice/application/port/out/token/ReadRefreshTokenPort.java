package com.onetwo.userservice.application.port.out.token;

import com.onetwo.userservice.adapter.out.persistence.entity.redis.RefreshToken;

import java.util.Optional;

public interface ReadRefreshTokenPort {

    Optional<RefreshToken> findRefreshTokenById(Long uuid);

    Optional<RefreshToken> findRefreshTokenByAccessToken(String accessToken);
}
