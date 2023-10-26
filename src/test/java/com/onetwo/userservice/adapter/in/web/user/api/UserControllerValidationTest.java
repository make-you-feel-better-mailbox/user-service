package com.onetwo.userservice.adapter.in.web.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.userservice.adapter.in.web.user.mapper.UserDtoMapper;
import com.onetwo.userservice.adapter.in.web.user.request.RegisterUserRequest;
import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.service.response.UserRegisterResponseDto;
import com.onetwo.userservice.application.service.service.UserService;
import com.onetwo.userservice.common.GlobalUrl;
import com.onetwo.userservice.common.config.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.filter.OncePerRequestFilter;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
                        SecurityConfig.class,
                        OncePerRequestFilter.class
                })
        }
)
class UserControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDtoMapper userDtoMapper;

    private final String userId = "12OneTwo12";
    private final String password = "password";
    private final Instant birth = Instant.now();
    private final String nickname = "newNickname";
    private final String name = "tester";
    private final String email = "onetwo12@onetwo.com";
    private final String phoneNumber = "01098006069";
    private final boolean userState = false;

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Web Adapter] 회원 회원가입 유효성검사 userId - 실패 테스트")
    void registerUserValidationUserIdFailTest(String userId) throws Exception {
        //given
        RegisterUserRequest registerUserRequest = new RegisterUserRequest(userId, password, birth, nickname, name, email, phoneNumber);

        //when
        ResultActions resultActions = mockMvc.perform(
                post(GlobalUrl.USER_ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUserRequest))
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Web Adapter] 회원 회원가입 유효성검사 password - 실패 테스트")
    void registerUserValidationPasswordFailTest(String password) throws Exception {
        //given
        RegisterUserRequest registerUserRequest = new RegisterUserRequest(userId, password, birth, nickname, name, email, phoneNumber);

        //when
        ResultActions resultActions = mockMvc.perform(
                post(GlobalUrl.USER_ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUserRequest))
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("[단위][Web Adapter] 회원 회원가입 유효성검사 birth - 실패 테스트")
    void registerUserValidationBirthFailTest(Instant birth) throws Exception {
        //given
        RegisterUserRequest registerUserRequest = new RegisterUserRequest(userId, password, birth, nickname, name, email, phoneNumber);

        //when
        ResultActions resultActions = mockMvc.perform(
                post(GlobalUrl.USER_ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUserRequest))
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Web Adapter] 회원 회원가입 유효성검사 nickname - 실패 테스트")
    void registerUserValidationNicknameFailTest(String nickname) throws Exception {
        //given
        RegisterUserRequest registerUserRequest = new RegisterUserRequest(userId, password, birth, nickname, name, email, phoneNumber);

        //when
        ResultActions resultActions = mockMvc.perform(
                post(GlobalUrl.USER_ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUserRequest))
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Web Adapter] 회원 회원가입 유효성검사 name - 실패 테스트")
    void registerUserValidationNameFailTest(String name) throws Exception {
        //given
        RegisterUserRequest registerUserRequest = new RegisterUserRequest(userId, password, birth, nickname, name, email, phoneNumber);

        //when
        ResultActions resultActions = mockMvc.perform(
                post(GlobalUrl.USER_ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUserRequest))
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Web Adapter] 회원 회원가입 유효성검사 email - 실패 테스트")
    void registerUserValidationEmailFailTest(String email) throws Exception {
        //given
        RegisterUserRequest registerUserRequest = new RegisterUserRequest(userId, password, birth, nickname, name, email, phoneNumber);

        //when
        ResultActions resultActions = mockMvc.perform(
                post(GlobalUrl.USER_ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUserRequest))
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Web Adapter] 회원 회원가입 유효성검사 phoneNumber null - 성공 테스트")
    void registerUserValidationPhoneNumberNullSuccessTest(String phoneNumber) throws Exception {
        //given
        RegisterUserRequest registerUserRequest = new RegisterUserRequest(userId, password, birth, nickname, name, email, phoneNumber);

        UserRegisterResponseDto savedUser = new UserRegisterResponseDto(userId);

        when(userService.registerUser(any(RegisterUserCommand.class))).thenReturn(savedUser);
        //when
        ResultActions resultActions = mockMvc.perform(
                post(GlobalUrl.USER_ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUserRequest))
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isCreated())
                .andDo(print());
    }
}
