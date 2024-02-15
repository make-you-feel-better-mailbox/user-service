package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.adapter.out.persistence.entity.user.UserEntity;
import com.onetwo.userservice.application.port.in.user.command.LogoutUserCommand;
import com.onetwo.userservice.application.port.in.user.response.LogoutResponseDto;
import com.onetwo.userservice.application.port.out.token.DeleteRefreshTokenPort;
import com.onetwo.userservice.application.port.out.token.ReadRefreshTokenPort;
import com.onetwo.userservice.application.port.out.user.ReadUserPort;
import com.onetwo.userservice.application.service.converter.TokenUseCaseConverter;
import com.onetwo.userservice.application.service.service.UserService;
import com.onetwo.userservice.common.exceptions.NotFoundResourceException;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LogoutUseCaseTest {

    @InjectMocks
    private UserService logoutUseCase;

    @Mock
    private ReadUserPort readUserPort;

    @Mock
    private ReadRefreshTokenPort readRefreshTokenPort;

    @Mock
    private DeleteRefreshTokenPort deleteRefreshTokenPort;

    @Mock
    private TokenUseCaseConverter tokenUseCaseConverter;

    private final Long uuid = 1L;
    private final String userId = "12OneTwo12";
    private final String password = "password";
    private final String nickname = "newNickname";
    private final String email = "onetwo12@onetwo.com";
    private final String phoneNumber = "01098006069";

    @Test
    @DisplayName("[단위][Use Case] 회원 로그아웃 - 성공 테스트")
    void logoutUserUseCaseSuccessTest() {
        //given
        LogoutUserCommand logoutUserCommand = new LogoutUserCommand(userId);
        UserEntity userEntity = new UserEntity(uuid, userId, password, nickname, email, phoneNumber, false);
        User user = User.entityToDomain(userEntity);

        RefreshToken refreshToken = RefreshToken.createRefreshToken(uuid, "access-token", "refresh-token");
        LogoutResponseDto logoutResponseDto = new LogoutResponseDto(true);

        given(readUserPort.findByUserId(anyString())).willReturn(Optional.of(user));
        given(readRefreshTokenPort.findRefreshTokenById(anyLong())).willReturn(Optional.of(refreshToken));
        given(readRefreshTokenPort.findRefreshTokenById(anyLong())).willReturn(Optional.empty());
        given(tokenUseCaseConverter.resultToLogoutResponseDto(anyBoolean())).willReturn(logoutResponseDto);

        //when
        LogoutResponseDto result = logoutUseCase.logoutUser(logoutUserCommand);

        Assertions.assertTrue(result.isLogoutSuccess());
    }

    @Test
    @DisplayName("[단위][Use Case] 회원 로그아웃 User does not exist - 실패 테스트")
    void logoutUserUseCaseUserDoesNotExistFailTest() {
        //given
        LogoutUserCommand logoutUserCommand = new LogoutUserCommand(userId);

        given(readUserPort.findByUserId(anyString())).willReturn(Optional.empty());

        //when
        Assertions.assertThrows(NotFoundResourceException.class, () -> logoutUseCase.logoutUser(logoutUserCommand));
    }
}