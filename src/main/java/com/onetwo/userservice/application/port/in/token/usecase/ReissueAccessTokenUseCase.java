package com.onetwo.userservice.application.port.in.token.usecase;

import com.onetwo.userservice.application.port.in.token.command.ReissueTokenCommand;
import com.onetwo.userservice.application.service.response.ReissuedTokenResponseDto;

public interface ReissueAccessTokenUseCase {
    ReissuedTokenResponseDto reissueAccessTokenByRefreshToken(ReissueTokenCommand reissueTokenCommand);
}
