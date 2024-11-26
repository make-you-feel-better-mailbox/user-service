package com.onetwo.userservice.common.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuthInfo {

    private OAuthGoogleInfo google = new OAuthGoogleInfo();
}
