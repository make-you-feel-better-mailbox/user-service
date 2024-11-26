package com.onetwo.userservice.application.port.in.user.response;

public record UserInfoResponseDto(String userId,
                                  String nickname,
                                  String email,
                                  String phoneNumber,
                                  String profileImageEndPoint,
                                  boolean oauth,
                                  String registrationId) {
}
