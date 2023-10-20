package com.onetwo.userservice.application.port.in.user.command;

import com.onetwo.userservice.application.port.in.SelfValidating;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class LogoutUserCommand extends SelfValidating<LogoutUserCommand> {

    @NotEmpty
    private final String userId;

    public LogoutUserCommand(String userId) {
        this.userId = userId;
        this.validateSelf();
    }
}
