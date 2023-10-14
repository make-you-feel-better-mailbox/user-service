package com.onetwo.userservice.adapter.out.persistence.repository.redis;

import com.onetwo.userservice.adapter.out.persistence.entity.redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RedisRepository extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByAccessToken(String accessToken);
}
