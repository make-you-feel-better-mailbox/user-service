package com.onetwo.userservice.common.exceptions;

import com.onetwo.userservice.common.jwt.JwtCode;
import lombok.Getter;

@Getter
public class TokenValidationException extends RuntimeException {

    private JwtCode jwtCode;

    public TokenValidationException(JwtCode code) {
        super(code.getValue());
        jwtCode = code;
    }
}
