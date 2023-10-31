package com.onetwo.userservice.application.port.in.user.command;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class UpdateUserPasswordCommandValidationTest {

    private final String userId = "userId";
    private final String currentPassword = "password";
    private final String newPassword = "newPassword";

    @Test
    @DisplayName("[단위][Command Validation] Update User Password Command Validation test - 성공 테스트")
    void updateUserPasswordCommandValidationSuccessTest() {
        //given when then
        Assertions.assertDoesNotThrow(() -> new UpdateUserPasswordCommand(userId, currentPassword, newPassword, newPassword));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Withdraw User Command user Id Validation fail test  - 실패 테스트")
    void withdrawUserCommandUserIdValidationFailTest(String testUserId) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new UpdateUserPasswordCommand(testUserId, currentPassword, newPassword, newPassword));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Withdraw User Command current password Validation fail test  - 실패 테스트")
    void withdrawUserCommandCurrentPasswordValidationFailTest(String testCurrentPassword) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new UpdateUserPasswordCommand(userId, testCurrentPassword, newPassword, newPassword));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Withdraw User Command new password Validation fail test  - 실패 테스트")
    void withdrawUserCommandNewPasswordValidationFailTest(String testNewPassword) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new UpdateUserPasswordCommand(userId, currentPassword, testNewPassword, newPassword));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Withdraw User Command new password check Validation fail test  - 실패 테스트")
    void withdrawUserCommandNewPasswordCheckValidationFailTest(String testNewPasswordCheck) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new UpdateUserPasswordCommand(userId, currentPassword, newPassword, testNewPasswordCheck));
    }
}