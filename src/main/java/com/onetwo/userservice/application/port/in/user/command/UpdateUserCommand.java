package com.onetwo.userservice.application.port.in.user.command;

import com.onetwo.userservice.application.port.in.SelfValidating;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public final class UpdateUserCommand extends SelfValidating<UpdateUserCommand> {

    @NotEmpty
    private final String userId;

    @NotEmpty
    private final String nickname;

    @NotEmpty
    private final String email;

    private final String phoneNumber;

    private final String profileImageEndPoint;

    public UpdateUserCommand(String userId, String nickname, String email, String phoneNumber, String profileImageEndPoint) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profileImageEndPoint = profileImageEndPoint;
        this.validateSelf();
    }
}
