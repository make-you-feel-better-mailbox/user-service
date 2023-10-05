package com.onetwo.userservice.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.userservice.common.GlobalUrl;
import com.onetwo.userservice.config.SecurityConfig;
import com.onetwo.userservice.controller.request.LoginUserRequest;
import com.onetwo.userservice.controller.request.RegisterUserRequest;
import com.onetwo.userservice.controller.response.TokenResponseDto;
import com.onetwo.userservice.service.requset.LoginDto;
import com.onetwo.userservice.service.requset.UserDto;
import com.onetwo.userservice.service.response.UserIdExistCheckDto;
import com.onetwo.userservice.service.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.filter.OncePerRequestFilter;

import java.time.Instant;
import java.util.Date;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
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
    void registerUserSuccessTest() throws Exception {
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
                .andDo(print());
    }

    @Test
    @DisplayName("[단위] 회원 ID 중복 체크 - 성공 테스트")
    void userIdExistCheckSuccessTest() throws Exception {
        //given
        String userId = "newUserId";

        UserIdExistCheckDto userIdExistCheckDto = new UserIdExistCheckDto(false);

        when(userService.userIdExistCheck(anyString())).thenReturn(userIdExistCheckDto);
        //when
        ResultActions resultActions = mockMvc.perform(
                get(GlobalUrl.USER_ID + "/" + userId)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("[단위] 회원 로그인 - 성공 테스트")
    void loginUserSuccessTest() throws Exception {
        //given
        String userId = "newUserId";
        String password = "password";

        long tokenValidityInMs = 1000;

        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidityInMs);

        String token = Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .signWith(Keys.hmacShaKeyFor("secret-key-test-secret-key-it-only-for-test-one-two-test-key".getBytes()), SignatureAlgorithm.HS256)
                .setExpiration(validity)
                .compact();

        LoginUserRequest loginUserRequest = new LoginUserRequest(userId, password);
        TokenResponseDto tokenResponseDto = new TokenResponseDto(token);

        when(userService.loginUser(any(LoginDto.class), anyString())).thenReturn(tokenResponseDto);
        //when
        ResultActions resultActions = mockMvc.perform(
                post(GlobalUrl.USER_LOGIN)
                        .content(objectMapper.writeValueAsString(loginUserRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }
}