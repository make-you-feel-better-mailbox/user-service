package com.onetwo.userservice.service.service;

import com.onetwo.userservice.entity.role.Privilege;
import com.onetwo.userservice.entity.role.PrivilegeNames;
import com.onetwo.userservice.entity.role.Role;
import com.onetwo.userservice.entity.role.RolePrivilege;
import com.onetwo.userservice.repository.role.PrivilegeRepository;
import com.onetwo.userservice.repository.role.RolePrivilegeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrivilegeServiceImpl implements PrivilegeService {

    private final PrivilegeRepository privilegeRepository;
    private final RolePrivilegeRepository rolePrivilegeRepository;

    @Override
    @Transactional
    public Privilege createPrivilegeIfNotFound(PrivilegeNames privilegeName) {
        Optional<Privilege> optionalPrivilege = privilegeRepository.findByPrivilegeName(privilegeName);

        Privilege privilege;

        if (optionalPrivilege.isEmpty()) {
            privilege = new Privilege(privilegeName);
            privilegeRepository.save(privilege);
        } else privilege = optionalPrivilege.get();

        return privilege;
    }

    @Override
    @Transactional
    public void mappingRoleAndPrivilege(Role role, List<Privilege> privileges) {
        privileges.forEach(privilege -> {
            Optional<RolePrivilege> optionalRolePrivilege = rolePrivilegeRepository.findByRoleAndPrivilege(role, privilege);
            if (optionalRolePrivilege.isEmpty()) {
                rolePrivilegeRepository.save(new RolePrivilege(role, privilege));
            }
        });
    }

    @Override
    public List<Privilege> getPrivilegeByRole(Role role) {
        List<RolePrivilege> rolePrivileges = rolePrivilegeRepository.findByRole(role);

        if (rolePrivileges == null || rolePrivileges.size() == 0) return null;

        return rolePrivileges.stream().map(RolePrivilege::getPrivilege).toList();
    }
}
