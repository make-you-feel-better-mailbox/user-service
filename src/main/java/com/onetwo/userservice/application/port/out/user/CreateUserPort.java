package com.onetwo.userservice.application.port.out.user;

import com.onetwo.userservice.adapter.out.persistence.entity.user.UserEntity;

public interface CreateUserPort {
    UserEntity createNewUser(UserEntity newUser);
}
