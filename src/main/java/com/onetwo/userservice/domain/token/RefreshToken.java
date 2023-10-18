package com.onetwo.userservice.domain.token;

import lombok.Getter;

@Getter
public class RefreshToken {

    private Long uuid;
    private String accessToken;
    private String refreshToken;

    private RefreshToken(Long uuid, String accessToken, String refreshToken) {
        this.uuid = uuid;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static RefreshToken createRefreshToken(Long uuid, String accessToken, String refreshToken) {
        return new RefreshToken(uuid, accessToken, refreshToken);
    }

    public void setReissueAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
