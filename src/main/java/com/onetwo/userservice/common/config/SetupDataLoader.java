package com.onetwo.userservice.common.config;

import com.onetwo.userservice.adapter.out.persistence.entity.role.PrivilegeEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.RoleEntity;
import com.onetwo.userservice.application.port.in.role.usecase.CreatePrivilegeUseCase;
import com.onetwo.userservice.application.port.in.role.usecase.CreateRoleUseCase;
import com.onetwo.userservice.application.port.in.role.usecase.MappingRoleUseCase;
import com.onetwo.userservice.domain.role.PrivilegeNames;
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

        PrivilegeEntity readPrivilege
                = createPrivilegeUseCase.createPrivilegeIfNotFound(PrivilegeNames.READ_PRIVILEGE);
        PrivilegeEntity writePrivilege
                = createPrivilegeUseCase.createPrivilegeIfNotFound(PrivilegeNames.WRITE_PRIVILEGE);

        List<PrivilegeEntity> adminPrivilege = Arrays.asList(readPrivilege, writePrivilege);
        List<PrivilegeEntity> userPrivilege = Arrays.asList(readPrivilege);

        RoleEntity adminRole = createRoleUseCase.createRoleIfNotFound(RoleNames.ROLE_ADMIN);
        RoleEntity userRole = createRoleUseCase.createRoleIfNotFound(RoleNames.ROLE_USER);

        mappingRoleUseCase.mappingRoleAndPrivilege(adminRole, adminPrivilege);
        mappingRoleUseCase.mappingRoleAndPrivilege(userRole, userPrivilege);

        alreadySetup = true;
    }
}
