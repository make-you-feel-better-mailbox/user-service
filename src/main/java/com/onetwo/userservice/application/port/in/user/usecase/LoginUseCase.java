package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.adapter.in.web.user.response.TokenResponse;
import com.onetwo.userservice.application.port.in.user.command.LoginUserCommand;

public interface LoginUseCase {

    TokenResponse loginUser(LoginUserCommand loginUserCommand);
}
