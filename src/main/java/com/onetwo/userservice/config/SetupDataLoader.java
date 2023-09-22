package com.onetwo.userservice.config;

import com.onetwo.userservice.entity.role.Privilege;
import com.onetwo.userservice.entity.role.PrivilegeNames;
import com.onetwo.userservice.entity.role.Role;
import com.onetwo.userservice.entity.role.RoleNames;
import com.onetwo.userservice.service.service.PrivilegeService;
import com.onetwo.userservice.service.service.RoleService;
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
    private final RoleService roleService;
    private final PrivilegeService privilegeService;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;

        Privilege readPrivilege
                = privilegeService.createPrivilegeIfNotFound(PrivilegeNames.READ_PRIVILEGE);
        Privilege writePrivilege
                = privilegeService.createPrivilegeIfNotFound(PrivilegeNames.WRITE_PRIVILEGE);

        List<Privilege> adminPrivilege = Arrays.asList(readPrivilege, writePrivilege);
        List<Privilege> userPrivilege = Arrays.asList(readPrivilege);

        Role adminRole = roleService.createRoleIfNotFound(RoleNames.ROLE_ADMIN);
        Role userRole = roleService.createRoleIfNotFound(RoleNames.ROLE_USER);

        privilegeService.mappingRoleAndPrivilege(adminRole, adminPrivilege);
        privilegeService.mappingRoleAndPrivilege(userRole, userPrivilege);

        alreadySetup = true;
    }
}
