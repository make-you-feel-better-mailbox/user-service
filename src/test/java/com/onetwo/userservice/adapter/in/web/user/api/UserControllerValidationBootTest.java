package com.onetwo.userservice.adapter.in.web.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.userservice.adapter.in.web.user.request.UpdateUserRequest;
import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.usecase.RegisterUserUseCase;
import com.onetwo.userservice.common.GlobalStatus;
import com.onetwo.userservice.common.GlobalUrl;
import com.onetwo.userservice.domain.user.MyUserDetail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerValidationBootTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RegisterUserUseCase registerUserUseCase;

    private final String userId = "12OneTwo12";
    private final String password = "password";
    private final Instant birth = Instant.now();
    private final String nickname = "newNickname";
    private final String name = "tester";
    private final String email = "onetwo12@onetwo.com";
    private final String phoneNumber = "01098006069";

    private static final HttpHeaders httpHeaders = new HttpHeaders();

    @BeforeAll
    static void settingHeader(@Value("${access-id}") String accessId, @Value("${access-key}") String accessKey) {
        httpHeaders.add(GlobalStatus.ACCESS_ID, accessId);
        httpHeaders.add(GlobalStatus.ACCESS_KEY, accessKey);
    }

    @ParameterizedTest
    @NullSource
    @WithMockUser(username = userId)
    @DisplayName("[통합][Web Adapter] 회원 수정 유효성검사 birth - 실패 테스트")
    void updateUserValidationBirthFailTest(Instant birth) throws Exception {
        //given
        UpdateUserRequest updateUserRequest = new UpdateUserRequest(birth, nickname, name, email, phoneNumber);

        //when
        ResultActions resultActions = mockMvc.perform(
                put(GlobalUrl.USER_ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(httpHeaders)
                        .content(objectMapper.writeValueAsString(updateUserRequest))
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @WithMockUser(username = userId)
    @DisplayName("[통합][Web Adapter] 회원 수정 유효성검사 nickname - 실패 테스트")
    void updateUserValidationNicknameFailTest(String nickname) throws Exception {
        //given
        UpdateUserRequest updateUserRequest = new UpdateUserRequest(birth, nickname, name, email, phoneNumber);

        //when
        ResultActions resultActions = mockMvc.perform(
                put(GlobalUrl.USER_ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(httpHeaders)
                        .content(objectMapper.writeValueAsString(updateUserRequest))
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @WithMockUser(username = userId)
    @DisplayName("[통합][Web Adapter] 회원 수정 유효성검사 name - 실패 테스트")
    void updateUserValidationNameFailTest(String name) throws Exception {
        //given
        UpdateUserRequest updateUserRequest = new UpdateUserRequest(birth, nickname, name, email, phoneNumber);

        //when
        ResultActions resultActions = mockMvc.perform(
                put(GlobalUrl.USER_ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(httpHeaders)
                        .content(objectMapper.writeValueAsString(updateUserRequest))
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @WithMockUser(username = userId)
    @DisplayName("[통합][Web Adapter] 회원 수정 유효성검사 email - 실패 테스트")
    void updateUserValidationEmailFailTest(String email) throws Exception {
        //given
        UpdateUserRequest updateUserRequest = new UpdateUserRequest(birth, nickname, name, email, phoneNumber);
        //when
        ResultActions resultActions = mockMvc.perform(
                put(GlobalUrl.USER_ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(httpHeaders)
                        .content(objectMapper.writeValueAsString(updateUserRequest))
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @Transactional
    @WithMockUser(username = userId)
    @DisplayName("[통합][Web Adapter] 회원 수정 유효성검사 phoneNumber null - 성공 테스트")
    void updateUserValidationPhoneNumberNullSuccessTest(String phoneNumber) throws Exception {
        //given
        registerUserUseCase.registerUser(new RegisterUserCommand(userId, password, birth, nickname, name, email, this.phoneNumber));
        UpdateUserRequest updateUserRequest = new UpdateUserRequest(birth, nickname, name, email, phoneNumber);

        MyUserDetail myUserDetail = new MyUserDetail(userId, password, false, new HashSet<>());
        Authentication authentication = new UsernamePasswordAuthenticationToken(myUserDetail, password, myUserDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //when
        ResultActions resultActions = mockMvc.perform(
                put(GlobalUrl.USER_ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(httpHeaders)
                        .content(objectMapper.writeValueAsString(updateUserRequest))
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }
}
