package com.onetwo.userservice.service.service;

import com.onetwo.userservice.entity.redis.RefreshToken;
import com.onetwo.userservice.service.requset.ReissueTokenDto;
import com.onetwo.userservice.service.response.ReissuedTokenDto;

public interface UserTokenService {
    void saveRefreshToken(RefreshToken token);

    ReissuedTokenDto reissueAccessTokenByRefreshToken(ReissueTokenDto reissueTokenDto);
}
