package com.onetwo.userservice.application.port.in.token.usecase;

import com.onetwo.userservice.application.port.in.token.command.ReissueTokenCommand;
import com.onetwo.userservice.application.service.response.ReissuedTokenResponseDto;

public interface ReissueAccessTokenUseCase {

    /**
     * Reissue Access token use case,
     * Reissue Access token by Refresh token
     *
     * @param reissueTokenCommand
     * @return Reissued Access token
     */
    ReissuedTokenResponseDto reissueAccessTokenByRefreshToken(ReissueTokenCommand reissueTokenCommand);
}
