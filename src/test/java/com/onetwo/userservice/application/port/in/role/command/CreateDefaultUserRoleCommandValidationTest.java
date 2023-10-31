package com.onetwo.userservice.application.port.in.role.command;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class CreateDefaultUserRoleCommandValidationTest {

    @Test
    @DisplayName("[단위][Command Validation] Create Default User Role Command Validation test - 성공 테스트")
    void createDefaultUserRoleCommandValidationSuccessTest() {
        //given
        final String userId = "12OneTwo12";

        //when then
        Assertions.assertDoesNotThrow(() -> new CreateDefaultUserRoleCommand(userId));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Create Default User Role Command user id Validation fail test - 실패 테스트")
    void createDefaultUserRoleCommandValidationSuccessTest(String testUserId) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new CreateDefaultUserRoleCommand(testUserId));
    }
}