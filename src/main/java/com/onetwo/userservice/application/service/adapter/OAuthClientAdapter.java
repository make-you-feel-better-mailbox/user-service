package com.onetwo.userservice.application.service.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.userservice.application.port.out.dto.OAuthResponseDto;
import com.onetwo.userservice.application.port.out.dto.OAuthTokenResponseDto;
import com.onetwo.userservice.application.port.out.user.ReadOAuthPort;
import com.onetwo.userservice.common.exceptions.BadRequestException;
import com.onetwo.userservice.common.exceptions.BadResponseException;
import com.onetwo.userservice.common.properties.OAuthClientInfo;
import com.onetwo.userservice.common.properties.OAuthInfo;
import com.onetwo.userservice.common.properties.PropertiesInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;

@Service
@RequiredArgsConstructor
public class OAuthClientAdapter implements ReadOAuthPort {

    private final PropertiesInfo propertiesInfo;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public OAuthTokenResponseDto getToken(String authorizationCode, String registrationId) {
        OAuthClientInfo oAuthClientInfo = getOAuthClientInfo(registrationId);

        String clientId = oAuthClientInfo.getClientId();
        String clientSecret = oAuthClientInfo.getClientSecret();
        String redirectUri = oAuthClientInfo.getRedirectUri();
        String tokenUri = oAuthClientInfo.getTokenUri();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class);
        JsonNode jsonNode = responseNode.getBody();

        OAuthTokenResponseDto response;

        try {
            response = objectMapper.treeToValue(jsonNode, OAuthTokenResponseDto.class);
        } catch (JsonProcessingException e) {
            throw new BadResponseException(e.getMessage());
        }

        return response;
    }

    @Override
    public OAuthResponseDto getUserResource(String accessToken, String registrationId) {
        OAuthClientInfo oAuthClientInfo = getOAuthClientInfo(registrationId);

        String resourceUri = oAuthClientInfo.getResourceUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);

        JsonNode jsonNode = restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();

        OAuthResponseDto response;

        try {
            response = objectMapper.treeToValue(jsonNode, OAuthResponseDto.class);
        } catch (JsonProcessingException e) {
            throw new BadResponseException(e.getMessage());
        }

        return response;
    }

    @Override
    public OAuthClientInfo getOAuthClientInfo(String registrationId) {
        OAuthClientInfo oAuthClientInfo = null;

        try {
            OAuthInfo oAuthInfo = propertiesInfo.getOauth2();
            Field oauthPropertiesField = OAuthInfo.class.getDeclaredField(registrationId);

            oauthPropertiesField.setAccessible(true);

            oAuthClientInfo = (OAuthClientInfo) oauthPropertiesField.get(oAuthInfo);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new BadRequestException("social login registration id does not found on properties");
        }
        return oAuthClientInfo;
    }

    @Override
    public String getAuthorizedURI(String registrationId) {
        OAuthClientInfo oAuthClientInfo = getOAuthClientInfo(registrationId);

        String authorizedURI = oAuthClientInfo.getAfterUri()
                + "client_id=" + oAuthClientInfo.getClientId()
                + "&redirect_uri" + oAuthClientInfo.getRedirectUri()
                + "&" + oAuthClientInfo.getAfterUri();

        return authorizedURI;
    }
}
