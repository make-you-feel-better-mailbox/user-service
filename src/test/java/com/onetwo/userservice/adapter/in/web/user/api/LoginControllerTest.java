package com.onetwo.userservice.adapter.in.web.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.userservice.adapter.in.web.token.mapper.TokenDtoMapper;
import com.onetwo.userservice.adapter.in.web.user.mapper.UserDtoMapper;
import com.onetwo.userservice.adapter.in.web.user.request.LoginUserRequest;
import com.onetwo.userservice.application.port.in.user.command.LoginUserCommand;
import com.onetwo.userservice.application.port.in.user.usecase.LoginUseCase;
import com.onetwo.userservice.application.port.in.user.usecase.LogoutUseCase;
import com.onetwo.userservice.application.port.in.user.response.TokenResponseDto;
import com.onetwo.userservice.common.GlobalUrl;
import com.onetwo.userservice.common.config.SecurityConfig;
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

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LoginController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
                        SecurityConfig.class,
                        OncePerRequestFilter.class
                })
        }
)
@AutoConfigureRestDocs
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LoginUseCase loginUseCase;

    @MockBean
    private UserDtoMapper userDtoMapper;

    @MockBean
    private TokenDtoMapper tokenDtoMapper;

    @MockBean
    private LogoutUseCase logoutUseCase;

    @Test
    @DisplayName("[단위][Web Adapter] 회원 로그인 - 성공 테스트")
    void loginUserSuccessTest() throws Exception {
        //given
        String userId = "12OneTwo12";
        Long uuid = 1L;
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

        String refreshToken = Jwts.builder()
                .setSubject(uuid.toString())
                .setIssuedAt(now)
                .signWith(Keys.hmacShaKeyFor("secret-key-test-secret-key-it-only-for-test-one-two-test-key".getBytes()), SignatureAlgorithm.HS256)
                .setExpiration(validity)
                .compact();

        LoginUserRequest loginUserRequest = new LoginUserRequest(userId, password);
        TokenResponseDto tokenResponse = new TokenResponseDto(token, refreshToken);

        when(loginUseCase.loginUser(any(LoginUserCommand.class))).thenReturn(tokenResponse);
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
