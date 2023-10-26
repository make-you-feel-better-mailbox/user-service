package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.out.user.ReadUserPort;
import com.onetwo.userservice.application.service.converter.UserUseCaseConverter;
import com.onetwo.userservice.application.service.response.UserDetailResponseDto;
import com.onetwo.userservice.application.service.response.UserIdExistCheckDto;
import com.onetwo.userservice.application.service.service.UserService;
import com.onetwo.userservice.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
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
    private final Instant birth = Instant.now();
    private final String nickname = "newNickname";
    private final String name = "tester";
    private final String email = "onetwo12@onetwo.com";
    private final String phoneNumber = "01098006069";

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
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber);
        User user = User.createNewUserByCommand(registerUserCommand, password);

        boolean userState = false;

        UserDetailResponseDto userDetailResponseDto = new UserDetailResponseDto(userId, birth, nickname, name, email, phoneNumber, userState);

        given(readUserPort.findByUserId(userId)).willReturn(Optional.of(user));
        given(userUseCaseConverter.userToUserDetailResponseDto(any(User.class))).willReturn(userDetailResponseDto);

        //when
        UserDetailResponseDto result = readUserUseCase.getUserDetailInfo(userId);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertSame(userState, result.state());
    }
}