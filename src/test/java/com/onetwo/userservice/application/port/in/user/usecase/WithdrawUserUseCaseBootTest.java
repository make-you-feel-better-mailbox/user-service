package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.command.WithdrawUserCommand;
import com.onetwo.userservice.application.port.in.user.response.UserWithdrawResponseDto;
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
class WithdrawUserUseCaseBootTest {

    @Autowired
    private WithdrawUserUseCase withdrawUserUseCase;

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

    @Test
    @DisplayName("[통합][Use Case] 회원 탈퇴 - 성공 테스트")
    void withdrawUserUseCaseSuccessTest() {
        //given
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber);
        User user = User.createNewUserByCommand(registerUserCommand, passwordEncoder.encode(password));

        registerUserPort.registerNewUser(user);

        WithdrawUserCommand withdrawUserCommand = new WithdrawUserCommand(userId, password, userId);

        //when
        UserWithdrawResponseDto result = withdrawUserUseCase.withdrawUser(withdrawUserCommand);

        //then
        Assertions.assertTrue(result.isWithdrawSuccess());
    }

    @Test
    @DisplayName("[통합][Use Case] 회원 탈퇴 different user request withdraw - 실패 테스트")
    void withdrawUserUseCaseDifferentUserRequestFailTest() {
        //given
        String anotherUser = "anotherUserId";
        WithdrawUserCommand withdrawUserCommand = new WithdrawUserCommand(userId, password, anotherUser);

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> withdrawUserUseCase.withdrawUser(withdrawUserCommand));
    }

    @Test
    @DisplayName("[통합][Use Case] 회원 탈퇴 User does not exist - 실패 테스트")
    void withdrawUserUseCaseUserDoesNotExistFailTest() {
        //given
        WithdrawUserCommand withdrawUserCommand = new WithdrawUserCommand(userId, password, userId);

        //when then
        Assertions.assertThrows(NotFoundResourceException.class, () -> withdrawUserUseCase.withdrawUser(withdrawUserCommand));
    }

    @Test
    @DisplayName("[통합][Use Case] 회원 탈퇴 User password not matched - 실패 테스트")
    void withdrawUserUseCasePasswordNotMatchedFailTest() {
        //given
        String incorrectPassword = "incorrectPassword";

        RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber);
        User user = User.createNewUserByCommand(registerUserCommand, passwordEncoder.encode(incorrectPassword));

        registerUserPort.registerNewUser(user);

        WithdrawUserCommand withdrawUserCommand = new WithdrawUserCommand(userId, password, userId);

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> withdrawUserUseCase.withdrawUser(withdrawUserCommand));
    }

    @Test
    @DisplayName("[통합][Use Case] 회원 탈퇴 User already withdrew - 실패 테스트")
    void withdrawUserUseCaseAlreadyWithdrewFailTest() {
        //given
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber);
        User user = User.createNewUserByCommand(registerUserCommand, passwordEncoder.encode(password));

        User savedUser = registerUserPort.registerNewUser(user);
        savedUser.userWithdraw();
        updateUserPort.updateUser(savedUser);

        WithdrawUserCommand withdrawUserCommand = new WithdrawUserCommand(userId, password, userId);

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> withdrawUserUseCase.withdrawUser(withdrawUserCommand));
    }
}
