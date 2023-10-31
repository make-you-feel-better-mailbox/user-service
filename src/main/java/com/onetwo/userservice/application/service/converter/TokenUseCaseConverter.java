package com.onetwo.userservice.application.service.converter;

import com.onetwo.userservice.application.port.in.user.response.LogoutResponseDto;
import com.onetwo.userservice.application.port.in.token.response.ReissuedTokenResponseDto;
import com.onetwo.userservice.application.port.in.user.response.TokenResponseDto;
import com.onetwo.userservice.domain.token.RefreshToken;

public interface TokenUseCaseConverter {
    TokenResponseDto tokenToTokenResponseDto(RefreshToken token);

    LogoutResponseDto resultToLogoutResponseDto(boolean refreshTokenNotExist);

    ReissuedTokenResponseDto reissuedTokenToReissuedTokenResponseDto(String reissuedAccessToken);
}
