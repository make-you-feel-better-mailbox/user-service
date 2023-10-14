package com.onetwo.userservice.application.port.out.user;

import com.onetwo.userservice.adapter.out.persistence.entity.user.User;

public interface CreateUserPort {
    User createNewUser(User newUser);
}
