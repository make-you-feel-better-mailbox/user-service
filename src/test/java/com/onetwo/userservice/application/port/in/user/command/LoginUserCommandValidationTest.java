package com.onetwo.userservice.application.port.in.user.command;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class LoginUserCommandValidationTest {

    @Test
    @DisplayName("[단위][Command Validation] Login User Command Validation test - 성공 테스트")
    void loginUserCommandValidationSuccessTest() {
        //given when then
        final String userId = "12OneTwo12";
        final String password = "password";

        //when then
        Assertions.assertDoesNotThrow(() -> new LoginUserCommand(userId, password));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Login User Command user Id Validation fail test - 실패 테스트")
    void loginUserCommandUserIdValidationFailTest(String testUserId) {
        //given when then
        final String password = "password";

        Assertions.assertThrows(ConstraintViolationException.class, () -> new LoginUserCommand(testUserId, password));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Login User Command password Validation fail test - 실패 테스트")
    void loginUserCommandPasswordValidationFailTest(String testPassword) {
        //given when then
        final String userId = "12OneTwo12";

        Assertions.assertThrows(ConstraintViolationException.class, () -> new LoginUserCommand(userId, testPassword));
    }
}