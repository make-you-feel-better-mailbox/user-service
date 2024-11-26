package com.onetwo.userservice.common.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuthClientInfo {

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String tokenUri;
    private String resourceUri;
    private String beforeUri;
    private String afterUri;
}
