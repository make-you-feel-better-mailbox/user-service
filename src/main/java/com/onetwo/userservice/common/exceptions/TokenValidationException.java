package com.onetwo.userservice.common.exceptions;

import com.onetwo.userservice.common.jwt.JwtCode;
import lombok.Getter;

@Getter
public class TokenValidationException extends RuntimeException {

    public TokenValidationException(JwtCode code) {
        super(code.getValue());
    }
}
