package com.onetwo.userservice.application.port.in;

import com.onetwo.userservice.adapter.out.persistence.entity.redis.RefreshToken;
import com.onetwo.userservice.application.port.in.command.ReissueTokenCommand;
import com.onetwo.userservice.application.service.response.ReissuedTokenDto;

public interface ReissueAccessTokenUseCase {
    void saveRefreshToken(RefreshToken token);

    ReissuedTokenDto reissueAccessTokenByRefreshToken(ReissueTokenCommand reissueTokenCommand);
}
