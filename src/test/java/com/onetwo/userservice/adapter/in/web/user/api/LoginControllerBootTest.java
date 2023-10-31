package com.onetwo.userservice.adapter.in.web.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.userservice.adapter.in.web.user.request.LoginUserRequest;
import com.onetwo.userservice.application.port.in.user.command.LoginUserCommand;
import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.usecase.LoginUseCase;
import com.onetwo.userservice.application.port.in.user.usecase.RegisterUserUseCase;
import com.onetwo.userservice.application.port.in.user.response.TokenResponseDto;
import com.onetwo.userservice.common.GlobalStatus;
import com.onetwo.userservice.common.GlobalUrl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class LoginControllerBootTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RegisterUserUseCase registerUserUseCase;

    @Autowired
    private LoginUseCase loginUseCase;

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

    @Test
    @Transactional
    @DisplayName("[통합][Web Adapter] 회원 로그인 - 성공 테스트")
    void loginUserSuccessTest() throws Exception {
        //given
        LoginUserRequest loginUserRequest = new LoginUserRequest(userId, password);

        registerUserUseCase.registerUser(new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber));

        //when
        ResultActions resultActions = mockMvc.perform(
                post(GlobalUrl.USER_LOGIN)
                        .headers(httpHeaders)
                        .content(objectMapper.writeValueAsString(loginUserRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-login",
                                requestHeaders(
                                        headerWithName(GlobalStatus.ACCESS_ID).description("서버 Access id"),
                                        headerWithName(GlobalStatus.ACCESS_KEY).description("서버 Access key")
                                ),
                                requestFields(
                                        fieldWithPath("userId").type(JsonFieldType.STRING).description("로그인할 유저의 ID"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("로그인할 유저의 Password")
                                ),
                                responseFields(
                                        fieldWithPath("accessToken").type(JsonFieldType.STRING).description("유저의 access-token"),
                                        fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("유저의 refresh-token")
                                )
                        )
                );
    }

    @Test
    @Transactional
    @DisplayName("[통합][Web Adapter] 회원 로그아웃 - 성공 테스트")
    void logoutUserSuccessTest() throws Exception {
        //given
        LoginUserCommand loginUserRequest = new LoginUserCommand(userId, password);

        registerUserUseCase.registerUser(new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber));

        TokenResponseDto tokenResponse = loginUseCase.loginUser(loginUserRequest);

        HttpHeaders logoutRequestHeader = new HttpHeaders();
        logoutRequestHeader.add(GlobalStatus.ACCESS_ID, httpHeaders.getFirst(GlobalStatus.ACCESS_ID));
        logoutRequestHeader.add(GlobalStatus.ACCESS_KEY, httpHeaders.getFirst(GlobalStatus.ACCESS_KEY));
        logoutRequestHeader.add(GlobalStatus.ACCESS_TOKEN, tokenResponse.accessToken());

        //when
        ResultActions resultActions = mockMvc.perform(
                delete(GlobalUrl.USER_LOGIN)
                        .headers(logoutRequestHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-logout",
                                requestHeaders(
                                        headerWithName(GlobalStatus.ACCESS_ID).description("서버 Access id"),
                                        headerWithName(GlobalStatus.ACCESS_KEY).description("서버 Access key"),
                                        headerWithName(GlobalStatus.ACCESS_TOKEN).description("유저의 access-token")
                                ),
                                responseFields(
                                        fieldWithPath("isLogoutSuccess").type(JsonFieldType.BOOLEAN).description("로그아웃 성공 여부")
                                )
                        )
                );
    }
}
