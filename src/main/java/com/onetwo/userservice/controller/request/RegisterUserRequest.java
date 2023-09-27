package com.onetwo.userservice.controller.request;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record RegisterUserRequest(@NotNull String userId,
                                  @NotNull String password,
                                  @NotNull Instant birth,
                                  @NotNull String nickname,
                                  @NotNull String name,
                                  @NotNull String email,
                                  String phoneNumber) {
}
