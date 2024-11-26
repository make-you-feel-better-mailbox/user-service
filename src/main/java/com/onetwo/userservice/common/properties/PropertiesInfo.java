package com.onetwo.userservice.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties
@Getter
@Setter
@Component
public class PropertiesInfo {

    private OAuthInfo oauth2 = new OAuthInfo();
}
