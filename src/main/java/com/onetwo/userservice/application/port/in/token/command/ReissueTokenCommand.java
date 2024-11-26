package com.onetwo.userservice.application.port.in.token.command;

import com.onetwo.userservice.application.port.in.SelfValidating;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public final class ReissueTokenCommand extends SelfValidating<ReissueTokenCommand> {

    @NotEmpty
    private final String accessToken;
    @NotEmpty
    private final String refreshToken;

    public ReissueTokenCommand(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.validateSelf();
    }
}
