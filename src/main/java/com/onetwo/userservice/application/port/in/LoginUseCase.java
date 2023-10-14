package com.onetwo.userservice.application.port.in;

import com.onetwo.userservice.adapter.in.web.user.response.TokenResponse;
import com.onetwo.userservice.application.port.in.command.LoginUserCommand;

public interface LoginUseCase {

    TokenResponse loginUser(LoginUserCommand loginUserCommand);
}
