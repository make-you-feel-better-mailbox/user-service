package com.onetwo.userservice.application.port.out.user;

import com.onetwo.userservice.domain.user.User;

public interface RegisterUserPort {
    User registerNewUser(User requestRegisterUser);
}
