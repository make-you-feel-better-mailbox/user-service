package com.onetwo.userservice.application.service.adapter;

import com.onetwo.userservice.adapter.out.persistence.entity.token.RefreshTokenEntity;
import com.onetwo.userservice.adapter.out.persistence.repository.redis.RedisRepository;
import com.onetwo.userservice.application.port.out.token.CreateRefreshTokenPort;
import com.onetwo.userservice.application.port.out.token.ReadRefreshTokenPort;
import com.onetwo.userservice.application.port.out.token.UpdateRefreshTokenPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenPersistenceAdapter implements CreateRefreshTokenPort, ReadRefreshTokenPort, UpdateRefreshTokenPort {

    private final RedisRepository redisRepository;

    @Override
    public void saveRefreshToken(RefreshTokenEntity token) {
        redisRepository.save(token);
    }

    @Override
    public Optional<RefreshTokenEntity> findRefreshTokenById(Long uuid) {
        return redisRepository.findById(uuid);
    }

    @Override
    public Optional<RefreshTokenEntity> findRefreshTokenByAccessToken(String accessToken) {
        return redisRepository.findByAccessToken(accessToken);
    }

    @Override
    public void updateRefreshToken(RefreshTokenEntity token) {
        redisRepository.save(token);
    }
}
