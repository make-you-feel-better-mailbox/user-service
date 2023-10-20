package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.WithdrawUserCommand;
import com.onetwo.userservice.application.service.response.UserWithdrawResponseDto;

public interface WithdrawUserUseCase {

    UserWithdrawResponseDto withdrawUser(WithdrawUserCommand withdrawDto, String userId);
}
