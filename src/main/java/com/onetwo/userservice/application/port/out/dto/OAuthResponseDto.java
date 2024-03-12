package com.onetwo.userservice.application.port.out.dto;

import java.math.BigInteger;

public record OAuthResponseDto(BigInteger id,
                               String email,
                               String name) {
}
