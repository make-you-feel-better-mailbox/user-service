package com.onetwo.userservice.application.port.out.user;

import com.onetwo.userservice.adapter.out.persistence.entity.user.UserEntity;

import java.util.Optional;

public interface ReadUserPort {
    Optional<UserEntity> findByUserId(String userId);

    Optional<UserEntity> findById(Long uuid);
}
