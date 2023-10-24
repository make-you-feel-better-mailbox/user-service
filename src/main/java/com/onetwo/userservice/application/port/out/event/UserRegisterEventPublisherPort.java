package com.onetwo.userservice.application.port.out.event;

import com.onetwo.userservice.domain.user.User;

public interface UserRegisterEventPublisherPort {
    void publishEvent(User savedUser);
}
