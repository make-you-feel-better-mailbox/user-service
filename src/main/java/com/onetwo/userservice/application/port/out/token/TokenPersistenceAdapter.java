package com.onetwo.userservice.application.port.out.token;

import com.onetwo.userservice.adapter.out.persistence.entity.redis.RefreshToken;
import com.onetwo.userservice.adapter.out.persistence.repository.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenPersistenceAdapter implements CreateRefreshTokenPort, ReadRefreshTokenPort {

    private final RedisRepository redisRepository;

    @Override
    public void saveRefreshToken(RefreshToken token) {
        redisRepository.save(token);
    }

    @Override
    public Optional<RefreshToken> findRefreshTokenById(Long uuid) {
        return redisRepository.findById(uuid);
    }

    @Override
    public Optional<RefreshToken> findRefreshTokenByAccessToken(String accessToken) {
        return redisRepository.findByAccessToken(accessToken);
    }
}
