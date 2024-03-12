package com.onetwo.userservice.domain.user;

import com.onetwo.userservice.adapter.out.persistence.entity.user.UserEntity;
import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.command.UpdateUserCommand;
import com.onetwo.userservice.application.port.out.dto.OAuthResponseDto;
import com.onetwo.userservice.domain.BaseDomain;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends BaseDomain {

    private Long uuid;

    private String userId;

    private String password;

    private String nickname;

    private String email;

    private String phoneNumber;

    private boolean oauth;

    private String registrationId;

    private boolean state;

    public static User createNewUserByCommand(RegisterUserCommand registerUserCommand, String encoredPassword) {
        User newUser = new User(
                null,
                registerUserCommand.getUserId(),
                encoredPassword,
                registerUserCommand.getNickname(),
                registerUserCommand.getEmail(),
                registerUserCommand.getPhoneNumber(),
                registerUserCommand.getOauth(),
                registerUserCommand.getRegistrationId(),
                false
        );

        newUser.setDefaultState();

        return newUser;
    }

    public static User entityToDomain(UserEntity userEntity) {
        User user = new User(
                userEntity.getUuid(),
                userEntity.getUserId(),
                userEntity.getPassword(),
                userEntity.getNickname(),
                userEntity.getEmail(),
                userEntity.getPhoneNumber(),
                userEntity.getOauth(),
                userEntity.getRegistrationId(),
                userEntity.getState()
        );

        user.setMetaDataByEntity(userEntity);

        return user;
    }

    public static User createNewUserByOAuth(String oAuthUserId, OAuthResponseDto oAuthResponseDto, String registrationId) {
        User newUser = new User(
                null,
                oAuthUserId,
                "",
                oAuthResponseDto.name(),
                oAuthResponseDto.email(),
                null,
                true,
                registrationId,
                false
        );

        newUser.setDefaultState();

        return newUser;
    }

    private void setDefaultState() {
        this.state = false;
        setCreatedAt(Instant.now());
        setCreateUser(this.userId);
    }

    public void updateEncodePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public boolean isUserWithdraw() {
        return this.state;
    }

    public void userWithdraw() {
        this.state = true;
        setUpdatedAt(Instant.now());
        setUpdateUser(this.userId);
    }

    public void updateUser(UpdateUserCommand updateUserCommand) {
        this.nickname = updateUserCommand.getNickname();
        this.email = updateUserCommand.getEmail();
        this.phoneNumber = updateUserCommand.getPhoneNumber();
        setUpdatedAt(Instant.now());
        setUpdateUser(updateUserCommand.getUserId());
    }

    public void rejoin() {
        this.state = false;
    }
}
