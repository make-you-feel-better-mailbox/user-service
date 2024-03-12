package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.response.UserDetailResponseDto;
import com.onetwo.userservice.application.port.in.user.response.UserIdExistCheckDto;
import com.onetwo.userservice.application.port.out.user.RegisterUserPort;
import com.onetwo.userservice.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ReadUserUseCaseBootTest {

    @Autowired
    private ReadUserUseCase readUserUseCase;

    @Autowired
    private RegisterUserPort registerUserPort;

    private final String userId = "12OneTwo12";
    private final String password = "password";
    private final String nickname = "newNickname";
    private final String email = "onetwo12@onetwo.com";
    private final String phoneNumber = "01098006069";
    private final boolean oauth = false;
    private final String registrationId = null;

    private final RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, nickname, email, phoneNumber, oauth, registrationId);


    @Test
    @DisplayName("[통합][Use Case] 회원 ID 존재여부 확인 - 성공 테스트")
    void userIdExistCheckUseCaseSuccessTest() {
        //given when
        UserIdExistCheckDto userIdExistCheckResultDto = readUserUseCase.userIdExistCheck(userId);

        //then
        Assertions.assertFalse(userIdExistCheckResultDto.userIdExist());
    }

    @Test
    @DisplayName("[통합][Use Case] 회원 상세정보 - 성공 테스트 ")
    void getUserDetailInfoUseCaseSuccessTest() {
        //given
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(userId, password, nickname, email, phoneNumber, oauth, registrationId);
        User user = User.createNewUserByCommand(registerUserCommand, password);

        registerUserPort.registerNewUser(user);

        //when
        UserDetailResponseDto result = readUserUseCase.getUserDetailInfo(userId);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.state());
    }
}
