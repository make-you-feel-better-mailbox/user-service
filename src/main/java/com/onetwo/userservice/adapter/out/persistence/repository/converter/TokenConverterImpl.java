package com.onetwo.userservice.adapter.out.persistence.repository.converter;

import com.onetwo.userservice.adapter.out.persistence.entity.token.RefreshTokenEntity;
import com.onetwo.userservice.domain.token.RefreshToken;
import org.springframework.stereotype.Component;

@Component
public class TokenConverterImpl implements TokenConverter {
    @Override
    public RefreshToken entityToDomain(RefreshTokenEntity refreshTokenEntity) {
        return RefreshToken.createRefreshToken(
                refreshTokenEntity.getUuid(),
                refreshTokenEntity.getAccessToken(),
                refreshTokenEntity.getRefreshToken()
        );
    }

    @Override
    public RefreshTokenEntity domainToEntity(RefreshToken token) {
        return new RefreshTokenEntity(
                token.getUuid(),
                token.getAccessToken(),
                token.getRefreshToken()
        );
    }
}
