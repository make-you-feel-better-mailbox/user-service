package com.onetwo.userservice.application.port.out.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record OAuthTokenResponseDto(String accessToken,
                                    String refreshToken) {
}
