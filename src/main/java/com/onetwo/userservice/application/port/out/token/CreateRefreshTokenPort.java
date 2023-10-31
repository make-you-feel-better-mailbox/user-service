package com.onetwo.userservice.application.port.out.token;

import com.onetwo.userservice.domain.token.RefreshToken;

public interface CreateRefreshTokenPort {
    void saveRefreshToken(RefreshToken token);
}
