package com.onetwo.userservice.adapter.in.web.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.userservice.adapter.in.web.user.request.LoginUserRequest;
import com.onetwo.userservice.adapter.in.web.user.request.RegisterUserRequest;
import com.onetwo.userservice.adapter.in.web.user.request.UpdateUserRequest;
import com.onetwo.userservice.adapter.in.web.user.request.WithdrawUserRequest;
import com.onetwo.userservice.application.port.in.user.command.LoginUserCommand;
import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.usecase.LoginUseCase;
import com.onetwo.userservice.application.port.in.user.usecase.RegisterUserUseCase;
import com.onetwo.userservice.application.port.out.token.CreateRefreshTokenPort;
import com.onetwo.userservice.application.port.out.token.ReadRefreshTokenPort;
import com.onetwo.userservice.application.port.out.user.ReadUserPort;
import com.onetwo.userservice.application.service.response.TokenResponseDto;
import com.onetwo.userservice.common.GlobalStatus;
import com.onetwo.userservice.common.GlobalUrl;
import com.onetwo.userservice.domain.user.User;
import org.junit.jupiter.api.Assertions;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class UserControllerBootTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RegisterUserUseCase registerUserUseCase;

    @Autowired
    private LoginUseCase loginUseCase;

    @Autowired
    private CreateRefreshTokenPort createRefreshTokenPort;

    @Autowired
    private ReadRefreshTokenPort readRefreshTokenPort;

    @Autowired
    private ReadUserPort readUserPort;

    private final String userId = "newUserId";
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
    @DisplayName("[통합] 회원 회원가입 - 성공 테스트")
    void registerUserSuccessTest() throws Exception {
        //given
        RegisterUserRequest registerUserRequest = new RegisterUserRequest(userId, password, birth, nickname, name, email, phoneNumber);

        //when
        ResultActions resultActions = mockMvc.perform(
                post(GlobalUrl.USER_ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUserRequest))
                        .headers(httpHeaders)
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("user-sign-up",
                                requestHeaders(
                                        headerWithName(GlobalStatus.ACCESS_ID).description("서버 Access id"),
                                        headerWithName(GlobalStatus.ACCESS_KEY).description("서버 Access key")
                                ),
                                requestFields(
                                        fieldWithPath("userId").type(JsonFieldType.STRING).description("생성할 유저의 ID"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("생성할 유저의 Password"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("생성할 유저의 Name"),
                                        fieldWithPath("birth").type(JsonFieldType.STRING).description("생성할 유저의 생년월일 (instant type)"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("생성할 유저의 nickname"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("생성할 유저의 email"),
                                        fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("생성할 유저의 휴대폰 번호")
                                ),
                                responseFields(
                                        fieldWithPath("userId").type(JsonFieldType.STRING).description("생성된 유저의 ID")
                                )
                        )
                );
    }

    @Test
    @DisplayName("[통합] 회원 ID 중복 체크 - 성공 테스트")
    void userIdExistCheckSuccessTest() throws Exception {
        //given
        String userId = this.userId;

        //when
        ResultActions resultActions = mockMvc.perform(
                get(GlobalUrl.USER_ID + "/{user-id}", userId)
                        .headers(httpHeaders)
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-id-exist",
                                requestHeaders(
                                        headerWithName(GlobalStatus.ACCESS_ID).description("서버 Access id"),
                                        headerWithName(GlobalStatus.ACCESS_KEY).description("서버 Access key")
                                ),
                                pathParameters(
                                        parameterWithName("user-id").description("존재여부 확인할 유저 id")
                                ),
                                responseFields(
                                        fieldWithPath("userIdExist").type(JsonFieldType.BOOLEAN).description("존재하는지 여부")
                                )
                        )
                );
    }

    @Test
    @Transactional
    @DisplayName("[통합] 회원 상세정보 - 성공 테스트")
    void getUserDetailInfoSuccessTest() throws Exception {
        //given
        LoginUserCommand loginUserRequest = new LoginUserCommand(userId, password);

        registerUserUseCase.registerUser(new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber));

        TokenResponseDto tokenResponse = loginUseCase.loginUser(loginUserRequest);

        HttpHeaders userDetailInfoHttpHeaders = new HttpHeaders();
        userDetailInfoHttpHeaders.add(GlobalStatus.ACCESS_ID, httpHeaders.getFirst(GlobalStatus.ACCESS_ID));
        userDetailInfoHttpHeaders.add(GlobalStatus.ACCESS_KEY, httpHeaders.getFirst(GlobalStatus.ACCESS_KEY));
        userDetailInfoHttpHeaders.add(GlobalStatus.ACCESS_TOKEN, tokenResponse.accessToken());

        //when
        ResultActions resultActions = mockMvc.perform(
                get(GlobalUrl.USER_ROOT)
                        .headers(userDetailInfoHttpHeaders)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-detail",
                                requestHeaders(
                                        headerWithName(GlobalStatus.ACCESS_ID).description("서버 Access id"),
                                        headerWithName(GlobalStatus.ACCESS_KEY).description("서버 Access key"),
                                        headerWithName(GlobalStatus.ACCESS_TOKEN).description("유저의 access-token")
                                ),
                                responseFields(
                                        fieldWithPath("userId").type(JsonFieldType.STRING).description("유저의 ID"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("유저의 Name"),
                                        fieldWithPath("birth").type(JsonFieldType.STRING).description("유저의 생년월일 (instant type)"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("유저의 nickname"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("유저의 email"),
                                        fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("유저의 휴대폰 번호"),
                                        fieldWithPath("state").type(JsonFieldType.BOOLEAN).description("유저의 상태 ( True: 탈퇴, False: 정상 )")
                                )
                        )
                );
    }

    @Test
    @Transactional
    @DisplayName("[통합] 회원 탈퇴 - 성공 테스트")
    void withdrawUserSuccessTest() throws Exception {
        //given
        LoginUserCommand loginUserRequest = new LoginUserCommand(userId, password);

        registerUserUseCase.registerUser(new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber));

        TokenResponseDto tokenResponse = loginUseCase.loginUser(loginUserRequest);

        WithdrawUserRequest withdrawUserRequest = new WithdrawUserRequest(userId, password);

        HttpHeaders userWithdrawRequestHeader = new HttpHeaders();
        userWithdrawRequestHeader.add(GlobalStatus.ACCESS_ID, httpHeaders.getFirst(GlobalStatus.ACCESS_ID));
        userWithdrawRequestHeader.add(GlobalStatus.ACCESS_KEY, httpHeaders.getFirst(GlobalStatus.ACCESS_KEY));
        userWithdrawRequestHeader.add(GlobalStatus.ACCESS_TOKEN, tokenResponse.accessToken());

        //when
        ResultActions resultActions = mockMvc.perform(
                delete(GlobalUrl.USER_ROOT)
                        .headers(userWithdrawRequestHeader)
                        .content(objectMapper.writeValueAsString(withdrawUserRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-withdraw",
                                requestHeaders(
                                        headerWithName(GlobalStatus.ACCESS_ID).description("서버 Access id"),
                                        headerWithName(GlobalStatus.ACCESS_KEY).description("서버 Access key"),
                                        headerWithName(GlobalStatus.ACCESS_TOKEN).description("유저의 access-token")
                                ),
                                requestFields(
                                        fieldWithPath("userId").type(JsonFieldType.STRING).description("유저의 Id"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("유저의 Password")
                                ),
                                responseFields(
                                        fieldWithPath("isWithdrawSuccess").type(JsonFieldType.BOOLEAN).description("탈퇴 성공 여부")
                                )
                        )
                );
    }

    @Test
    @Transactional
    @DisplayName("[통합] 회원 수정 - 성공 테스트")
    void updateUserSuccessTest() throws Exception {
        //given
        LoginUserCommand loginUserRequest = new LoginUserCommand(userId, password);

        registerUserUseCase.registerUser(new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber));

        TokenResponseDto tokenResponse = loginUseCase.loginUser(loginUserRequest);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest(Instant.now(), "updateNickname", "updateName", "updateEmail@onetwo.com", "");

        HttpHeaders updateUserRequestHeader = new HttpHeaders();
        updateUserRequestHeader.add(GlobalStatus.ACCESS_ID, httpHeaders.getFirst(GlobalStatus.ACCESS_ID));
        updateUserRequestHeader.add(GlobalStatus.ACCESS_KEY, httpHeaders.getFirst(GlobalStatus.ACCESS_KEY));
        updateUserRequestHeader.add(GlobalStatus.ACCESS_TOKEN, tokenResponse.accessToken());

        //when
        ResultActions resultActions = mockMvc.perform(
                put(GlobalUrl.USER_ROOT)
                        .headers(updateUserRequestHeader)
                        .content(objectMapper.writeValueAsString(updateUserRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-update",
                                requestHeaders(
                                        headerWithName(GlobalStatus.ACCESS_ID).description("서버 Access id"),
                                        headerWithName(GlobalStatus.ACCESS_KEY).description("서버 Access key"),
                                        headerWithName(GlobalStatus.ACCESS_TOKEN).description("유저의 access-token")
                                ),
                                requestFields(
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("유저의 변경할 Name"),
                                        fieldWithPath("birth").type(JsonFieldType.STRING).description("유저의 변경할 생년월일 (instant type)"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("유저의 변경할 nickname"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("유저의 변경할 email"),
                                        fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("유저의 변경할 휴대폰 번호")
                                ),
                                responseFields(
                                        fieldWithPath("userId").type(JsonFieldType.STRING).description("변경한 유저의 Id"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("변경된 유저의 Name"),
                                        fieldWithPath("birth").type(JsonFieldType.STRING).description("변경된 유저의 생년월일 (instant type)"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("변경된 유저의 nickname"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("변경된 유저의 email"),
                                        fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("변경된 유저의 휴대폰 번호"),
                                        fieldWithPath("state").type(JsonFieldType.BOOLEAN).description("유저의 상태 ( True: 탈퇴, False: 정상 )")
                                )
                        )
                );
    }

    @Test
    @Transactional
    @DisplayName("[통합] 회원 로그아웃 - 성공 테스트")
    void logoutUserSuccessTest() throws Exception {
        //given
        LoginUserCommand loginUserRequest = new LoginUserCommand(userId, password);

        registerUserUseCase.registerUser(new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber));

        TokenResponseDto tokenResponse = loginUseCase.loginUser(loginUserRequest);

        HttpHeaders userWithdrawRequestHeader = new HttpHeaders();
        userWithdrawRequestHeader.add(GlobalStatus.ACCESS_ID, httpHeaders.getFirst(GlobalStatus.ACCESS_ID));
        userWithdrawRequestHeader.add(GlobalStatus.ACCESS_KEY, httpHeaders.getFirst(GlobalStatus.ACCESS_KEY));
        userWithdrawRequestHeader.add(GlobalStatus.ACCESS_TOKEN, tokenResponse.accessToken());

        //when
        ResultActions resultActions = mockMvc.perform(
                delete(GlobalUrl.USER_LOGIN)
                        .headers(userWithdrawRequestHeader)
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

    @Test
    @Transactional
    @DisplayName("[통합] 회원 로그인 Refresh Token Saved - 성공 테스트")
    void loginUserRefreshTokenSavedSuccessTest() throws Exception {
        //given
        LoginUserRequest loginUserRequest = new LoginUserRequest(userId, password);

        registerUserUseCase.registerUser(new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber));

        User user = readUserPort.findByUserId(userId).get();

        //when
        ResultActions resultActions = mockMvc.perform(
                post(GlobalUrl.USER_LOGIN)
                        .headers(httpHeaders)
                        .content(objectMapper.writeValueAsString(loginUserRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(result -> Assertions.assertTrue(readRefreshTokenPort.findRefreshTokenById(user.getUuid()).isPresent()))
                .andDo(print());
    }
}
