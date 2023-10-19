package com.onetwo.userservice.common.config;

import com.onetwo.userservice.application.port.in.role.usecase.CreatePrivilegeUseCase;
import com.onetwo.userservice.application.port.in.role.usecase.CreateRoleUseCase;
import com.onetwo.userservice.application.port.in.role.usecase.MappingRoleUseCase;
import com.onetwo.userservice.domain.role.Privilege;
import com.onetwo.userservice.domain.role.PrivilegeNames;
import com.onetwo.userservice.domain.role.Role;
import com.onetwo.userservice.domain.role.RoleNames;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;
    private final CreateRoleUseCase createRoleUseCase;
    private final CreatePrivilegeUseCase createPrivilegeUseCase;
    private final MappingRoleUseCase mappingRoleUseCase;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;

        Privilege readPrivilege
                = createPrivilegeUseCase.createPrivilegeIfNotFound(PrivilegeNames.READ_PRIVILEGE);
        Privilege writePrivilege
                = createPrivilegeUseCase.createPrivilegeIfNotFound(PrivilegeNames.WRITE_PRIVILEGE);

        List<Privilege> adminPrivilege = Arrays.asList(readPrivilege, writePrivilege);
        List<Privilege> userPrivilege = Arrays.asList(readPrivilege);

        Role adminRole = createRoleUseCase.createRoleIfNotFound(RoleNames.ROLE_ADMIN);
        Role userRole = createRoleUseCase.createRoleIfNotFound(RoleNames.ROLE_USER);

        mappingRoleUseCase.mappingRoleAndPrivilege(adminRole, adminPrivilege);
        mappingRoleUseCase.mappingRoleAndPrivilege(userRole, userPrivilege);

        alreadySetup = true;
    }
}
