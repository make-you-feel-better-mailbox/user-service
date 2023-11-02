package com.onetwo.userservice.application.port.in.user.command;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class LogoutUserCommandValidationTest {

    @Test
    @DisplayName("[단위][Command Validation] Logout User Command Validation test - 성공 테스트")
    void logoutUserCommandValidationSuccessTest() {
        //given
        final String userId = "12OneTwo12";

        //when then
        Assertions.assertDoesNotThrow(() -> new LogoutUserCommand(userId));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Login User Command user Id Validation fail test - 실패 테스트")
    void loginUserCommandUserIdValidationFailTest(String testUserId) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new LogoutUserCommand(testUserId));
    }
}