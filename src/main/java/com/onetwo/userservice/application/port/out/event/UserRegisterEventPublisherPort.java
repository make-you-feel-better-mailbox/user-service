package com.onetwo.userservice.application.port.out.event;

import com.onetwo.userservice.domain.user.User;

public interface UserRegisterEventPublisherPort {

    /**
     * Publish event when user registered
     *
     * @param savedUser registered user
     */
    void publishEvent(User savedUser);
}
