package com.onetwo.userservice.controller.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record RegisterUserRequest(@NotEmpty String userId,
                                  @NotEmpty String password,
                                  @NotNull Instant birth,
                                  @NotEmpty String nickname,
                                  @NotEmpty String name,
                                  @NotEmpty String email,
                                  String phoneNumber) {
}
