package com.onetwo.userservice.service.service;

import com.onetwo.userservice.entity.redis.RefreshToken;
import com.onetwo.userservice.repository.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {

    private final RedisRepository redisRepository;

    @Override
    public void saveRefreshToken(RefreshToken token) {
        redisRepository.save(token);
    }
}
