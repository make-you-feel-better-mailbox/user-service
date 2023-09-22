package com.onetwo.userservice.repository.role;

import com.onetwo.userservice.entity.role.Role;
import com.onetwo.userservice.entity.role.UserRole;
import com.onetwo.userservice.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findByUser(User user);

    Optional<UserRole> findByUserAndRole(User savedUser, Role role);
}
