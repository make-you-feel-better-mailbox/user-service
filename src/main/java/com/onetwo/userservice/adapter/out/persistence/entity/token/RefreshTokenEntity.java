package com.onetwo.userservice.adapter.out.persistence.entity.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "refresh", timeToLive = 604800)
@AllArgsConstructor
public class RefreshTokenEntity {

    @Id
    private Long uuid;
    @Indexed
    private String accessToken;
    private String refreshToken;
}


