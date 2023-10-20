package com.onetwo.userservice.application.port.in.role.usecase;

import com.onetwo.userservice.domain.role.Privilege;
import com.onetwo.userservice.domain.role.Role;

import java.util.List;

public interface ReadPrivilegeUseCase {
    List<Privilege> getPrivilegeByRole(Role role);
}
