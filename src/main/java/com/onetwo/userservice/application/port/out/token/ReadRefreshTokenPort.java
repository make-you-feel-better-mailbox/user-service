package com.onetwo.userservice.application.port.out.token;

import com.onetwo.userservice.adapter.out.persistence.entity.token.RefreshTokenEntity;

import java.util.Optional;

public interface ReadRefreshTokenPort {

    Optional<RefreshTokenEntity> findRefreshTokenById(Long uuid);

    Optional<RefreshTokenEntity> findRefreshTokenByAccessToken(String accessToken);
}
