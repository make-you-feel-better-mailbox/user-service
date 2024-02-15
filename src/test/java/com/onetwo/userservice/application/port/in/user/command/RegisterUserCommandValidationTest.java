package com.onetwo.userservice.application.port.in.user.command;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class RegisterUserCommandValidationTest {

    private final String userId = "12OneTwo12";
    private final String password = "password";
    private final String nickname = "newNickname";
    private final String email = "onetwo12@onetwo.com";
    private final String phoneNumber = "01098006069";

    @Test
    @DisplayName("[단위][Command Validation] Register User Command Validation test - 성공 테스트")
    void registerUserCommandValidationSuccessTest() {
        //given when then
        Assertions.assertDoesNotThrow(() -> new RegisterUserCommand(userId, password, nickname, email, phoneNumber));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Register User Command user Id Validation fail test - 실패 테스트")
    void registerUserCommandUserIdValidationFailTest(String testUserId) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new RegisterUserCommand(testUserId, password, nickname, email, phoneNumber));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Register User Command password Validation fail test - 실패 테스트")
    void registerUserCommandPasswordValidationFailTest(String testPassword) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new RegisterUserCommand(userId, testPassword, nickname, email, phoneNumber));
    }


    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Register User Command nickname Validation fail test - 실패 테스트")
    void registerUserCommandNicknameValidationFailTest(String testNickname) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new RegisterUserCommand(userId, password, testNickname, email, phoneNumber));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Register User Command email Validation fail test - 실패 테스트")
    void registerUserCommandEmailValidationFailTest(String testEmail) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new RegisterUserCommand(userId, password, nickname, testEmail, phoneNumber));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Register User Command phoneNumber Validation success test - 성공 테스트")
    void registerUserCommandPhoneNumberValidationSuccessTest(String testPhoneNumber) {
        //given when then
        Assertions.assertDoesNotThrow(() -> new RegisterUserCommand(userId, password, nickname, email, testPhoneNumber));
    }
}