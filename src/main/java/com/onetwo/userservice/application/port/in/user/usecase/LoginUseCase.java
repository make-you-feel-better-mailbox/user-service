package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.LoginUserCommand;
import com.onetwo.userservice.application.service.response.TokenResponseDto;

public interface LoginUseCase {

    TokenResponseDto loginUser(LoginUserCommand loginUserCommand);
}
