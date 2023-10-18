package com.onetwo.userservice.application.port.in.token.usecase;

import com.onetwo.userservice.adapter.out.persistence.entity.token.RefreshTokenEntity;
import com.onetwo.userservice.application.port.in.token.command.ReissueTokenCommand;
import com.onetwo.userservice.application.service.response.ReissuedTokenDto;

public interface ReissueAccessTokenUseCase {
    void saveRefreshToken(RefreshTokenEntity token);

    ReissuedTokenDto reissueAccessTokenByRefreshToken(ReissueTokenCommand reissueTokenCommand);
}
