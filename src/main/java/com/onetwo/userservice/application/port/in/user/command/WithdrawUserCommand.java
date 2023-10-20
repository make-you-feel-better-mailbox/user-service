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

    public WithdrawUserCommand(String userId, String password) {
        this.userId = userId;
        this.password = password;
        this.validateSelf();
    }
}
