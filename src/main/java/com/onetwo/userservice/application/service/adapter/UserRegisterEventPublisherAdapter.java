package com.onetwo.userservice.application.service.adapter;

import com.onetwo.userservice.adapter.out.event.RegisterUserEvent;
import com.onetwo.userservice.application.port.out.event.UserRegisterEventPublisherPort;
import com.onetwo.userservice.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterEventPublisherAdapter implements UserRegisterEventPublisherPort {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Publish event when user registered
     *
     * @param savedUser registered user
     */
    @Override
    public void publishEvent(User savedUser) {
        RegisterUserEvent registerUserEvent = new RegisterUserEvent(savedUser.getUserId());
        eventPublisher.publishEvent(registerUserEvent);
    }
}
