package com.onetwo.userservice.application.port.in.user.command;

import com.onetwo.userservice.application.port.in.SelfValidating;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.Instant;

@Getter
public final class UpdateUserCommand extends SelfValidating<UpdateUserCommand> {

    @NotNull
    private final Instant birth;

    @NotEmpty
    private final String nickname;

    @NotEmpty
    private final String name;

    @NotEmpty
    private final String email;

    private final String phoneNumber;

    public UpdateUserCommand(Instant birth, String nickname, String name, String email, String phoneNumber) {
        this.birth = birth;
        this.nickname = nickname;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.validateSelf();
    }
}
