package com.onetwo.userservice.controller.api;

import com.onetwo.userservice.common.GlobalUrl;
import com.onetwo.userservice.controller.mapper.TokenDtoMapper;
import com.onetwo.userservice.controller.request.ReissueTokenRequest;
import com.onetwo.userservice.service.requset.ReissueTokenDto;
import com.onetwo.userservice.service.response.ReissuedTokenDto;
import com.onetwo.userservice.service.service.UserTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final UserTokenService userTokenService;

    @PostMapping(GlobalUrl.TOKEN_REFRESH)
    public ResponseEntity<ReissuedTokenDto> reissueAccessTokenByRefreshToken(@RequestBody @Valid ReissueTokenRequest reissueTokenRequest) {
        ReissueTokenDto reissueTokenDto = TokenDtoMapper.of().reissueTokenRequestToDto(reissueTokenRequest);
        ReissuedTokenDto reissuedTokenDto = userTokenService.reissueAccessTokenByRefreshToken(reissueTokenDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reissuedTokenDto);
    }
}
