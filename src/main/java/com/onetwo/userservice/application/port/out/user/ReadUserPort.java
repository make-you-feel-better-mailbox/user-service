package com.onetwo.userservice.application.port.out.user;

import com.onetwo.userservice.domain.user.User;

import java.util.Optional;

public interface ReadUserPort {
    Optional<User> findByUserId(String userId);

    Optional<User> findById(Long uuid);
}
