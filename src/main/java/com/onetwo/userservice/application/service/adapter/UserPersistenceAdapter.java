package com.onetwo.userservice.application.service.adapter;

import com.onetwo.userservice.adapter.out.persistence.entity.user.UserEntity;
import com.onetwo.userservice.adapter.out.persistence.repository.user.UserRepository;
import com.onetwo.userservice.application.port.out.user.ReadUserPort;
import com.onetwo.userservice.application.port.out.user.RegisterUserPort;
import com.onetwo.userservice.application.port.out.user.UpdateUserPort;
import com.onetwo.userservice.common.GlobalStatus;
import com.onetwo.userservice.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements ReadUserPort, RegisterUserPort, UpdateUserPort {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findByUserId(String userId) {
        Optional<UserEntity> userEntity = userRepository.findByUserId(userId);

        return checkOptionalUserEntityAndConvertToOptionalDomain(userEntity);
    }

    @Override
    public Optional<User> findById(Long uuid) {
        Optional<UserEntity> userEntity = userRepository.findById(uuid);

        return checkOptionalUserEntityAndConvertToOptionalDomain(userEntity);
    }

    @Override
    public User registerNewUser(User requestRegisterUser) {
        UserEntity newUser = UserEntity.domainToEntity(requestRegisterUser);

        UserEntity savedUserEntity = userRepository.save(newUser);

        return User.entityToDomain(savedUserEntity);
    }

    private Optional<User> checkOptionalUserEntityAndConvertToOptionalDomain(Optional<UserEntity> userEntity) {
        if (userEntity.isPresent()) {
            User user = User.entityToDomain(userEntity.get());

            return Optional.of(user);
        }

        return Optional.empty();
    }

    @Override
    public void updateUser(User user) {
        UserEntity userEntity = UserEntity.domainToEntity(user);

        userRepository.save(userEntity);
    }

    @Override
    public Optional<User> findByUserIdAndOAuth(String oAuthUserId) {
        Optional<UserEntity> userEntity = userRepository.findByUserIdAndOauthAndState(
                oAuthUserId,
                GlobalStatus.PERSISTENCE_USER_IS_OAUTH,
                GlobalStatus.PERSISTENCE_NOT_DELETED);

        return checkOptionalUserEntityAndConvertToOptionalDomain(userEntity);
    }
}
