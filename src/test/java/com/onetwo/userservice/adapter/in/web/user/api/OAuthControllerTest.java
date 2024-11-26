package com.onetwo.userservice.adapter.in.web.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.userservice.adapter.in.web.token.mapper.TokenDtoMapper;
import com.onetwo.userservice.adapter.in.web.user.mapper.OAuthDtoMapper;
import com.onetwo.userservice.adapter.in.web.user.request.OAuthLoginRequest;
import com.onetwo.userservice.adapter.in.web.user.response.AuthorizedURIResponse;
import com.onetwo.userservice.adapter.in.web.user.response.TokenResponse;
import com.onetwo.userservice.application.port.in.user.command.OAuthLoginCommand;
import com.onetwo.userservice.application.port.in.user.response.AuthorizedURIResponseDto;
import com.onetwo.userservice.application.port.in.user.response.TokenResponseDto;
import com.onetwo.userservice.application.port.in.user.usecase.LoginOAuthUseCase;
import com.onetwo.userservice.common.GlobalStatus;
import com.onetwo.userservice.common.GlobalUrl;
import com.onetwo.userservice.common.config.SecurityConfig;
import com.onetwo.userservice.common.config.filter.AccessKeyCheckFilter;
import com.onetwo.userservice.config.TestHeader;
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
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OAuthController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
                        SecurityConfig.class,
                        OncePerRequestFilter.class
                })
        }
)
@AutoConfigureRestDocs
@Import({TestHeader.class, AccessKeyCheckFilter.class})
class OAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LoginOAuthUseCase loginOAuthUseCase;

    @MockBean
    private OAuthDtoMapper oAuthDtoMapper;

    @MockBean
    private TokenDtoMapper tokenDtoMapper;

    @Autowired
    private TestHeader testHeader;

    @Test
    @DisplayName("[단위][Web Adapter] OAuth 회원 로그인 - 성공 테스트")
    void oAuthLoginUserSuccessTest() throws Exception {
        //given
        String code = "some.oauth.code";
        String registrationId = "google";

        String userId = code + "_" + registrationId;
        Long uuid = 1L;

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

        OAuthLoginRequest oAuthLoginRequest = new OAuthLoginRequest(code, registrationId);
        OAuthLoginCommand oAuthLoginCommand = new OAuthLoginCommand(oAuthLoginRequest.code(), oAuthLoginRequest.registrationId());
        TokenResponseDto tokenResponseDto = new TokenResponseDto(token, refreshToken);
        TokenResponse tokenResponse = new TokenResponse(tokenResponseDto.accessToken(), tokenResponseDto.refreshToken());

        when(oAuthDtoMapper.oAuthLoginRequestToCommand(any(OAuthLoginRequest.class))).thenReturn(oAuthLoginCommand);
        when(loginOAuthUseCase.loginOAuth(any(OAuthLoginCommand.class))).thenReturn(tokenResponseDto);
        when(tokenDtoMapper.tokenDtoToTokenResponse(any(TokenResponseDto.class))).thenReturn(tokenResponse);
        //when
        ResultActions resultActions = mockMvc.perform(
                post(GlobalUrl.OAUTH_ROOT)
                        .content(objectMapper.writeValueAsString(oAuthLoginRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(testHeader.getRequestHeader())
                        .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("oauth-user-login",
                                requestHeaders(
                                        headerWithName(GlobalStatus.ACCESS_ID).description("서버 Access id"),
                                        headerWithName(GlobalStatus.ACCESS_KEY).description("서버 Access key")
                                ),
                                requestFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("로그인할 유저 OAuth code"),
                                        fieldWithPath("registrationId").type(JsonFieldType.STRING).description("로그인할 유저의 registration Id ( OAuth resource registration )")
                                ),
                                responseFields(
                                        fieldWithPath("accessToken").type(JsonFieldType.STRING).description("유저의 access-token"),
                                        fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("유저의 refresh-token")
                                )
                        )
                );
    }

    @Test
    @DisplayName("[단위][Web Adapter] OAuth 회원 로그인 Authorized URI 조회 - 성공 테스트")
    void getAuthorizedURISuccessTest() throws Exception {
        //given
        String registrationId = "google";
        String authorizedURI = "http://authorized-uri/someuri";

        AuthorizedURIResponseDto authorizedURIResponseDto = new AuthorizedURIResponseDto(authorizedURI);
        AuthorizedURIResponse authorizedURIResponse = new AuthorizedURIResponse(authorizedURIResponseDto.authorizedURI());

        when(loginOAuthUseCase.getAuthorizedURI(anyString())).thenReturn(authorizedURIResponseDto);
        when(oAuthDtoMapper.dtoToAuthorizedURIResponse(any(AuthorizedURIResponseDto.class))).thenReturn(authorizedURIResponse);
        //when
        ResultActions resultActions = mockMvc.perform(
                get(GlobalUrl.OAUTH_ROOT + "/{registrationId}", registrationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(testHeader.getRequestHeader())
                        .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-authorized-uri",
                                requestHeaders(
                                        headerWithName(GlobalStatus.ACCESS_ID).description("서버 Access id"),
                                        headerWithName(GlobalStatus.ACCESS_KEY).description("서버 Access key")
                                ),
                                pathParameters(
                                        parameterWithName("registrationId").description("uri를 조회할 registration Id ( OAuth resource registration )")
                                ),
                                responseFields(
                                        fieldWithPath("authorizedURI").type(JsonFieldType.STRING).description("authorized-uri")
                                )
                        )
                );
    }
}