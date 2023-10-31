package com.onetwo.userservice.application.port.in.user.response;

import java.time.Instant;

public record UserDetailResponseDto(String userId,
                                    Instant birth,
                                    String nickname,
                                    String name,
                                    String email,
                                    String phoneNumber,
                                    boolean state) {
}