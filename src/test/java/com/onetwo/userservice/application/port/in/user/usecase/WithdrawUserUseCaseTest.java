package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.command.WithdrawUserCommand;
import com.onetwo.userservice.application.port.out.user.ReadUserPort;
import com.onetwo.userservice.application.port.out.user.UpdateUserPort;
import com.onetwo.userservice.application.service.converter.UserUseCaseConverter;
import com.onetwo.userservice.application.port.in.user.response.UserWithdrawResponseDto;
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

import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class WithdrawUserUseCaseTest {

    @InjectMocks
    private UserService withdrawUserUseCase;

    @Mock
    private ReadUserPort readUserPort;

    @Mock
    private PasswordEncoder passwordEncoder;

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
    @DisplayName("[단위][Use Case] 회원 탈퇴 - 성공 테스트")
    void withdrawUserUseCaseSuccessTest() {
        //given
        WithdrawUserCommand withdrawUserCommand = new WithdrawUserCommand(userId, password, userId);

        RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber);
        User user = User.createNewUserByCommand(registerUserCommand, password);

        UserWithdrawResponseDto withdrawResponseDto = new UserWithdrawResponseDto(true);

        given(readUserPort.findByUserId(userId)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
        given(userUseCaseConverter.userToUserWithdrawResponseDto(any(User.class))).willReturn(withdrawResponseDto);

        //when
        UserWithdrawResponseDto result = withdrawUserUseCase.withdrawUser(withdrawUserCommand);

        //then
        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("[단위][Use Case] 회원 탈퇴 different user request withdraw - 실패 테스트")
    void withdrawUserUseCaseDifferentUserRequestFailTest() {
        //given
        String anotherUser = "anotherUserId";
        WithdrawUserCommand withdrawUserCommand = new WithdrawUserCommand(userId, password, anotherUser);

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> withdrawUserUseCase.withdrawUser(withdrawUserCommand));
    }

    @Test
    @DisplayName("[단위][Use Case] 회원 탈퇴 User does not exist - 실패 테스트")
    void withdrawUserUseCaseUserDoesNotExistFailTest() {
        //given
        WithdrawUserCommand withdrawUserCommand = new WithdrawUserCommand(userId, password, userId);

        given(readUserPort.findByUserId(userId)).willReturn(Optional.empty());

        //when then
        Assertions.assertThrows(NotFoundResourceException.class, () -> withdrawUserUseCase.withdrawUser(withdrawUserCommand));
    }

    @Test
    @DisplayName("[단위][Use Case] 회원 탈퇴 User password not matched - 실패 테스트")
    void withdrawUserUseCasePasswordNotMatchedFailTest() {
        //given
        WithdrawUserCommand withdrawUserCommand = new WithdrawUserCommand(userId, password, userId);
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber);
        User user = User.createNewUserByCommand(registerUserCommand, password);

        given(readUserPort.findByUserId(userId)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> withdrawUserUseCase.withdrawUser(withdrawUserCommand));
    }

    @Test
    @DisplayName("[단위][Use Case] 회원 탈퇴 User already withdrew - 실패 테스트")
    void withdrawUserUseCaseAlreadyWithdrewFailTest() {
        //given
        WithdrawUserCommand withdrawUserCommand = new WithdrawUserCommand(userId, password, userId);
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber);
        User user = User.createNewUserByCommand(registerUserCommand, password);
        user.userWithdraw();

        given(readUserPort.findByUserId(userId)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> withdrawUserUseCase.withdrawUser(withdrawUserCommand));
    }
}