package com.onetwo.userservice.entity.redis;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refresh", timeToLive = 604800)
public class RefreshToken {

    @Id
    private String userId;
    private String ip;
    private String refreshToken;

    private RefreshToken(String userId, String ip, String refreshToken) {
        this.userId = userId;
        this.ip = ip;
        this.refreshToken = refreshToken;
    }

    public static RefreshToken createRefreshToken(String userId, String ip, String refreshToken) {
        return new RefreshToken(userId, ip, refreshToken);
    }

    public void reissue(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
