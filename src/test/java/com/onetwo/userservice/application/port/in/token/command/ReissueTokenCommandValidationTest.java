package com.onetwo.userservice.application.port.in.token.command;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class ReissueTokenCommandValidationTest {

    private final String accessToken = "accessToken";
    private final String refreshToken = "refreshToken";

    @Test
    @DisplayName("[단위][Command Validation] Reissue Token Command Validation test - 성공 테스트")
    void reissueTokenCommandValidationSuccessTest() {
        //given when then
        Assertions.assertDoesNotThrow(() -> new ReissueTokenCommand(accessToken, refreshToken));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Reissue Token Command access token Validation fail test  - 실패 테스트")
    void reissueTokenCommandAccessTokenValidationFailTest(String testAccessToken) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new ReissueTokenCommand(testAccessToken, refreshToken));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Reissue Token Command refresh token Validation fail test  - 실패 테스트")
    void reissueTokenCommandPasswordValidationFailTest(String testRefreshToken) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new ReissueTokenCommand(accessToken, testRefreshToken));
    }
}