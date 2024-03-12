package com.onetwo.userservice.application.port.in.user.command;

import com.onetwo.userservice.application.port.in.SelfValidating;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public final class OAuthLoginCommand extends SelfValidating<OAuthLoginCommand> {

    @NotEmpty
    private final String code;

    @NotEmpty
    private final String registrationId;

    public OAuthLoginCommand(String code, String registrationId) {
        this.code = code;
        this.registrationId = registrationId;
        this.validateSelf();
    }
}
