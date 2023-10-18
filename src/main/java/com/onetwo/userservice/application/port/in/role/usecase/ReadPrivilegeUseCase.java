package com.onetwo.userservice.application.port.in.role.usecase;

import com.onetwo.userservice.adapter.out.persistence.entity.role.PrivilegeEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.role.RoleEntity;

import java.util.List;

public interface ReadPrivilegeUseCase {
    List<PrivilegeEntity> getPrivilegeByRole(RoleEntity role);
}
