package com.onetwo.userservice.application.service.response;

import java.time.Instant;

public record UserUpdateResponseDto(String userId,
                                    Instant birth,
                                    String nickname,
                                    String name,
                                    String email,
                                    String phoneNumber,
                                    boolean state) {
}
