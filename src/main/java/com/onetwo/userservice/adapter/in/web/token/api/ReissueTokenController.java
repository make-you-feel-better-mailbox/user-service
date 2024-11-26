package com.onetwo.userservice.adapter.in.web.token.api;

import com.onetwo.userservice.adapter.in.web.token.mapper.TokenDtoMapper;
import com.onetwo.userservice.adapter.in.web.token.request.ReissueTokenRequest;
import com.onetwo.userservice.application.port.in.token.command.ReissueTokenCommand;
import com.onetwo.userservice.application.port.in.token.usecase.ReissueAccessTokenUseCase;
import com.onetwo.userservice.adapter.in.web.token.response.ReissuedTokenDto;
import com.onetwo.userservice.application.port.in.token.response.ReissuedTokenResponseDto;
import com.onetwo.userservice.common.GlobalUrl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReissueTokenController {

    private final ReissueAccessTokenUseCase reissueAccessTokenUseCase;
    private final TokenDtoMapper tokenDtoMapper;

    /**
     * Reissue Access token inbound adapter
     *
     * @param reissueTokenRequest Access token and Refresh token
     * @return Reissued Access token
     */
    @PostMapping(GlobalUrl.TOKEN_REFRESH)
    public ResponseEntity<ReissuedTokenDto> reissueAccessTokenByRefreshToken(@RequestBody @Valid ReissueTokenRequest reissueTokenRequest) {
        ReissueTokenCommand reissueTokenCommand = tokenDtoMapper.reissueTokenRequestToCommand(reissueTokenRequest);
        ReissuedTokenResponseDto reissuedTokenDto = reissueAccessTokenUseCase.reissueAccessTokenByRefreshToken(reissueTokenCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body(tokenDtoMapper.dtoToReissuedTokenDto(reissuedTokenDto));
    }
}
