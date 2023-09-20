package com.onetwo.userservice.service.requset;

public record UserDto(String userId,
                      String password,
                      String nickname,
                      String name,
                      String email,
                      String phoneNumber) {
}
