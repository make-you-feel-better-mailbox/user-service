package com.onetwo.userservice.application.port.in.user.command;

import com.onetwo.userservice.application.port.in.SelfValidating;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public final class WithdrawUserCommand extends SelfValidating<WithdrawUserCommand> {

    @NotEmpty
    private final String userId;
    @NotEmpty
    private final String password;
    @NotEmpty
    private final String requestUserId;

    public WithdrawUserCommand(String userId, String password, String requestUserId) {
        this.userId = userId;
        this.password = password;
        this.requestUserId = requestUserId;
        this.validateSelf();
    }
}
