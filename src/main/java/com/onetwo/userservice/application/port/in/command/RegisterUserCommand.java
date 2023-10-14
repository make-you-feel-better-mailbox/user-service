package com.onetwo.userservice.application.port.in.command;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.Instant;

@Getter
public final class RegisterUserCommand extends SelfValidating<RegisterUserCommand> {

    @NotEmpty
    private final String userId;

    @NotEmpty
    private final String password;

    @NotNull
    private final Instant birth;

    @NotEmpty
    private final String nickname;

    @NotEmpty
    private final String name;

    @NotEmpty
    private final String email;

    private final String phoneNumber;

    public RegisterUserCommand(String userId, String password, Instant birth, String nickname, String name, String email, String phoneNumber) {
        this.userId = userId;
        this.password = password;
        this.birth = birth;
        this.nickname = nickname;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.validateSelf();
    }
}
