package com.onetwo.userservice.adapter.in.web.user.mapper;

import com.onetwo.userservice.adapter.in.web.user.request.OAuthLoginRequest;
import com.onetwo.userservice.adapter.in.web.user.response.AuthorizedURIResponse;
import com.onetwo.userservice.application.port.in.user.command.OAuthLoginCommand;
import com.onetwo.userservice.application.port.in.user.response.AuthorizedURIResponseDto;
import org.springframework.stereotype.Component;

@Component
public class OAuthDtoMapper {
    public OAuthLoginCommand oAuthLoginRequestToCommand(OAuthLoginRequest oAuthLoginRequest) {
        return new OAuthLoginCommand(oAuthLoginRequest.code(), oAuthLoginRequest.registrationId());
    }

    public AuthorizedURIResponse dtoToAuthorizedURIResponse(AuthorizedURIResponseDto authorizedURIResponse) {
        return new AuthorizedURIResponse(authorizedURIResponse.authorizedURI());
    }
}
