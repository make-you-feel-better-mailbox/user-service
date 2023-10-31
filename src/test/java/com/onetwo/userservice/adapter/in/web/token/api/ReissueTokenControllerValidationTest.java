package com.onetwo.userservice.adapter.in.web.token.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.userservice.adapter.in.web.token.mapper.TokenDtoMapper;
import com.onetwo.userservice.adapter.in.web.token.request.ReissueTokenRequest;
import com.onetwo.userservice.application.port.in.token.command.ReissueTokenCommand;
import com.onetwo.userservice.application.port.in.token.usecase.ReissueAccessTokenUseCase;
import com.onetwo.userservice.application.port.in.token.response.ReissuedTokenResponseDto;
import com.onetwo.userservice.common.GlobalUrl;
import com.onetwo.userservice.common.config.SecurityConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReissueTokenController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
                        SecurityConfig.class,
                        OncePerRequestFilter.class
                })
        }
)
class ReissueTokenControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReissueAccessTokenUseCase reissueAccessTokenUseCase;

    @MockBean
    private TokenDtoMapper tokenDtoMapper;

    private String userId = "12OneTwo12";


    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Web Adapter] Access Token 재발급 accessToken 유효성 검사 - 실패 테스트")
    void reissueAccessTokenAccessTokenValidationFailTest(String accessToken) throws Exception {
        //given
        long tokenValidityInMs = 1000;

        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidityInMs);

        String refreshToken = Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .signWith(Keys.hmacShaKeyFor("secret-key-test-secret-key-it-only-for-test-one-two-test-key".getBytes()), SignatureAlgorithm.HS256)
                .setExpiration(validity)
                .compact();

        ReissueTokenRequest reissueTokenCommand = new ReissueTokenRequest(accessToken, refreshToken);

        ReissuedTokenResponseDto reissuedTokenDto = new ReissuedTokenResponseDto(accessToken);

        when(reissueAccessTokenUseCase.reissueAccessTokenByRefreshToken(any(ReissueTokenCommand.class))).thenReturn(reissuedTokenDto);
        //when
        ResultActions resultActions = mockMvc.perform(
                post(GlobalUrl.TOKEN_REFRESH)
                        .content(objectMapper.writeValueAsString(reissueTokenCommand))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Web Adapter] Access Token 재발급 refreshToken 유효성 검사 - 실패 테스트")
    void reissueAccessTokenRefreshTokenValidationFailTest(String refreshToken) throws Exception {
        //given
        long tokenValidityInMs = 1000;

        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidityInMs);

        String accessToken = Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .signWith(Keys.hmacShaKeyFor("secret-key-test-secret-key-it-only-for-test-one-two-test-key".getBytes()), SignatureAlgorithm.HS256)
                .setExpiration(validity)
                .compact();

        ReissueTokenRequest reissueTokenCommand = new ReissueTokenRequest(accessToken, refreshToken);

        ReissuedTokenResponseDto reissuedTokenDto = new ReissuedTokenResponseDto(accessToken);

        when(reissueAccessTokenUseCase.reissueAccessTokenByRefreshToken(any(ReissueTokenCommand.class))).thenReturn(reissuedTokenDto);
        //when
        ResultActions resultActions = mockMvc.perform(
                post(GlobalUrl.TOKEN_REFRESH)
                        .content(objectMapper.writeValueAsString(reissueTokenCommand))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }
}
