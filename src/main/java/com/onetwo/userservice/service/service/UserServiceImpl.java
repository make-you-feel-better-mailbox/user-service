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
import com.onetwo.userservice.service.requset.UserRegisterDto;
import com.onetwo.userservice.service.response.UserIdExistCheckDto;
import com.onetwo.userservice.service.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final MyUserDetailsService myUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserTokenService userTokenService;

    @Override
    @Transactional(readOnly = true)
    public UserIdExistCheckDto userIdExistCheck(String userId) {
        return new UserIdExistCheckDto(userIdExist(userId));
    }

    @Override
    public UserResponseDto getUserDetailInfo(String token) {
        return null;
    }

    @Override
    @Transactional
    public UserResponseDto registerUser(UserRegisterDto userRegisterDto) {
        if (userIdExist(userRegisterDto.userId())) throw new ResourceAlreadyExistsException("user-id already exist");

        User newUser = UserConverter.of().userRequestDtoToUser(userRegisterDto);
        newUser.setDefaultState();
        newUser.setEncodePassword(passwordEncoder.encode(userRegisterDto.password()));

        User savedUser = userRepository.save(newUser);

        roleService.createNewUserRole(savedUser);

        return UserConverter.of().userToUserResponseDto(savedUser);
    }

    private boolean userIdExist(String userDto) {
        return userRepository.findByUserId(userDto).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public TokenResponseDto loginUser(LoginDto loginDto) {
        User user = userRepository.findByUserId(loginDto.id())
                .orElseThrow(() -> new NotFoundResourceException("No Resource user exception"));

        if (!passwordEncoder.matches(loginDto.pw(), user.getPassword()))
            throw new BadRequestException("Password does not match");

        String accessToken = jwtTokenProvider.createAccessToken(user.getUserId());

        // refresh token 발급 및 저장
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUuid());
        RefreshToken token = RefreshToken.createRefreshTokenEntity(user.getUuid(), accessToken, refreshToken);

        userTokenService.saveRefreshToken(token);

        return new TokenResponseDto(accessToken, refreshToken);
    }
}
