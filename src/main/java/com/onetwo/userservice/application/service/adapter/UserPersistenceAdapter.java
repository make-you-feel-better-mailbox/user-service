package com.onetwo.userservice.application.service.adapter;

import com.onetwo.userservice.adapter.out.persistence.entity.user.UserEntity;
import com.onetwo.userservice.adapter.out.persistence.repository.user.UserRepository;
import com.onetwo.userservice.application.port.out.user.CreateUserPort;
import com.onetwo.userservice.application.port.out.user.ReadUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements ReadUserPort, CreateUserPort {

    private final UserRepository userRepository;

    @Override
    public Optional<UserEntity> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public Optional<UserEntity> findById(Long uuid) {
        return userRepository.findById(uuid);
    }

    @Override
    public UserEntity createNewUser(UserEntity newUser) {
        return userRepository.save(newUser);
    }
}
