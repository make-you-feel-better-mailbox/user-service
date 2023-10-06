package com.onetwo.userservice.service.service;

import com.onetwo.userservice.entity.redis.RefreshToken;
import com.onetwo.userservice.repository.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {

    private final RedisRepository redisRepository;

    @Override
    public void saveRefreshToken(RefreshToken token) {
        redisRepository.save(token);
    }

    @Override
    public Optional<RefreshToken> findRefreshTokenById(String id) {
        return redisRepository.findById(id);
    }

    @Override
    public Optional<RefreshToken> findRefreshTokenByAccessToken(String accessToken) {
        return redisRepository.findByAccessToken(accessToken);
    }
}
