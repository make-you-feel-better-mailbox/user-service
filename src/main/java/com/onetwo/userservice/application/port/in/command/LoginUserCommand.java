package com.onetwo.userservice.application.port.in.command;

import lombok.Getter;

@Getter
public final class LoginUserCommand extends SelfValidating<LoginUserCommand> {

    private final String id;
    private final String pw;

    public LoginUserCommand(String id, String pw) {
        this.id = id;
        this.pw = pw;
        this.validateSelf();
    }
}
