package com.onetwo.userservice.adapter.in.web.user.api;

import com.onetwo.userservice.adapter.in.web.token.mapper.TokenDtoMapper;
import com.onetwo.userservice.adapter.in.web.user.mapper.OAuthDtoMapper;
import com.onetwo.userservice.adapter.in.web.user.request.OAuthLoginRequest;
import com.onetwo.userservice.adapter.in.web.user.response.AuthorizedURIResponse;
import com.onetwo.userservice.adapter.in.web.user.response.TokenResponse;
import com.onetwo.userservice.application.port.in.user.command.OAuthLoginCommand;
import com.onetwo.userservice.application.port.in.user.response.AuthorizedURIResponseDto;
import com.onetwo.userservice.application.port.in.user.response.TokenResponseDto;
import com.onetwo.userservice.application.port.in.user.usecase.LoginOAuthUseCase;
import com.onetwo.userservice.common.GlobalUrl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final LoginOAuthUseCase loginOAuthUseCase;
    private final OAuthDtoMapper oAuthDtoMapper;
    private final TokenDtoMapper tokenDtoMapper;

    /**
     * Login oAuth user inbound adapter
     *
     * @param oAuthLoginRequest user information about oAuth user
     * @return Access token and Refresh token
     */
    @PostMapping(GlobalUrl.OAUTH_ROOT)
    public ResponseEntity<TokenResponse> loginOAuth(@RequestBody @Valid OAuthLoginRequest oAuthLoginRequest) {
        OAuthLoginCommand oAuthLoginCommand = oAuthDtoMapper.oAuthLoginRequestToCommand(oAuthLoginRequest);
        TokenResponseDto tokenResponseDto = loginOAuthUseCase.loginOAuth(oAuthLoginCommand);
        return ResponseEntity.ok().body(tokenDtoMapper.tokenDtoToTokenResponse(tokenResponseDto));
    }

    /**
     * Get OAuth Authorized Request Uri
     *
     * @param registrationId registration id
     * @return Authorized Request URI
     */
    @GetMapping(GlobalUrl.OAUTH_ROOT + "/{registrationId}")
    public ResponseEntity<AuthorizedURIResponse> getAuthorizedURI(@PathVariable String registrationId) {
        AuthorizedURIResponseDto authorizedURIResponse = loginOAuthUseCase.getAuthorizedURI(registrationId);
        return ResponseEntity.ok().body(oAuthDtoMapper.dtoToAuthorizedURIResponse(authorizedURIResponse));
    }
}
