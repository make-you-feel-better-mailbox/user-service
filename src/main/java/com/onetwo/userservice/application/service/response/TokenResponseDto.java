package com.onetwo.userservice.application.service.response;

public record TokenResponseDto(String accessToken,
                               String refreshToken) {
}

