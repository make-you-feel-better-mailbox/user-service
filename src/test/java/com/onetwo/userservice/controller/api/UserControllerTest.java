package com.onetwo.userservice.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.userservice.common.GlobalUrl;
import com.onetwo.userservice.config.FilterConfigure;
import com.onetwo.userservice.config.LoggingFilter;
import com.onetwo.userservice.controller.request.RegisterUserRequest;
import com.onetwo.userservice.entity.user.User;
import com.onetwo.userservice.jwt.JwtTokenFilter;
import com.onetwo.userservice.jwt.TokenProvider;
import com.onetwo.userservice.repository.user.UserRepository;
import com.onetwo.userservice.service.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = UserController.class)
@AutoConfigureRestDocs
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private JwtTokenFilter jwtTokenFilter;

    @MockBean
    private FilterConfigure filterConfigure;

    @MockBean
    private LoggingFilter loggingFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("회원 회원가입 - 성공 테스트")
    void registerUser() throws Exception {
        //given
        String userId = "newUserId";
        String password = "password";
        Instant birth = Instant.now();
        String nickname = "newNickname";
        String name = "tester";
        String email = "onetwo12@onetwo.com";
        String phoneNumber = "01098006069";

        RegisterUserRequest registerUserRequest = new RegisterUserRequest(userId, password, birth, nickname, name, email, phoneNumber);

        Long savedUserId = 1L;
        boolean savedUserState = false;

        User savedUser = new User(savedUserId, userId, passwordEncoder.encode(password), birth, nickname, name, email, phoneNumber, savedUserState);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        //when
        ResultActions resultActions = mockMvc.perform(
                post(GlobalUrl.USER_ROOT)
                        .content(objectMapper.writeValueAsString(registerUserRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("user",
                                requestFields(
                                        fieldWithPath("userId").type(JsonFieldType.STRING).description("생성할 유저의 ID"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("생성할 유저의 Password"),
                                        fieldWithPath("birth").type(JsonFieldType.NUMBER).description("생성할 유저의 생년월일 (instant type)"),
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
}