package com.onetwo.userservice.adapter.in.web.user.response;

public record UserInfoResponse(String userId,
                               String nickname,
                               String email,
                               String phoneNumber,
                               String profileImageEndPoint,
                               boolean oauth,
                               String registrationId) {
}
