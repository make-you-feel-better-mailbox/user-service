package com.onetwo.userservice.application.service.service;

import com.onetwo.userservice.application.port.in.token.command.ReissueTokenCommand;
import com.onetwo.userservice.application.port.in.token.usecase.ReissueAccessTokenUseCase;
import com.onetwo.userservice.application.port.out.token.ReadRefreshTokenPort;
import com.onetwo.userservice.application.port.out.token.UpdateRefreshTokenPort;
import com.onetwo.userservice.application.port.out.user.ReadUserPort;
import com.onetwo.userservice.application.service.converter.TokenUseCaseConverter;
import com.onetwo.userservice.application.service.response.ReissuedTokenResponseDto;
import com.onetwo.userservice.common.exceptions.NotFoundResourceException;
import com.onetwo.userservice.common.exceptions.TokenValidationException;
import com.onetwo.userservice.common.jwt.JwtCode;
import com.onetwo.userservice.common.jwt.TokenProvider;
import com.onetwo.userservice.domain.token.RefreshToken;
import com.onetwo.userservice.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReissueAccessTokenService implements ReissueAccessTokenUseCase {

    private final UpdateRefreshTokenPort updateRefreshTokenPort;
    private final ReadRefreshTokenPort readRefreshTokenPort;
    private final TokenProvider tokenProvider;
    private final ReadUserPort readUserPort;
    private final TokenUseCaseConverter tokenUseCaseConverter;

    /**
     * Reissue Access token use case,
     * Find Refresh token in cache and check validation Refresh token,
     * And reissue Access token by Refresh token and update Access token in Refresh token domain
     *
     * @param reissueTokenCommand
     * @return Reissued Access token
     */
    @Override
    @Transactional
    public ReissuedTokenResponseDto reissueAccessTokenByRefreshToken(ReissueTokenCommand reissueTokenCommand) {
        Optional<RefreshToken> optionalRefreshToken = readRefreshTokenPort.findRefreshTokenByAccessToken(reissueTokenCommand.getAccessToken());

        if (optionalRefreshToken.isEmpty()) throw new TokenValidationException(JwtCode.REFRESH_TOKEN_EXPIRED);

        RefreshToken refreshToken = optionalRefreshToken.get();

        tokenProvider.refreshTokenValidation(refreshToken.getRefreshToken());

        User user = readUserPort.findById(refreshToken.getUuid()).orElseThrow(() -> new NotFoundResourceException("User does not Exist"));

        String reissuedAccessToken = tokenProvider.createAccessToken(user.getUserId());

        refreshToken.setReissueAccessToken(reissuedAccessToken);

        updateRefreshTokenPort.updateRefreshToken(refreshToken);

        return tokenUseCaseConverter.reissuedTokenToReissuedTokenResponseDto(reissuedAccessToken);
    }
}
