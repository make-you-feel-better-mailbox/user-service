package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.command.UpdateUserCommand;
import com.onetwo.userservice.application.port.in.user.command.UpdateUserPasswordCommand;
import com.onetwo.userservice.application.port.in.user.response.UserUpdatePasswordResponseDto;
import com.onetwo.userservice.application.port.in.user.response.UserUpdateResponseDto;
import com.onetwo.userservice.application.port.out.user.ReadUserPort;
import com.onetwo.userservice.application.port.out.user.UpdateUserPort;
import com.onetwo.userservice.application.service.converter.UserUseCaseConverter;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseTest {

    @InjectMocks
    private UserService updateUserUseCase;

    @Mock
    private ReadUserPort readUserPort;

    @Mock
    private UserUseCaseConverter userUseCaseConverter;

    @Mock
    private UpdateUserPort updateUserPort;

    @Mock
    private PasswordEncoder passwordEncoder;

    private final String userId = "12OneTwo12";
    private final String password = "password";
    private final String nickname = "newNickname";
    private final String email = "onetwo12@onetwo.com";
    private final String phoneNumber = "01098006069";
    private final String newPassword = "newPassword";
    private final String incorrectPassword = "incorrectPassword";

    private final boolean oauth = false;
    private final String registrationId = null;

    private final RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, nickname, email, phoneNumber, oauth, registrationId);

    @Test
    @DisplayName("[단위][Use Case] 회원 수정 - 성공 테스트")
    void updateUserUseCaseSuccessTest() {
        //given
        UpdateUserCommand updateUserCommand = new UpdateUserCommand(userId, nickname, email, phoneNumber);
        String encodedPassword = "encoded-password";
        User user = User.createNewUserByCommand(registerUserCommand, encodedPassword);
        UserUpdateResponseDto userUpdateResponseDto = new UserUpdateResponseDto(userId, password, email, phoneNumber, false);

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
        UpdateUserCommand updateUserCommand = new UpdateUserCommand(userId, nickname, email, phoneNumber);

        given(readUserPort.findByUserId(userId)).willReturn(Optional.empty());

        //when then
        Assertions.assertThrows(NotFoundResourceException.class, () -> updateUserUseCase.updateUser(updateUserCommand));
    }

    @Test
    @DisplayName("[단위][Use Case] 회원 수정 user withdrew - 실패 테스트")
    void updateUserUseCaseUserWithdrewFailTest() {
        //given
        UpdateUserCommand updateUserCommand = new UpdateUserCommand(userId, nickname, email, phoneNumber);
        String encodedPassword = "encoded-password";
        User user = User.createNewUserByCommand(registerUserCommand, encodedPassword);
        user.userWithdraw();

        given(readUserPort.findByUserId(userId)).willReturn(Optional.of(user));

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> updateUserUseCase.updateUser(updateUserCommand));
    }

    @Test
    @DisplayName("[단위][Use Case] 회원 비밀번호 수정 - 성공 테스트")
    void updateUserPasswordUseCaseSuccessTest() {
        //given
        UpdateUserPasswordCommand updateUserPasswordCommand = new UpdateUserPasswordCommand(userId, password, newPassword, newPassword);
        User user = User.createNewUserByCommand(registerUserCommand, password);
        UserUpdatePasswordResponseDto userUpdatePasswordResponseDto = new UserUpdatePasswordResponseDto(true);

        given(readUserPort.findByUserId(userId)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(password, password)).willReturn(true);
        given(passwordEncoder.matches(newPassword, password)).willReturn(false);
        given(passwordEncoder.encode(anyString())).willReturn(password);
        given(userUseCaseConverter.toUserUpdatePasswordResponseDto(anyBoolean())).willReturn(userUpdatePasswordResponseDto);

        //when
        UserUpdatePasswordResponseDto result = updateUserUseCase.updatePassword(updateUserPasswordCommand);

        //then
        Assertions.assertTrue(result.isSuccess());
    }

    @Test
    @DisplayName("[단위][Use Case] 회원 비밀번호 수정 User does not exist - 실패 테스트")
    void updateUserPasswordUseCaseUserDoesNotExistFailTest() {
        //given
        UpdateUserPasswordCommand updateUserPasswordCommand = new UpdateUserPasswordCommand(userId, password, newPassword, newPassword);

        given(readUserPort.findByUserId(userId)).willReturn(Optional.empty());

        //when then
        Assertions.assertThrows(NotFoundResourceException.class, () -> updateUserUseCase.updatePassword(updateUserPasswordCommand));
    }

    @Test
    @DisplayName("[단위][Use Case] 회원 비밀번호 수정 user already withdraw - 실패 테스트")
    void updateUserPasswordUseCaseUserAlreadyWithdrawFailTest() {
        //given
        UpdateUserPasswordCommand updateUserPasswordCommand = new UpdateUserPasswordCommand(userId, incorrectPassword, newPassword, newPassword);
        User user = User.createNewUserByCommand(registerUserCommand, password);

        user.userWithdraw();

        given(readUserPort.findByUserId(userId)).willReturn(Optional.of(user));

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> updateUserUseCase.updatePassword(updateUserPasswordCommand));
    }

    @Test
    @DisplayName("[단위][Use Case] 회원 비밀번호 수정 Incorrect password - 실패 테스트")
    void updateUserPasswordUseCaseIncorrectPasswordFailTest() {
        //given
        UpdateUserPasswordCommand updateUserPasswordCommand = new UpdateUserPasswordCommand(userId, incorrectPassword, newPassword, newPassword);
        User user = User.createNewUserByCommand(registerUserCommand, password);

        given(readUserPort.findByUserId(userId)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> updateUserUseCase.updatePassword(updateUserPasswordCommand));
    }

    @Test
    @DisplayName("[단위][Use Case] 회원 비밀번호 수정 not matched new password and new password check - 실패 테스트")
    void updateUserPasswordUseCaseNotMatchedNewPasswordAndPasswordCheckFailTest() {
        //given
        UpdateUserPasswordCommand updateUserPasswordCommand = new UpdateUserPasswordCommand(userId, password, newPassword, incorrectPassword);
        User user = User.createNewUserByCommand(registerUserCommand, password);

        given(readUserPort.findByUserId(userId)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> updateUserUseCase.updatePassword(updateUserPasswordCommand));
    }

    @Test
    @DisplayName("[단위][Use Case] 회원 비밀번호 수정 Current password and new password same - 실패 테스트")
    void updateUserPasswordUseCaseIncorrectNewPasswordFailTest() {
        //given
        UpdateUserPasswordCommand updateUserPasswordCommand = new UpdateUserPasswordCommand(userId, password, password, password);
        User user = User.createNewUserByCommand(registerUserCommand, password);

        given(readUserPort.findByUserId(userId)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
        given(passwordEncoder.matches(password, password)).willReturn(true);

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> updateUserUseCase.updatePassword(updateUserPasswordCommand));
    }
}