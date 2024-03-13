package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.response.UserDetailResponseDto;
import com.onetwo.userservice.application.port.in.user.response.UserIdExistCheckDto;
import com.onetwo.userservice.application.port.in.user.response.UserInfoResponseDto;
import com.onetwo.userservice.application.port.out.user.ReadUserPort;
import com.onetwo.userservice.application.service.converter.UserUseCaseConverter;
import com.onetwo.userservice.application.service.service.UserService;
import com.onetwo.userservice.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReadUserUseCaseTest {

    @InjectMocks
    private UserService readUserUseCase;

    @Mock
    private UserUseCaseConverter userUseCaseConverter;

    @Mock
    private ReadUserPort readUserPort;

    private final String userId = "12OneTwo12";
    private final String password = "password";
    private final String nickname = "newNickname";
    private final String email = "onetwo12@onetwo.com";
    private final String phoneNumber = "01098006069";
    private final boolean oauth = false;
    private final String registrationId = null;

    private final RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, nickname, email, phoneNumber, oauth, registrationId);

    @Test
    @DisplayName("[단위][Use Case] 회원 ID 존재여부 확인 - 성공 테스트")
    void userIdExistCheckUseCaseSuccessTest() {
        //given
        UserIdExistCheckDto userIdExistCheckDto = new UserIdExistCheckDto(false);

        given(readUserPort.findByUserId(userId)).willReturn(Optional.empty());
        given(userUseCaseConverter.toUserIdExistCheckDto(anyBoolean())).willReturn(userIdExistCheckDto);

        //when
        UserIdExistCheckDto userIdExistCheckResultDto = readUserUseCase.userIdExistCheck(userId);

        //then
        Assertions.assertFalse(userIdExistCheckResultDto.userIdExist());
    }

    @Test
    @DisplayName("[단위][Use Case] 회원 상세정보 - 성공 테스트 ")
    void getUserDetailInfoUseCaseSuccessTest() {
        //given
        User user = User.createNewUserByCommand(registerUserCommand, password);

        boolean userState = false;

        UserDetailResponseDto userDetailResponseDto = new UserDetailResponseDto(userId, nickname, email, phoneNumber, oauth, registrationId, userState);

        given(readUserPort.findByUserId(userId)).willReturn(Optional.of(user));
        given(userUseCaseConverter.userToUserDetailResponseDto(any(User.class))).willReturn(userDetailResponseDto);

        //when
        UserDetailResponseDto result = readUserUseCase.getUserDetailInfo(userId);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertSame(userState, result.state());
    }

    @Test
    @DisplayName("[단위][Use Case] 회원 정보 조회 - 성공 테스트 ")
    void getUserInfoUseCaseSuccessTest() {
        //given
        User user = User.createNewUserByCommand(registerUserCommand, password);

        UserInfoResponseDto userInfoResponseDto = new UserInfoResponseDto(userId, nickname, email, phoneNumber, oauth, registrationId);

        given(readUserPort.findByUserId(userId)).willReturn(Optional.of(user));
        given(userUseCaseConverter.userToUserInfoResponseDto(any(User.class))).willReturn(userInfoResponseDto);

        //when
        UserInfoResponseDto result = readUserUseCase.getUserInfo(userId);

        //then
        Assertions.assertNotNull(result);
    }
}