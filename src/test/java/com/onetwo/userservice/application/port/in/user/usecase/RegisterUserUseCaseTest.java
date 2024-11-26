package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.response.UserRegisterResponseDto;
import com.onetwo.userservice.application.port.out.event.UserRegisterEventPublisherPort;
import com.onetwo.userservice.application.port.out.user.ReadUserPort;
import com.onetwo.userservice.application.port.out.user.RegisterUserPort;
import com.onetwo.userservice.application.service.converter.UserUseCaseConverter;
import com.onetwo.userservice.application.service.service.UserService;
import com.onetwo.userservice.common.exceptions.ResourceAlreadyExistsException;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RegisterUserUseCaseTest {

    @InjectMocks
    private UserService registerUserUseCase;

    @Mock
    private UserUseCaseConverter userUseCaseConverter;

    @Mock
    private ReadUserPort readUserPort;

    @Mock
    private RegisterUserPort registerUserPort;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRegisterEventPublisherPort userRegisterEventPublisherPort;

    private final String userId = "12OneTwo12";
    private final String password = "password";
    private final String nickname = "newNickname";
    private final String email = "onetwo12@onetwo.com";
    private final String phoneNumber = "01098006069";
    private final boolean oauth = false;
    private final String registrationId = null;

    private final RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, nickname, email, phoneNumber, oauth, registrationId);

    @Test
    @DisplayName("[단위][Use Case] 회원 회원가입 - 성공 테스트")
    void registerUserUseCaseSuccessTest() {
        //given
        String encodedPassword = "encoded-password";
        User savedUser = User.createNewUserByCommand(registerUserCommand, encodedPassword);
        UserRegisterResponseDto userRegisterResponseDto = new UserRegisterResponseDto(userId);

        given(readUserPort.findByUserId(userId)).willReturn(Optional.empty());
        given(passwordEncoder.encode(anyString())).willReturn(encodedPassword);
        given(registerUserPort.registerNewUser(any(User.class))).willReturn(savedUser);
        given(userUseCaseConverter.userToUserRegisterResponseDto(any(User.class))).willReturn(userRegisterResponseDto);

        //when
        UserRegisterResponseDto result = registerUserUseCase.registerUser(registerUserCommand);

        //then
        Assertions.assertSame(result.userId(), userId);
    }

    @Test
    @DisplayName("[단위][Use Case] 회원 회원가입 User id already exist - 실패 테스트")
    void registerUserUseCaseUserIdExistFailTest() {
        //given
        String encodedPassword = "encoded-password";
        User user = User.createNewUserByCommand(registerUserCommand, encodedPassword);

        given(readUserPort.findByUserId(userId)).willReturn(Optional.of(user));

        //when then
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> registerUserUseCase.registerUser(registerUserCommand));
    }
}