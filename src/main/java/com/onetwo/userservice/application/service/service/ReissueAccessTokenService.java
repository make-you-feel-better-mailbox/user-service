package com.onetwo.userservice.application.service.service;

import com.onetwo.userservice.adapter.out.persistence.entity.token.RefreshTokenEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.user.UserEntity;
import com.onetwo.userservice.application.port.in.token.usecase.ReissueAccessTokenUseCase;
import com.onetwo.userservice.application.port.in.token.command.ReissueTokenCommand;
import com.onetwo.userservice.application.port.out.token.CreateRefreshTokenPort;
import com.onetwo.userservice.application.port.out.token.ReadRefreshTokenPort;
import com.onetwo.userservice.application.port.out.token.UpdateRefreshTokenPort;
import com.onetwo.userservice.application.port.out.user.ReadUserPort;
import com.onetwo.userservice.application.service.response.ReissuedTokenDto;
import com.onetwo.userservice.common.exceptions.NotFoundResourceException;
import com.onetwo.userservice.common.exceptions.TokenValidationException;
import com.onetwo.userservice.common.jwt.JwtCode;
import com.onetwo.userservice.common.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReissueAccessTokenService implements ReissueAccessTokenUseCase {

    private final CreateRefreshTokenPort createRefreshTokenPort;
    private final UpdateRefreshTokenPort updateRefreshTokenPort;
    private final ReadRefreshTokenPort readRefreshTokenPort;
    private final TokenProvider tokenProvider;
    private final ReadUserPort readUserPort;

    @Override
    public void saveRefreshToken(RefreshTokenEntity token) {
        createRefreshTokenPort.saveRefreshToken(token);
    }

    @Override
    @Transactional
    public ReissuedTokenDto reissueAccessTokenByRefreshToken(ReissueTokenCommand reissueTokenCommand) {
        Optional<RefreshTokenEntity> optionalRefreshToken = readRefreshTokenPort.findRefreshTokenByAccessToken(reissueTokenCommand.getAccessToken());

        if (optionalRefreshToken.isEmpty()) throw new TokenValidationException(JwtCode.REFRESH_TOKEN_EXPIRED);

        RefreshTokenEntity refreshToken = optionalRefreshToken.get();

        tokenProvider.refreshTokenValidation(refreshToken.getRefreshToken());

        UserEntity user = readUserPort.findById(refreshToken.getUuid()).orElseThrow(() -> new NotFoundResourceException("User does not Exist"));

        String reissuedAccessToken = tokenProvider.createAccessToken(user.getUserId());

        refreshToken.setReissueAccessToken(reissuedAccessToken);

        updateRefreshTokenPort.updateRefreshToken(refreshToken);

        return new ReissuedTokenDto(reissuedAccessToken);
    }
}
