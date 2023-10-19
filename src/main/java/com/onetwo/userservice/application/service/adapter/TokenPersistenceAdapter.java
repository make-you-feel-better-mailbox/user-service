package com.onetwo.userservice.application.service.adapter;

import com.onetwo.userservice.adapter.out.persistence.entity.token.RefreshTokenEntity;
import com.onetwo.userservice.adapter.out.persistence.repository.converter.TokenConverter;
import com.onetwo.userservice.adapter.out.persistence.repository.redis.TokenRepository;
import com.onetwo.userservice.application.port.out.token.CreateRefreshTokenPort;
import com.onetwo.userservice.application.port.out.token.ReadRefreshTokenPort;
import com.onetwo.userservice.application.port.out.token.UpdateRefreshTokenPort;
import com.onetwo.userservice.domain.token.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenPersistenceAdapter implements CreateRefreshTokenPort, ReadRefreshTokenPort, UpdateRefreshTokenPort {

    private final TokenRepository tokenRepository;
    private final TokenConverter tokenConverter;

    @Override
    public void saveRefreshToken(RefreshToken token) {
        RefreshTokenEntity refreshTokenEntity = tokenConverter.domainToEntity(token);

        tokenRepository.save(refreshTokenEntity);
    }

    @Override
    public Optional<RefreshTokenEntity> findRefreshTokenById(Long uuid) {
        return tokenRepository.findById(uuid);
    }

    @Override
    public Optional<RefreshToken> findRefreshTokenByAccessToken(String accessToken) {
        Optional<RefreshTokenEntity> optionalRefreshToken = tokenRepository.findByAccessToken(accessToken);

        if (optionalRefreshToken.isPresent()) {
            RefreshToken refreshToken = tokenConverter.entityToDomain(optionalRefreshToken.get());
            return Optional.of(refreshToken);
        }

        return Optional.empty();
    }

    @Override
    public void updateRefreshToken(RefreshToken token) {
        RefreshTokenEntity refreshTokenEntity = tokenConverter.domainToEntity(token);

        tokenRepository.save(refreshTokenEntity);
    }
}
