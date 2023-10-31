package com.onetwo.userservice.adapter.in.web.token.mapper;

import com.onetwo.userservice.adapter.in.web.token.request.ReissueTokenRequest;
import com.onetwo.userservice.adapter.in.web.user.response.TokenResponse;
import com.onetwo.userservice.application.port.in.token.command.ReissueTokenCommand;
import com.onetwo.userservice.adapter.in.web.token.response.ReissuedTokenDto;
import com.onetwo.userservice.application.port.in.token.response.ReissuedTokenResponseDto;
import com.onetwo.userservice.application.port.in.user.response.TokenResponseDto;

public interface TokenDtoMapper {


    TokenResponse tokenDtoToTokenResponse(TokenResponseDto tokenResponseDto);

    ReissueTokenCommand reissueTokenRequestToCommand(ReissueTokenRequest reissueTokenRequest);

    ReissuedTokenDto dtoToReissuedTokenDto(ReissuedTokenResponseDto reissuedTokenDto);
}
