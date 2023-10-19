package com.onetwo.userservice.adapter.out.persistence.repository.redis;

import com.onetwo.userservice.adapter.out.persistence.entity.token.RefreshTokenEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByAccessToken(String accessToken);
}
