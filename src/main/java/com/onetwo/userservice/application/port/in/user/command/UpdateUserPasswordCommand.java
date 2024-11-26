package com.onetwo.userservice.application.port.in.user.command;

import com.onetwo.userservice.application.port.in.SelfValidating;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public final class UpdateUserPasswordCommand extends SelfValidating<UpdateUserPasswordCommand> {

    @NotEmpty
    private final String userId;

    @NotEmpty
    private final String currentPassword;

    @NotEmpty
    private final String newPassword;

    @NotEmpty
    private final String newPasswordCheck;

    public UpdateUserPasswordCommand(String userId, String currentPassword, String newPassword, String newPasswordCheck) {
        this.userId = userId;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.newPasswordCheck = newPasswordCheck;
        this.validateSelf();
    }
}
