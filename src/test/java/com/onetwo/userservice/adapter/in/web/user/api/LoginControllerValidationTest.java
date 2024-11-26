package com.onetwo.userservice.adapter.in.web.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.userservice.adapter.in.web.token.mapper.TokenDtoMapper;
import com.onetwo.userservice.adapter.in.web.user.mapper.UserDtoMapper;
import com.onetwo.userservice.adapter.in.web.user.request.LoginUserRequest;
import com.onetwo.userservice.application.port.in.user.usecase.LoginUseCase;
import com.onetwo.userservice.application.port.in.user.usecase.LogoutUseCase;
import com.onetwo.userservice.common.GlobalStatus;
import com.onetwo.userservice.common.GlobalUrl;
import com.onetwo.userservice.common.config.SecurityConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.filter.OncePerRequestFilter;

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
class LoginControllerValidationTest {

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

    private final String userId = "12OneTwo12";
    private final String password = "password";

    private static final HttpHeaders httpHeaders = new HttpHeaders();

    @BeforeAll
    static void settingHeader(@Value("${access-id}") String accessId, @Value("${access-key}") String accessKey) {
        httpHeaders.add(GlobalStatus.ACCESS_ID, accessId);
        httpHeaders.add(GlobalStatus.ACCESS_KEY, accessKey);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Web Adapter] 회원 Login 유효성검사 userId - 실패 테스트")
    void loginUserIdValidationFailTest(String userId) throws Exception {
        //given
        LoginUserRequest loginUserRequest = new LoginUserRequest(userId, password);

        //when
        ResultActions resultActions = mockMvc.perform(
                post(GlobalUrl.USER_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUserRequest))
                        .headers(httpHeaders)
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Web Adapter] 회원 Login 유효성검사 password - 실패 테스트")
    void loginUserPasswordValidationFailTest(String password) throws Exception {
        //given
        LoginUserRequest loginUserRequest = new LoginUserRequest(userId, password);

        //when
        ResultActions resultActions = mockMvc.perform(
                post(GlobalUrl.USER_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUserRequest))
                        .headers(httpHeaders)
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }
}
