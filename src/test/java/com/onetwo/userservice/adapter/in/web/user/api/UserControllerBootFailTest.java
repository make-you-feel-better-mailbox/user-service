package com.onetwo.userservice.adapter.in.web.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.userservice.adapter.in.web.user.request.RegisterUserRequest;
import com.onetwo.userservice.adapter.in.web.user.request.UpdateUserPasswordRequest;
import com.onetwo.userservice.adapter.in.web.user.request.WithdrawUserRequest;
import com.onetwo.userservice.adapter.out.persistence.repository.user.UserRepository;
import com.onetwo.userservice.application.port.in.user.command.LoginUserCommand;
import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.response.TokenResponseDto;
import com.onetwo.userservice.application.port.in.user.usecase.LoginUseCase;
import com.onetwo.userservice.application.port.in.user.usecase.RegisterUserUseCase;
import com.onetwo.userservice.common.GlobalStatus;
import com.onetwo.userservice.common.GlobalUrl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerBootFailTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RegisterUserUseCase registerUserUseCase;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginUseCase loginUseCase;

    private final String userId = "12OneTwo12";
    private final String password = "password";
    private final String nickname = "newNickname";
    private final String email = "onetwo12@onetwo.com";
    private final String phoneNumber = "01098006069";

    private static final HttpHeaders httpHeaders = new HttpHeaders();

    @BeforeAll
    static void settingHeader(@Value("${access-id}") String accessId, @Value("${access-key}") String accessKey) {
        httpHeaders.add(GlobalStatus.ACCESS_ID, accessId);
        httpHeaders.add(GlobalStatus.ACCESS_KEY, accessKey);
    }

    @Test
    @Transactional
    @DisplayName("[통합][Web Adapter] 회원 회원가입 User Id Already Exist - 실패 테스트")
    void registerUserAlreadyExistFailTest() throws Exception {
        //given
        registerUserUseCase.registerUser(new RegisterUserCommand(userId, password, nickname, email, phoneNumber));

        RegisterUserRequest registerUserRequest = new RegisterUserRequest(userId, password, nickname, email, phoneNumber);

        //when
        ResultActions resultActions = mockMvc.perform(
                post(GlobalUrl.USER_ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUserRequest))
                        .headers(httpHeaders)
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("[통합][Web Adapter] 회원 탈퇴 타회원 탈퇴요청 실패 - 실패 테스트")
    void withdrawUserWrongUserRequestFailTest() throws Exception {
        //given
        String otherUserId = "otherUserId";

        registerUserUseCase.registerUser(new RegisterUserCommand(userId, password, nickname, email, phoneNumber));
        registerUserUseCase.registerUser(new RegisterUserCommand(otherUserId, password, nickname, email, phoneNumber));

        LoginUserCommand loginUserRequest = new LoginUserCommand(otherUserId, password);
        TokenResponseDto tokenResponse = loginUseCase.loginUser(loginUserRequest);

        WithdrawUserRequest withdrawUserRequest = new WithdrawUserRequest(userId, password);

        HttpHeaders withdrawUserRequestHeader = new HttpHeaders();
        withdrawUserRequestHeader.add(GlobalStatus.ACCESS_ID, httpHeaders.getFirst(GlobalStatus.ACCESS_ID));
        withdrawUserRequestHeader.add(GlobalStatus.ACCESS_KEY, httpHeaders.getFirst(GlobalStatus.ACCESS_KEY));
        withdrawUserRequestHeader.add(GlobalStatus.ACCESS_TOKEN, tokenResponse.accessToken());

        //when
        ResultActions resultActions = mockMvc.perform(
                delete(GlobalUrl.USER_ROOT)
                        .headers(withdrawUserRequestHeader)
                        .content(objectMapper.writeValueAsString(withdrawUserRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("[통합][Web Adapter] 회원 비밀번호 수정 기존 비밀번호 불일치 - 실패 테스트")
    void updateUserPasswordCurrentPasswordIncorrectFailTest() throws Exception {
        //given
        LoginUserCommand loginUserRequest = new LoginUserCommand(userId, password);

        registerUserUseCase.registerUser(new RegisterUserCommand(userId, password, nickname, email, phoneNumber));

        TokenResponseDto tokenResponse = loginUseCase.loginUser(loginUserRequest);

        String newPassword = "newPassword";

        UpdateUserPasswordRequest updateUserPasswordRequest = new UpdateUserPasswordRequest(newPassword, newPassword, newPassword);

        HttpHeaders updateUserRequestHeader = new HttpHeaders();
        updateUserRequestHeader.add(GlobalStatus.ACCESS_ID, httpHeaders.getFirst(GlobalStatus.ACCESS_ID));
        updateUserRequestHeader.add(GlobalStatus.ACCESS_KEY, httpHeaders.getFirst(GlobalStatus.ACCESS_KEY));
        updateUserRequestHeader.add(GlobalStatus.ACCESS_TOKEN, tokenResponse.accessToken());

        //when
        ResultActions resultActions = mockMvc.perform(
                put(GlobalUrl.USER_PW)
                        .headers(updateUserRequestHeader)
                        .content(objectMapper.writeValueAsString(updateUserPasswordRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("[통합][Web Adapter] 회원 비밀번호 수정 신규 비밀번호 불일치 - 실패 테스트")
    void updateUserPasswordNewPasswordIncorrectFailTest() throws Exception {
        //given
        LoginUserCommand loginUserRequest = new LoginUserCommand(userId, password);

        registerUserUseCase.registerUser(new RegisterUserCommand(userId, password, nickname, email, phoneNumber));

        TokenResponseDto tokenResponse = loginUseCase.loginUser(loginUserRequest);

        String newPassword = "newPassword";
        String newPasswordCheck = "anotherPassword";

        UpdateUserPasswordRequest updateUserPasswordRequest = new UpdateUserPasswordRequest(password, newPassword, newPasswordCheck);

        HttpHeaders updateUserRequestHeader = new HttpHeaders();
        updateUserRequestHeader.add(GlobalStatus.ACCESS_ID, httpHeaders.getFirst(GlobalStatus.ACCESS_ID));
        updateUserRequestHeader.add(GlobalStatus.ACCESS_KEY, httpHeaders.getFirst(GlobalStatus.ACCESS_KEY));
        updateUserRequestHeader.add(GlobalStatus.ACCESS_TOKEN, tokenResponse.accessToken());

        //when
        ResultActions resultActions = mockMvc.perform(
                put(GlobalUrl.USER_PW)
                        .headers(updateUserRequestHeader)
                        .content(objectMapper.writeValueAsString(updateUserPasswordRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }
}
