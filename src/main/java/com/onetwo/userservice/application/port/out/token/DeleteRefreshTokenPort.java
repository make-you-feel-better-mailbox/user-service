package com.onetwo.userservice.application.port.out.token;

import com.onetwo.userservice.domain.token.RefreshToken;

public interface DeleteRefreshTokenPort {
    void deleteRefreshToken(RefreshToken token);
}
