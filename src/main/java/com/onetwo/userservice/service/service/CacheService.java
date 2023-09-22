package com.onetwo.userservice.service.service;

import com.onetwo.userservice.entity.redis.RefreshToken;

public interface CacheService {
    void saveRefreshToken(RefreshToken token);
}
