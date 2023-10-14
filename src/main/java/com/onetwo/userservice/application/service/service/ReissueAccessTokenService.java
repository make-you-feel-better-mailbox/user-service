package com.onetwo.userservice.application.service.service;

import com.onetwo.userservice.adapter.out.persistence.entity.redis.RefreshToken;
import com.onetwo.userservice.adapter.out.persistence.entity.user.User;
import com.onetwo.userservice.adapter.out.persistence.repository.user.UserRepository;
import com.onetwo.userservice.application.port.in.ReissueAccessTokenUseCase;
import com.onetwo.userservice.application.port.in.command.ReissueTokenCommand;
import com.onetwo.userservice.application.port.out.token.CreateRefreshTokenPort;
import com.onetwo.userservice.application.port.out.token.ReadRefreshTokenPort;
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
    private final ReadRefreshTokenPort readRefreshTokenPort;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Override
    public void saveRefreshToken(RefreshToken token) {
        createRefreshTokenPort.saveRefreshToken(token);
    }

    @Override
    @Transactional
    public ReissuedTokenDto reissueAccessTokenByRefreshToken(ReissueTokenCommand reissueTokenCommand) {
        Optional<RefreshToken> optionalRefreshToken = readRefreshTokenPort.findRefreshTokenByAccessToken(reissueTokenCommand.getAccessToken());

        if (optionalRefreshToken.isEmpty()) throw new TokenValidationException(JwtCode.REFRESH_TOKEN_EXPIRED);

        RefreshToken refreshToken = optionalRefreshToken.get();

        tokenProvider.refreshTokenValidation(refreshToken.getRefreshToken());

        User user = userRepository.findById(refreshToken.getUuid()).orElseThrow(() -> new NotFoundResourceException("User does not Exist"));

        String reissuedAccessToken = tokenProvider.createAccessToken(user.getUserId());

        refreshToken.setReissueAccessToken(reissuedAccessToken);

        return new ReissuedTokenDto(reissuedAccessToken);
    }
}
