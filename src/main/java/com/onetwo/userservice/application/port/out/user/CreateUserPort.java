package com.onetwo.userservice.application.port.out.user;

import com.onetwo.userservice.domain.user.User;

public interface CreateUserPort {
    User registerNewUser(User requestRegisterUser);
}
