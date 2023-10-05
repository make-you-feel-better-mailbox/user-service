package com.onetwo.userservice.service.service;

import com.onetwo.userservice.entity.redis.RefreshToken;

import java.util.Optional;

public interface CacheService {
    void saveRefreshToken(RefreshToken token);

    Optional<RefreshToken> findById(String id);
}
