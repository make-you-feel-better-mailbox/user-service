package com.onetwo.userservice.adapter.in.event;

import com.onetwo.userservice.adapter.out.event.RegisterUserEvent;
import com.onetwo.userservice.application.port.in.role.command.CreateDefaultUserRoleCommand;
import com.onetwo.userservice.application.port.in.role.usecase.CreateUserRoleUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRegisterEventListener {

    private final CreateUserRoleUseCase createUserRoleUseCase;

    /**
     * register user create default user role event adapter
     *
     * @param registerUserEvent register user event
     */
    @EventListener
    public void createDefaultUserRole(RegisterUserEvent registerUserEvent) {
        log.info("User Register Event caught on create default user role, userId={}", registerUserEvent.userId());
        CreateDefaultUserRoleCommand createDefaultUserRoleCommand = new CreateDefaultUserRoleCommand(registerUserEvent.userId());
        createUserRoleUseCase.createDefaultUserRole(createDefaultUserRoleCommand);
    }
}
