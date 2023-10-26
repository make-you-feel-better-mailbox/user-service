package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.command.UpdateUserCommand;
import com.onetwo.userservice.application.port.out.user.ReadUserPort;
import com.onetwo.userservice.application.port.out.user.UpdateUserPort;
import com.onetwo.userservice.application.service.converter.UserUseCaseConverter;
import com.onetwo.userservice.application.service.response.UserUpdateResponseDto;
import com.onetwo.userservice.application.service.service.UserService;
import com.onetwo.userservice.common.exceptions.BadRequestException;
import com.onetwo.userservice.common.exceptions.NotFoundResourceException;
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
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseTest {

    @InjectMocks
    private UserService updateUserUseCase;

    @Mock
    private ReadUserPort readUserPort;

    @Mock
    private UpdateUserPort updateUserPort;

    @Mock
    private UserUseCaseConverter userUseCaseConverter;

    private final String userId = "12OneTwo12";
    private final String password = "password";
    private final Instant birth = Instant.now();
    private final String nickname = "newNickname";
    private final String name = "tester";
    private final String email = "onetwo12@onetwo.com";
    private final String phoneNumber = "01098006069";

    @Test
    @DisplayName("[단위][Use Case] 회원 수정 - 성공 테스트")
    void updateUserUseCaseSuccessTest() {
        //given
        UpdateUserCommand updateUserCommand = new UpdateUserCommand(userId, birth, nickname, name, email, phoneNumber);
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber);
        String encodedPassword = "encoded-password";
        User user = User.createNewUserByCommand(registerUserCommand, encodedPassword);
        UserUpdateResponseDto userUpdateResponseDto = new UserUpdateResponseDto(userId, birth, password, name, email, phoneNumber, false);

        given(readUserPort.findByUserId(userId)).willReturn(Optional.of(user));
        given(userUseCaseConverter.userToUserUpdateResponseDto(any(User.class))).willReturn(userUpdateResponseDto);

        //when
        UserUpdateResponseDto result = updateUserUseCase.updateUser(updateUserCommand);

        //then
        Assertions.assertSame(result.userId(), userId);
    }

    @Test
    @DisplayName("[단위][Use Case] 회원 수정 user does not exist - 실패 테스트")
    void updateUserUseCaseUserDoesNotExistFailTest() {
        //given
        UpdateUserCommand updateUserCommand = new UpdateUserCommand(userId, birth, nickname, name, email, phoneNumber);

        given(readUserPort.findByUserId(userId)).willReturn(Optional.empty());

        //when then
        Assertions.assertThrows(NotFoundResourceException.class, () -> updateUserUseCase.updateUser(updateUserCommand));
    }

    @Test
    @DisplayName("[단위][Use Case] 회원 수정 user withdrew - 실패 테스트")
    void updateUserUseCaseUserWithdrewFailTest() {
        //given
        UpdateUserCommand updateUserCommand = new UpdateUserCommand(userId, birth, nickname, name, email, phoneNumber);
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber);
        String encodedPassword = "encoded-password";
        User user = User.createNewUserByCommand(registerUserCommand, encodedPassword);
        user.userWithdraw();

        given(readUserPort.findByUserId(userId)).willReturn(Optional.of(user));

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> updateUserUseCase.updateUser(updateUserCommand));
    }
}