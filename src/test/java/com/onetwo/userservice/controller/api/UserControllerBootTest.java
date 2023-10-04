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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
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
    @DisplayName("[통합] 회원 회원가입 - 성공 테스트")
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
                .andDo(print());
    }

    @Test
    @DisplayName("[통합] ID 중복 체크 - 성공 테스트")
    void userIdExistCheck() throws Exception {
        //given
        String userId = "newUserId";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(GlobalStatus.ACCESS_ID, accessId);
        httpHeaders.add(GlobalStatus.ACCESS_KEY, accessKey);

        //when
        ResultActions resultActions = mockMvc.perform(
                get(GlobalUrl.USER_ROOT + "?user-id=" + userId)
                        .headers(httpHeaders)
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }
}
