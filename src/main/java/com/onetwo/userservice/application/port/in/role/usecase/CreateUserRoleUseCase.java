package com.onetwo.userservice.application.port.in.role.usecase;

import com.onetwo.userservice.application.port.in.role.command.CreateDefaultUserRoleCommand;

public interface CreateUserRoleUseCase {

    /**
     * Create default user role
     *
     * @param createDefaultUserRoleCommand uuid
     */
    void createDefaultUserRole(CreateDefaultUserRoleCommand createDefaultUserRoleCommand);
}
