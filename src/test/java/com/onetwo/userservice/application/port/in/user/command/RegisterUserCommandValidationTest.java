package com.onetwo.userservice.application.port.in.user.command;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import java.time.Instant;

class RegisterUserCommandValidationTest {

    private final String userId = "12OneTwo12";
    private final String password = "password";
    private final Instant birth = Instant.now();
    private final String nickname = "newNickname";
    private final String name = "tester";
    private final String email = "onetwo12@onetwo.com";
    private final String phoneNumber = "01098006069";

    @Test
    @DisplayName("[단위][Command Validation] Register User Command Validation test - 성공 테스트")
    void registerUserCommandValidationSuccessTest() {
        //given when then
        Assertions.assertDoesNotThrow(() -> new RegisterUserCommand(userId, password, birth, nickname, name, email, phoneNumber));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Register User Command user Id Validation fail test - 실패 테스트")
    void registerUserCommandUserIdValidationFailTest(String testUserId) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new RegisterUserCommand(testUserId, password, birth, nickname, name, email, phoneNumber));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Register User Command password Validation fail test - 실패 테스트")
    void registerUserCommandPasswordValidationFailTest(String testPassword) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new RegisterUserCommand(userId, testPassword, birth, nickname, name, email, phoneNumber));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("[단위][Command Validation] Register User Command user Id Validation fail test - 실패 테스트")
    void registerUserCommandBirthValidationFailTest(Instant testBirth) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new RegisterUserCommand(userId, password, testBirth, nickname, name, email, phoneNumber));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Register User Command nickname Validation fail test - 실패 테스트")
    void registerUserCommandNicknameValidationFailTest(String testNickname) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new RegisterUserCommand(userId, password, birth, testNickname, name, email, phoneNumber));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Register User Command name Validation fail test - 실패 테스트")
    void registerUserCommandNameValidationFailTest(String testName) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new RegisterUserCommand(userId, password, birth, nickname, testName, email, phoneNumber));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Register User Command email Validation fail test - 실패 테스트")
    void registerUserCommandEmailValidationFailTest(String testEmail) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new RegisterUserCommand(userId, password, birth, nickname, name, testEmail, phoneNumber));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Register User Command phoneNumber Validation success test - 성공 테스트")
    void registerUserCommandPhoneNumberValidationSuccessTest(String testPhoneNumber) {
        //given when then
        Assertions.assertDoesNotThrow(() -> new RegisterUserCommand(userId, password, birth, nickname, name, email, testPhoneNumber));
    }
}