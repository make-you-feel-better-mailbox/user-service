package com.onetwo.userservice.adapter.in.web.token.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.userservice.application.port.in.token.usecase.ReissueAccessTokenUseCase;
import com.onetwo.userservice.application.port.in.token.command.ReissueTokenCommand;
import com.onetwo.userservice.application.service.response.ReissuedTokenDto;
import com.onetwo.userservice.common.GlobalUrl;
import com.onetwo.userservice.common.config.SecurityConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
class ReissueTokenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReissueAccessTokenUseCase reissueAccessTokenUseCase;

    private final String userId = "newUserId";
    private final Long uuid = 1L;


    @Test
    @DisplayName("[단위] Access Token 재발급 - 성공 테스트")
    void reissueAccessTokenByRefreshTokenSuccessTest() throws Exception {
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

        String refreshToken = Jwts.builder()
                .setSubject(uuid.toString())
                .setIssuedAt(now)
                .signWith(Keys.hmacShaKeyFor("secret-key-test-secret-key-it-only-for-test-one-two-test-key".getBytes()), SignatureAlgorithm.HS256)
                .setExpiration(validity)
                .compact();

        ReissueTokenCommand reissueTokenCommand = new ReissueTokenCommand(accessToken, refreshToken);

        ReissuedTokenDto reissuedTokenDto = new ReissuedTokenDto(accessToken);

        when(reissueAccessTokenUseCase.reissueAccessTokenByRefreshToken(reissueTokenCommand)).thenReturn(reissuedTokenDto);
        //when
        ResultActions resultActions = mockMvc.perform(
                post(GlobalUrl.TOKEN_REFRESH)
                        .content(objectMapper.writeValueAsString(reissueTokenCommand))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isCreated())
                .andDo(print());
    }
}
