package com.onetwo.userservice.adapter.in.web.user.response;

public record UpdateUserResponse(String userId,
                                 String nickname,
                                 String email,
                                 String phoneNumber,
                                 boolean state) {
}
