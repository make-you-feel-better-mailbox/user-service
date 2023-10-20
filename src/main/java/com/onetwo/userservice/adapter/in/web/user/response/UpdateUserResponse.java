package com.onetwo.userservice.adapter.in.web.user.response;

import java.time.Instant;

public record UpdateUserResponse(String userId,
                                 Instant birth,
                                 String nickname,
                                 String name,
                                 String email,
                                 String phoneNumber,
                                 boolean state) {
}
