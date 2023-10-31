package com.onetwo.userservice.adapter.in.web.token.mapper;

import com.onetwo.userservice.adapter.in.web.token.request.ReissueTokenRequest;
import com.onetwo.userservice.adapter.in.web.user.response.TokenResponse;
import com.onetwo.userservice.application.port.in.token.command.ReissueTokenCommand;
import com.onetwo.userservice.adapter.in.web.token.response.ReissuedTokenDto;
import com.onetwo.userservice.application.port.in.token.response.ReissuedTokenResponseDto;
import com.onetwo.userservice.application.port.in.user.response.TokenResponseDto;
import org.springframework.stereotype.Component;

@Component
public class TokenDtoMapperImpl implements TokenDtoMapper {

    @Override
    public TokenResponse tokenDtoToTokenResponse(TokenResponseDto tokenResponseDto) {
        return new TokenResponse(tokenResponseDto.accessToken(), tokenResponseDto.refreshToken());
    }

    @Override
    public ReissueTokenCommand reissueTokenRequestToCommand(ReissueTokenRequest reissueTokenRequest) {
        return new ReissueTokenCommand(reissueTokenRequest.accessToken(), reissueTokenRequest.refreshToken());
    }

    @Override
    public ReissuedTokenDto dtoToReissuedTokenDto(ReissuedTokenResponseDto reissuedTokenDto) {
        return new ReissuedTokenDto(reissuedTokenDto.accessToken());
    }
}
