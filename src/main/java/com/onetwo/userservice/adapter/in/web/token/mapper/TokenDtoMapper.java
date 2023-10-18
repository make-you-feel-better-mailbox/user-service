package com.onetwo.userservice.adapter.in.web.token.mapper;

import com.onetwo.userservice.adapter.in.web.token.request.ReissueTokenRequest;
import com.onetwo.userservice.application.port.in.token.command.ReissueTokenCommand;

public class TokenDtoMapper {

    private static TokenDtoMapper tokenDtoMapper;

    public static TokenDtoMapper of() {
        if (tokenDtoMapper == null)
            tokenDtoMapper = new TokenDtoMapper();

        return tokenDtoMapper;
    }

    public ReissueTokenCommand reissueTokenRequestToCommand(ReissueTokenRequest reissueTokenRequest) {
        return new ReissueTokenCommand(reissueTokenRequest.accessToken(), reissueTokenRequest.refreshToken());
    }
}
