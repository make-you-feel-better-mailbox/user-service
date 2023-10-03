package com.onetwo.userservice.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.userservice.common.GlobalStatus;
import com.onetwo.userservice.common.GlobalUrl;
import com.onetwo.userservice.controller.request.RegisterUserRequest;
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

import java.time.Instant;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class UserControllerBootTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${access-id}")
    private String accessId;

    @Value("${access-key}")
    private String accessKey;

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

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(GlobalStatus.ACCESS_ID, accessId);
        httpHeaders.add(GlobalStatus.ACCESS_KEY, accessKey);

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
