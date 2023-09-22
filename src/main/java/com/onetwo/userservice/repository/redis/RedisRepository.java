package com.onetwo.userservice.repository.redis;

import com.onetwo.userservice.entity.redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<RefreshToken, String> {
}
