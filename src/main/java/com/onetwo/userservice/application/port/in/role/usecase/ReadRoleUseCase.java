package com.onetwo.userservice.application.port.in.role.usecase;

import com.onetwo.userservice.adapter.out.persistence.entity.role.RoleEntity;
import com.onetwo.userservice.adapter.out.persistence.entity.user.UserEntity;

import java.util.List;

public interface ReadRoleUseCase {
    List<RoleEntity> getRolesByUser(UserEntity user);
}
