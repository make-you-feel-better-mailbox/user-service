package com.onetwo.userservice.domain.role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PrivilegeNames {
    READ_PRIVILEGE("READ_PRIVILEGE"), WRITE_PRIVILEGE("WRITE_PRIVILEGE");

    private final String value;
}
