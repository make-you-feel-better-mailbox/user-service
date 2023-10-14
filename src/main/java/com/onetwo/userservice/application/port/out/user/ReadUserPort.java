package com.onetwo.userservice.application.port.out.user;

import com.onetwo.userservice.adapter.out.persistence.entity.user.User;

import java.util.Optional;

public interface ReadUserPort {
    Optional<User> findByUserId(String userId);
}
