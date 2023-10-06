package com.onetwo.userservice.controller.response;

public record TokenResponseDto(String accessToken,
                               String refreshToken) {
}

