package com.onetwo.userservice.domain.user;

import com.onetwo.userservice.adapter.out.persistence.entity.user.UserEntity;
import com.onetwo.userservice.application.port.in.user.command.RegisterUserCommand;
import com.onetwo.userservice.application.port.in.user.command.UpdateUserCommand;
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

    private Instant birth;

    private String nickname;

    private String name;

    private String email;

    private String phoneNumber;

    private boolean state;

    public static User createNewUserByCommand(RegisterUserCommand registerUserCommand, String encoredPassword) {
        User newUser = new User(
                null,
                registerUserCommand.getUserId(),
                encoredPassword,
                registerUserCommand.getBirth(),
                registerUserCommand.getNickname(),
                registerUserCommand.getName(),
                registerUserCommand.getEmail(),
                registerUserCommand.getPhoneNumber(),
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
                userEntity.getBirth(),
                userEntity.getNickname(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getPhoneNumber(),
                userEntity.getState()
        );

        user.setMetaDataByEntity(userEntity);

        return user;
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
    }

    public void updateUser(UpdateUserCommand updateUserCommand) {
        this.birth = updateUserCommand.getBirth();
        this.nickname = updateUserCommand.getNickname();
        this.name = updateUserCommand.getName();
        this.email = updateUserCommand.getEmail();
        this.phoneNumber = updateUserCommand.getPhoneNumber();
        setUpdatedAt(Instant.now());
        setUpdateUser(updateUserCommand.getUserId());
    }
}
