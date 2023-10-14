package com.onetwo.userservice.application.port.out.token;

import com.onetwo.userservice.adapter.out.persistence.entity.redis.RefreshToken;

public interface CreateRefreshTokenPort {
    void saveRefreshToken(RefreshToken token);
}
