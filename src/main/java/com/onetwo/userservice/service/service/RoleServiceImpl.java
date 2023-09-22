package com.onetwo.userservice.service.service;

import com.onetwo.userservice.entity.role.Role;
import com.onetwo.userservice.entity.role.RoleNames;
import com.onetwo.userservice.entity.role.UserRole;
import com.onetwo.userservice.entity.user.User;
import com.onetwo.userservice.repository.role.RoleRepository;
import com.onetwo.userservice.repository.role.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    @Transactional
    public void createNewUserRole(User savedUser) {
        Optional<Role> optionalRole = roleRepository.findByRoleName(RoleNames.ROLE_USER);

        Role role;
        if (optionalRole.isEmpty()) role = createRoleIfNotFound(RoleNames.ROLE_USER);
        else role = optionalRole.get();

        Optional<UserRole> optionalUserRole = userRoleRepository.findByUserAndRole(savedUser, role);

        if (optionalUserRole.isEmpty()) userRoleRepository.save(new UserRole(savedUser, role));
    }

    @Override
    @Transactional
    public Role createRoleIfNotFound(RoleNames roleName) {
        Optional<Role> optionalRole = roleRepository.findByRoleName(roleName);

        Role role;

        if (optionalRole.isEmpty()) {
            role = new Role(roleName);
            roleRepository.save(role);
        } else role = optionalRole.get();

        return role;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getRolesByUser(User user) {
        List<UserRole> userRoles = userRoleRepository.findByUser(user);

        if (userRoles == null || userRoles.size() == 0) return null;

        return userRoles.stream().map(UserRole::getRole).toList();
    }
}
