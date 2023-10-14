package com.onetwo.userservice.application.port.out.user;

import com.onetwo.userservice.adapter.out.persistence.entity.user.User;
import com.onetwo.userservice.adapter.out.persistence.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements ReadUserPort, CreateUserPort {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public User createNewUser(User newUser) {
        return userRepository.save(newUser);
    }
}
