package com.onetwo.userservice.application.port.in.user.command;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class WithdrawUserCommandValidationTest {

    private final String userId = "12OneTwo12";
    private final String password = "password";
    private final String requestUserId = userId;

    @Test
    @DisplayName("[단위][Command Validation] Withdraw User Command Validation test - 성공 테스트")
    void withdrawUserCommandValidationSuccessTest() {
        //given when then
        Assertions.assertDoesNotThrow(() -> new WithdrawUserCommand(userId, password, requestUserId));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Withdraw User Command user Id Validation fail test  - 실패 테스트")
    void withdrawUserCommandUserIdValidationFailTest(String testUserId) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new WithdrawUserCommand(testUserId, password, requestUserId));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Withdraw User Command password Validation fail test  - 실패 테스트")
    void withdrawUserCommandPasswordValidationFailTest(String testPassword) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new WithdrawUserCommand(userId, testPassword, requestUserId));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Withdraw User Command request user id Validation fail test  - 실패 테스트")
    void withdrawUserCommandRequestUserIdValidationFailTest(String testRequestUserId) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new WithdrawUserCommand(userId, password, testRequestUserId));
    }
}