package com.onetwo.userservice.application.port.in.token.usecase;

import com.onetwo.userservice.adapter.out.persistence.entity.user.UserEntity;
import com.onetwo.userservice.application.port.in.token.command.ReissueTokenCommand;
import com.onetwo.userservice.application.port.in.token.response.ReissuedTokenResponseDto;
import com.onetwo.userservice.application.port.out.token.ReadRefreshTokenPort;
import com.onetwo.userservice.application.port.out.token.UpdateRefreshTokenPort;
import com.onetwo.userservice.application.port.out.user.ReadUserPort;
import com.onetwo.userservice.application.service.converter.TokenUseCaseConverter;
import com.onetwo.userservice.application.service.service.ReissueAccessTokenService;
import com.onetwo.userservice.common.exceptions.NotFoundResourceException;
import com.onetwo.userservice.common.exceptions.TokenValidationException;
import com.onetwo.userservice.common.jwt.TokenProvider;
import com.onetwo.userservice.domain.token.RefreshToken;
import com.onetwo.userservice.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReissueAccessTokenUseCaseTest {

    @InjectMocks
    private ReissueAccessTokenService reissueAccessTokenUseCase;

    @Mock
    private UpdateRefreshTokenPort updateRefreshTokenPort;

    @Mock
    private ReadRefreshTokenPort readRefreshTokenPort;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private ReadUserPort readUserPort;

    @Mock
    private TokenUseCaseConverter tokenUseCaseConverter;

    private final Long uuid = 1L;
    private final String userId = "12OneTwo12";
    private final String password = "password";
    private final String nickname = "newNickname";
    private final String email = "onetwo12@onetwo.com";
    private final String phoneNumber = "01098006069";
    private final String accessToken = "access-token";
    private final String refreshToken = "refresh-token";
    private final boolean oauth = false;
    private final String registrationId = null;

    @Test
    @DisplayName("[단위][Use Case] Access Token 재발급 - 성공 테스트")
    void reissueAccessTokenByRefreshTokenUseCaseSuccessTest() {
        //given
        ReissueTokenCommand reissueTokenCommand = new ReissueTokenCommand(accessToken, refreshToken);
        RefreshToken refreshTokenDomain = RefreshToken.createRefreshToken(uuid, accessToken, refreshToken);
        UserEntity userEntity = new UserEntity(uuid, userId, password, nickname, email, phoneNumber, oauth, registrationId, false);
        User user = User.entityToDomain(userEntity);
        ReissuedTokenResponseDto reissuedTokenResponseDto = new ReissuedTokenResponseDto(accessToken);

        given(readRefreshTokenPort.findRefreshTokenByAccessToken(anyString())).willReturn(Optional.of(refreshTokenDomain));
        given(readUserPort.findById(anyLong())).willReturn(Optional.of(user));
        given(tokenProvider.createAccessToken(anyString())).willReturn(refreshToken);
        given(tokenUseCaseConverter.reissuedTokenToReissuedTokenResponseDto(anyString())).willReturn(reissuedTokenResponseDto);

        //when
        ReissuedTokenResponseDto result = reissueAccessTokenUseCase.reissueAccessTokenByRefreshToken(reissueTokenCommand);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.accessToken());
    }

    @Test
    @DisplayName("[단위][Use Case] Access Token 재발급 Refresh Token does not exist - 실패 테스트")
    void reissueAccessTokenByRefreshTokenUseCaseRefreshTokenDoesNotExistTest() {
        //given
        ReissueTokenCommand reissueTokenCommand = new ReissueTokenCommand(accessToken, refreshToken);

        given(readRefreshTokenPort.findRefreshTokenByAccessToken(anyString())).willReturn(Optional.empty());

        //when then
        Assertions.assertThrows(TokenValidationException.class, () -> reissueAccessTokenUseCase.reissueAccessTokenByRefreshToken(reissueTokenCommand));
    }

    @Test
    @DisplayName("[단위][Use Case] Access Token 재발급 User does not exist - 실패 테스트")
    void reissueAccessTokenByRefreshTokenUseCaseUserDoesNotExistTest() {
        //given
        ReissueTokenCommand reissueTokenCommand = new ReissueTokenCommand(accessToken, refreshToken);
        RefreshToken refreshTokenDomain = RefreshToken.createRefreshToken(uuid, accessToken, refreshToken);

        given(readRefreshTokenPort.findRefreshTokenByAccessToken(anyString())).willReturn(Optional.of(refreshTokenDomain));
        given(readUserPort.findById(anyLong())).willReturn(Optional.empty());

        //when then
        Assertions.assertThrows(NotFoundResourceException.class, () -> reissueAccessTokenUseCase.reissueAccessTokenByRefreshToken(reissueTokenCommand));
    }
}