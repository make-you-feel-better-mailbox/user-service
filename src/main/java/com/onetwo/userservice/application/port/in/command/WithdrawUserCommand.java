package com.onetwo.userservice.application.port.in.command;

import lombok.Getter;

@Getter
public final class WithdrawUserCommand extends SelfValidating<WithdrawUserCommand> {

    private final String userId;
    private final String password;

    public WithdrawUserCommand(String userId, String password) {
        this.userId = userId;
        this.password = password;
        this.validateSelf();
    }
}
