package com.onetwo.userservice.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.userservice.common.GlobalUrl;
import com.onetwo.userservice.config.SecurityConfig;
import com.onetwo.userservice.controller.request.RegisterUserRequest;
import com.onetwo.userservice.service.requset.UserDto;
import com.onetwo.userservice.service.response.UserIdExistCheckDto;
import com.onetwo.userservice.service.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.filter.OncePerRequestFilter;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
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
@AutoConfigureRestDocs
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("[단위] 회원 회원가입 - 성공 테스트")
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

        UserDto savedUser = new UserDto(userId, passwordEncoder.encode(password), birth, nickname, name, email, phoneNumber);

        when(userService.registerUser(any(UserDto.class))).thenReturn(savedUser);
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
    @DisplayName("[단위] ID 중복 체크 - 성공 테스트")
    void userIdExistCheck() throws Exception {
        //given
        String userId = "newUserId";

        UserIdExistCheckDto userIdExistCheckDto = new UserIdExistCheckDto(false);

        when(userService.userIdExistCheck(anyString())).thenReturn(userIdExistCheckDto);
        //when
        ResultActions resultActions = mockMvc.perform(
                get(GlobalUrl.USER_ROOT + "?user-id=" + userId)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user",
                                queryParameters(
                                        parameterWithName("user-id").description("존재여부 확인할 유저 id")
                                ),
                                responseFields(
                                        fieldWithPath("userIdExist").type(JsonFieldType.BOOLEAN).description("존재하는지 여부")
                                )
                        )
                );
    }
}