package com.onetwo.userservice.adapter.in.web.user.response;

public record UserDetailResponse(String userId,
                                 String nickname,
                                 String email,
                                 String phoneNumber,
                                 String profileImageEndPoint,
                                 boolean oauth,
                                 String registrationId,
                                 boolean state) {
}
