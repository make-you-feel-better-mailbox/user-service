package com.onetwo.userservice.application.port.in.user.command;

import com.onetwo.userservice.application.port.in.SelfValidating;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public final class LoginUserCommand extends SelfValidating<LoginUserCommand> {

    @NotEmpty
    private final String id;
    @NotEmpty
    private final String pw;

    public LoginUserCommand(String id, String pw) {
        this.id = id;
        this.pw = pw;
        this.validateSelf();
    }
}
