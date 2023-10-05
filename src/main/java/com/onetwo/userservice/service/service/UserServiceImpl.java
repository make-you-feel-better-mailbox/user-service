package com.onetwo.userservice.service.service;

import com.onetwo.userservice.common.exceptions.BadRequestException;
import com.onetwo.userservice.common.exceptions.NotFoundResourceException;
import com.onetwo.userservice.common.exceptions.ResourceAlreadyExistsException;
import com.onetwo.userservice.controller.response.TokenResponseDto;
import com.onetwo.userservice.entity.redis.RefreshToken;
import com.onetwo.userservice.entity.user.User;
import com.onetwo.userservice.jwt.JwtTokenProvider;
import com.onetwo.userservice.repository.user.UserRepository;
import com.onetwo.userservice.service.converter.UserConverter;
import com.onetwo.userservice.service.requset.LoginDto;
import com.onetwo.userservice.service.requset.UserDto;
import com.onetwo.userservice.service.response.UserIdExistCheckDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final MyUserDetailsService myUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CacheService cacheService;

    @Override
    @Transactional(readOnly = true)
    public UserIdExistCheckDto userIdExistCheck(String userId) {
        return new UserIdExistCheckDto(userIdExist(userId));
    }

    @Override
    @Transactional
    public UserDto registerUser(UserDto userDto) {
        if (userIdExist(userDto.userId())) throw new ResourceAlreadyExistsException("user-id already exist");

        User newUser = UserConverter.of().userDtoToUser(userDto);
        newUser.setDefaultState();
        newUser.setEncodePassword(passwordEncoder.encode(userDto.password()));

        User savedUser = userRepository.save(newUser);

        roleService.createNewUserRole(savedUser);

        return UserConverter.of().userToUserDto(savedUser);
    }

    private boolean userIdExist(String userDto) {
        return userRepository.findByUserId(userDto).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public TokenResponseDto loginUser(LoginDto loginDto, String requestIp) {
        User user = userRepository.findByUserId(loginDto.id())
                .orElseThrow(() -> new NotFoundResourceException("No Resource user exception"));

        if (!passwordEncoder.matches(loginDto.pw(), user.getPassword()))
            throw new BadRequestException("Password does not match");

        Collection<GrantedAuthority> grantedAuthorities = myUserDetailsService.getGrantedAuthoritiesByUser(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword(), grantedAuthorities);

        // refresh token 발급 및 저장
        String refreshToken = jwtTokenProvider.createRefreshToken(authentication);
        RefreshToken token = RefreshToken.createRefreshTokenEntity(user.getUserId(), requestIp, refreshToken);

        cacheService.saveRefreshToken(token);

        String accessToken = jwtTokenProvider.createAccessToken(authentication);

        return new TokenResponseDto(accessToken);
    }
}
