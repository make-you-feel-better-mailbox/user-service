package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.WithdrawUserCommand;
import com.onetwo.userservice.application.service.response.UserWithdrawResponseDto;

public interface WithdrawUserUseCase {

    /**
     * Withdraw user use case,
     * Check user exist and request user is same with withdraw user,
     * user can withdraw only him self
     * if withdraw success user state ganna change true
     *
     * @param withdrawUserCommand request userId and requester Id
     * @return Boolean about withdraw success
     */
    UserWithdrawResponseDto withdrawUser(WithdrawUserCommand withdrawUserCommand);
}
