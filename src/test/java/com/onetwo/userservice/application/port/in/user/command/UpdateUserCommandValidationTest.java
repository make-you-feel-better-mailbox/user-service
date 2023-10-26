package com.onetwo.userservice.application.port.in.user.command;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import java.time.Instant;

class UpdateUserCommandValidationTest {

    private final String userId = "12OneTwo12";
    private final Instant birth = Instant.now();
    private final String nickname = "newNickname";
    private final String name = "tester";
    private final String email = "onetwo12@onetwo.com";
    private final String phoneNumber = "01098006069";

    @Test
    @DisplayName("[단위][Command Validation] Update User Command Validation test - 성공 테스트")
    void updateUserCommandValidationSuccessTest() {
        //given when then
        Assertions.assertDoesNotThrow(() -> new UpdateUserCommand(userId, birth, nickname, name, email, phoneNumber));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Update User Command user Id Validation fail test - 실패 테스트")
    void updateUserCommandUserIdValidationFailTest(String testUserId) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new UpdateUserCommand(testUserId, birth, nickname, name, email, phoneNumber));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("[단위][Command Validation] Update User Command user Id Validation fail test - 실패 테스트")
    void updateUserCommandBirthValidationFailTest(Instant testBirth) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new UpdateUserCommand(userId, testBirth, nickname, name, email, phoneNumber));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Update User Command nickname Validation fail test - 실패 테스트")
    void updateUserCommandNicknameValidationFailTest(String testNickname) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new UpdateUserCommand(userId, birth, testNickname, name, email, phoneNumber));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Update User Command name Validation fail test - 실패 테스트")
    void updateUserCommandNameValidationFailTest(String testName) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new UpdateUserCommand(userId, birth, nickname, testName, email, phoneNumber));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Update User Command email Validation fail test - 실패 테스트")
    void updateUserCommandEmailValidationFailTest(String testEmail) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new UpdateUserCommand(userId, birth, nickname, name, testEmail, phoneNumber));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Update User Command phoneNumber Validation success test - 성공 테스트")
    void updateUserCommandPhoneNumberValidationSuccessTest(String testPhoneNumber) {
        //given when then
        Assertions.assertDoesNotThrow(() -> new UpdateUserCommand(userId, birth, nickname, name, email, testPhoneNumber));
    }
}