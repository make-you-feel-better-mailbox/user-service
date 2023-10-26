package com.onetwo.userservice.application.service.converter;

import com.onetwo.userservice.application.service.response.LogoutResponseDto;
import com.onetwo.userservice.application.service.response.ReissuedTokenResponseDto;
import com.onetwo.userservice.application.service.response.TokenResponseDto;
import com.onetwo.userservice.domain.token.RefreshToken;
import org.springframework.stereotype.Component;

@Component
public class TokenUseCaseConverterImpl implements TokenUseCaseConverter {
    @Override
    public TokenResponseDto tokenToTokenResponseDto(RefreshToken token) {
        return new TokenResponseDto(token.getAccessToken(), token.getRefreshToken());
    }

    @Override
    public LogoutResponseDto resultToLogoutResponseDto(boolean refreshTokenNotExist) {
        return new LogoutResponseDto(refreshTokenNotExist);
    }

    @Override
    public ReissuedTokenResponseDto reissuedTokenToReissuedTokenResponseDto(String reissuedAccessToken) {
        return new ReissuedTokenResponseDto(reissuedAccessToken);
    }
}
