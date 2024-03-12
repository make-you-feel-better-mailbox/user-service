package com.onetwo.userservice.adapter.out.persistence.entity.user;

import com.onetwo.userservice.adapter.out.persistence.entity.BaseEntity;
import com.onetwo.userservice.adapter.out.persistence.repository.converter.BooleanNumberConverter;
import com.onetwo.userservice.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
@NoArgsConstructor
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uuid;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false, length = 300)
    private String password;

    @Column(nullable = false, length = 15)
    private String nickname;

    @Column(length = 123)
    private String email;

    @Column(length = 20)
    private String phoneNumber;

    @Column(nullable = false, length = 1)
    @Convert(converter = BooleanNumberConverter.class)
    private Boolean oauth;

    @Column(length = 20)
    private String registrationId;


    @Column(nullable = false, length = 1)
    @Convert(converter = BooleanNumberConverter.class)
    private Boolean state;

    public UserEntity(Long uuid, String userId, String password, String nickname, String email, String phoneNumber, Boolean oauth, String registrationId, Boolean state) {
        this.uuid = uuid;
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.oauth = oauth;
        this.registrationId = registrationId;
        this.state = state;
    }

    public static UserEntity domainToEntity(User user) {
        UserEntity userEntity = new UserEntity(
                user.getUuid(),
                user.getUserId(),
                user.getPassword(),
                user.getNickname(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.isOauth(),
                user.getRegistrationId(),
                user.isState()
        );

        userEntity.setMetaDataByDomain(user);
        return userEntity;
    }
}
