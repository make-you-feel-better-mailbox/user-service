package com.onetwo.userservice.adapter.out.persistence.repository.converter;

import com.onetwo.userservice.adapter.out.persistence.entity.token.RefreshTokenEntity;
import com.onetwo.userservice.domain.token.RefreshToken;

public interface TokenConverter {
    RefreshToken entityToDomain(RefreshTokenEntity refreshTokenEntity);

    RefreshTokenEntity domainToEntity(RefreshToken token);
}
