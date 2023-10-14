package com.onetwo.userservice.common.config;

import com.onetwo.userservice.adapter.out.persistence.entity.role.Privilege;
import com.onetwo.userservice.adapter.out.persistence.entity.role.PrivilegeNames;
import com.onetwo.userservice.adapter.out.persistence.entity.role.Role;
import com.onetwo.userservice.adapter.out.persistence.entity.role.RoleNames;
import com.onetwo.userservice.application.port.out.role.CreatePrivilegePort;
import com.onetwo.userservice.application.port.out.role.CreateRolePort;
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
    private final CreateRolePort createRolePort;
    private final CreatePrivilegePort createPrivilegePort;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;

        Privilege readPrivilege
                = createPrivilegePort.createPrivilegeIfNotFound(PrivilegeNames.READ_PRIVILEGE);
        Privilege writePrivilege
                = createPrivilegePort.createPrivilegeIfNotFound(PrivilegeNames.WRITE_PRIVILEGE);

        List<Privilege> adminPrivilege = Arrays.asList(readPrivilege, writePrivilege);
        List<Privilege> userPrivilege = Arrays.asList(readPrivilege);

        Role adminRole = createRolePort.createRoleIfNotFound(RoleNames.ROLE_ADMIN);
        Role userRole = createRolePort.createRoleIfNotFound(RoleNames.ROLE_USER);

        createPrivilegePort.mappingRoleAndPrivilege(adminRole, adminPrivilege);
        createPrivilegePort.mappingRoleAndPrivilege(userRole, userPrivilege);

        alreadySetup = true;
    }
}
