package com.onetwo.userservice.application.port.out.token;

import com.onetwo.userservice.adapter.out.persistence.entity.token.RefreshTokenEntity;

public interface UpdateRefreshTokenPort {

    void updateRefreshToken(RefreshTokenEntity token);
}
