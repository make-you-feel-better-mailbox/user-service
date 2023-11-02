package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.command.UpdateUserCommand;
import com.onetwo.userservice.application.port.in.user.command.UpdateUserPasswordCommand;
import com.onetwo.userservice.application.port.in.user.response.UserUpdatePasswordResponseDto;
import com.onetwo.userservice.application.port.in.user.response.UserUpdateResponseDto;
import com.onetwo.userservice.application.port.out.user.RegisterUserPort;
import com.onetwo.userservice.application.port.out.user.UpdateUserPort;
import com.onetwo.userservice.common.exceptions.BadRequestException;
import com.onetwo.userservice.common.exceptions.NotFoundResourceException;
import com.onetwo.userservice.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@SpringBootTest
@Transactional
class UpdateUserUseCaseBootTest {

    @Autowired
    private UpdateUserUseCase updateUserUseCase;

    @Autowired
    private RegisterUserPort registerUserPort;

    @Autowired
    private UpdateUserPort updateUserPort;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final String userId = "12OneTwo12";
    private final String password = "password";
    private final Instant birth = Instant.now();
    private final String nickname = "newNickname";
    private final String name = "tester";
    private final String email = "onetwo12@onetwo.com";
    private final String phoneNumber = "01098006069";
    private final String newPassword = "newPassword";
    private final String incorrectPassword = "incorrectPassword";

    @Test
    @DisplayName("[통합][Use Case] 회원 수정 - 성공 테스트")
    void updateUserUseCaseSuccessTest() {
        //given
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber);
        User user = User.createNewUserByCommand(registerUserCommand, passwordEncoder.encode(password));

        registerUserPort.registerNewUser(user);

        UpdateUserCommand updateUserCommand = new UpdateUserCommand(userId, birth, nickname, name, email, phoneNumber);

        //when
        UserUpdateResponseDto result = updateUserUseCase.updateUser(updateUserCommand);

        //then
        Assertions.assertSame(result.userId(), userId);
    }

    @Test
    @DisplayName("[통합][Use Case] 회원 수정 user does not exist - 실패 테스트")
    void updateUserUseCaseUserDoesNotExistFailTest() {
        //given
        UpdateUserCommand updateUserCommand = new UpdateUserCommand(userId, birth, nickname, name, email, phoneNumber);

        //when then
        Assertions.assertThrows(NotFoundResourceException.class, () -> updateUserUseCase.updateUser(updateUserCommand));
    }

    @Test
    @DisplayName("[통합][Use Case] 회원 수정 user withdrew - 실패 테스트")
    void updateUserUseCaseUserWithdrewFailTest() {
        //given
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber);
        User user = User.createNewUserByCommand(registerUserCommand, passwordEncoder.encode(password));

        User savedUser = registerUserPort.registerNewUser(user);
        savedUser.userWithdraw();
        updateUserPort.updateUser(savedUser);

        UpdateUserCommand updateUserCommand = new UpdateUserCommand(userId, birth, nickname, name, email, phoneNumber);

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> updateUserUseCase.updateUser(updateUserCommand));
    }


    @Test
    @DisplayName("[통합][Use Case] 회원 비밀번호 수정 - 성공 테스트")
    void updateUserPasswordUseCaseSuccessTest() {
        //given
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber);
        User user = User.createNewUserByCommand(registerUserCommand, passwordEncoder.encode(password));

        registerUserPort.registerNewUser(user);

        UpdateUserPasswordCommand updateUserPasswordCommand = new UpdateUserPasswordCommand(userId, password, newPassword, newPassword);

        //when
        UserUpdatePasswordResponseDto result = updateUserUseCase.updatePassword(updateUserPasswordCommand);

        //then
        Assertions.assertTrue(result.isSuccess());
    }

    @Test
    @DisplayName("[통합][Use Case] 회원 비밀번호 수정 User does not exist - 실패 테스트")
    void updateUserPasswordUseCaseUserDoesNotExistFailTest() {
        //given
        UpdateUserPasswordCommand updateUserPasswordCommand = new UpdateUserPasswordCommand(userId, password, newPassword, newPassword);

        //when then
        Assertions.assertThrows(NotFoundResourceException.class, () -> updateUserUseCase.updatePassword(updateUserPasswordCommand));
    }

    @Test
    @DisplayName("[통합][Use Case] 회원 비밀번호 수정 user already withdraw - 실패 테스트")
    void updateUserPasswordUseCaseUserAlreadyWithdrawFailTest() {
        //given
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber);
        User user = User.createNewUserByCommand(registerUserCommand, passwordEncoder.encode(password));

        User savedUser = registerUserPort.registerNewUser(user);
        savedUser.userWithdraw();
        updateUserPort.updateUser(savedUser);

        UpdateUserPasswordCommand updateUserPasswordCommand = new UpdateUserPasswordCommand(userId, incorrectPassword, newPassword, newPassword);

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> updateUserUseCase.updatePassword(updateUserPasswordCommand));
    }

    @Test
    @DisplayName("[통합][Use Case] 회원 비밀번호 수정 Incorrect password - 실패 테스트")
    void updateUserPasswordUseCaseIncorrectPasswordFailTest() {
        //given
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber);
        User user = User.createNewUserByCommand(registerUserCommand, passwordEncoder.encode(password));

        registerUserPort.registerNewUser(user);

        UpdateUserPasswordCommand updateUserPasswordCommand = new UpdateUserPasswordCommand(userId, incorrectPassword, newPassword, newPassword);

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> updateUserUseCase.updatePassword(updateUserPasswordCommand));
    }

    @Test
    @DisplayName("[통합][Use Case] 회원 비밀번호 수정 not matched new password and new password check - 실패 테스트")
    void updateUserPasswordUseCaseNotMatchedNewPasswordAndPasswordCheckFailTest() {
        //given
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber);
        User user = User.createNewUserByCommand(registerUserCommand, passwordEncoder.encode(password));

        registerUserPort.registerNewUser(user);

        UpdateUserPasswordCommand updateUserPasswordCommand = new UpdateUserPasswordCommand(userId, password, newPassword, incorrectPassword);

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> updateUserUseCase.updatePassword(updateUserPasswordCommand));
    }

    @Test
    @DisplayName("[통합][Use Case] 회원 비밀번호 수정 Current password and new password same - 실패 테스트")
    void updateUserPasswordUseCaseIncorrectNewPasswordFailTest() {
        //given
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber);
        User user = User.createNewUserByCommand(registerUserCommand, passwordEncoder.encode(password));

        registerUserPort.registerNewUser(user);

        UpdateUserPasswordCommand updateUserPasswordCommand = new UpdateUserPasswordCommand(userId, password, password, password);

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> updateUserUseCase.updatePassword(updateUserPasswordCommand));
    }
}
