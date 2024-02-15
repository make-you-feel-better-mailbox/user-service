package com.onetwo.userservice.application.port.in.user.response;

public record UserUpdateResponseDto(String userId,
                                    String nickname,
                                    String email,
                                    String phoneNumber,
                                    boolean state) {
}
