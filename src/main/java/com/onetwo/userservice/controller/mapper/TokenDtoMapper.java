package com.onetwo.userservice.controller.mapper;

import com.onetwo.userservice.controller.request.ReissueTokenRequest;
import com.onetwo.userservice.service.requset.ReissueTokenDto;

public class TokenDtoMapper {

    private static TokenDtoMapper tokenDtoMapper;

    public static TokenDtoMapper of() {
        if (tokenDtoMapper == null)
            tokenDtoMapper = new TokenDtoMapper();

        return tokenDtoMapper;
    }

    public ReissueTokenDto reissueTokenRequestToDto(ReissueTokenRequest reissueTokenRequest) {
        return new ReissueTokenDto(reissueTokenRequest.accessToken(), reissueTokenRequest.refreshToken());
    }
}
