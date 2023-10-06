package com.onetwo.userservice.service.service;

import com.onetwo.userservice.common.exceptions.NotFoundResourceException;
import com.onetwo.userservice.common.exceptions.TokenValidationException;
import com.onetwo.userservice.entity.redis.RefreshToken;
import com.onetwo.userservice.entity.user.User;
import com.onetwo.userservice.jwt.JwtCode;
import com.onetwo.userservice.jwt.TokenProvider;
import com.onetwo.userservice.repository.user.UserRepository;
import com.onetwo.userservice.service.requset.ReissueTokenDto;
import com.onetwo.userservice.service.response.ReissuedTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserTokenServiceImpl implements UserTokenService {

    private final CacheService cacheService;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;

    @Override
    public void saveRefreshToken(RefreshToken token) {
        cacheService.saveRefreshToken(token);
    }

    @Override
    public ReissuedTokenDto reissueAccessTokenByRefreshToken(ReissueTokenDto reissueTokenDto) {
        Optional<RefreshToken> optionalRefreshToken = cacheService.findRefreshTokenByAccessToken(reissueTokenDto.accessToken());

        if (optionalRefreshToken.isEmpty()) throw new TokenValidationException(JwtCode.REFRESH_TOKEN_EXPIRED);

        RefreshToken refreshToken = optionalRefreshToken.get();

        tokenProvider.refreshTokenValidation(refreshToken.getRefreshToken());

        User user = userRepository.findById(refreshToken.getUuid()).orElseThrow(() -> new NotFoundResourceException("User does not Exist"));

        String reissuedAccessToken = tokenProvider.createAccessToken(user.getUserId());

        return new ReissuedTokenDto(reissuedAccessToken);
    }
}
