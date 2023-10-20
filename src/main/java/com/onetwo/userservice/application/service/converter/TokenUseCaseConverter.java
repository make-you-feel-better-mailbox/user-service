package com.onetwo.userservice.application.service.converter;

import com.onetwo.userservice.application.service.response.LogoutResponseDto;
import com.onetwo.userservice.application.service.response.TokenResponseDto;
import com.onetwo.userservice.domain.token.RefreshToken;

public interface TokenUseCaseConverter {
    TokenResponseDto tokenToTokenResponseDto(RefreshToken token);

    LogoutResponseDto resultToLogoutResponseDto(boolean refreshTokenNotExist);
}
