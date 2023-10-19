package com.onetwo.userservice.application.port.out.token;

import com.onetwo.userservice.adapter.out.persistence.entity.token.RefreshTokenEntity;
import com.onetwo.userservice.domain.token.RefreshToken;

import java.util.Optional;

public interface ReadRefreshTokenPort {

    Optional<RefreshTokenEntity> findRefreshTokenById(Long uuid);

    Optional<RefreshToken> findRefreshTokenByAccessToken(String accessToken);
}
