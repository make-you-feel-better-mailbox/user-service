package com.onetwo.userservice.service.requset;

import java.time.Instant;

public record UserDto(String userId,
                      String password,
                      Instant birth,
                      String nickname,
                      String name,
                      String email,
                      String phoneNumber) {
}
