package com.onetwo.userservice.application.port.in.user.response;

public record TokenResponseDto(String accessToken,
                               String refreshToken) {
}

