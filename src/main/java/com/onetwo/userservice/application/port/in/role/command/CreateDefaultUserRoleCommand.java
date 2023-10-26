package com.onetwo.userservice.application.port.in.role.command;

import com.onetwo.userservice.application.port.in.SelfValidating;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CreateDefaultUserRoleCommand extends SelfValidating<CreateDefaultUserRoleCommand> {

    @NotEmpty
    private final String userId;

    public CreateDefaultUserRoleCommand(String userId) {
        this.userId = userId;
        this.validateSelf();
    }
}
