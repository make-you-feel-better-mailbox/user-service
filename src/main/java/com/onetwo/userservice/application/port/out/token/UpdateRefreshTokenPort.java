package com.onetwo.userservice.application.port.out.token;

import com.onetwo.userservice.domain.token.RefreshToken;

public interface UpdateRefreshTokenPort {

    void updateRefreshToken(RefreshToken token);
}
