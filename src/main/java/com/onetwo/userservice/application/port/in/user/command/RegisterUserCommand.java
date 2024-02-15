package com.onetwo.userservice.application.port.in.user.command;

import com.onetwo.userservice.application.port.in.SelfValidating;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public final class RegisterUserCommand extends SelfValidating<RegisterUserCommand> {

    @NotEmpty
    private final String userId;

    @NotEmpty
    private final String password;

    @NotEmpty
    private final String nickname;

    @NotEmpty
    private final String email;

    private final String phoneNumber;

    public RegisterUserCommand(String userId, String password, String nickname, String email, String phoneNumber) {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.validateSelf();
    }
}
