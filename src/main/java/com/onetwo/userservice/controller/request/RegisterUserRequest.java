package com.onetwo.userservice.controller.request;

import jakarta.validation.constraints.NotNull;

public record RegisterUserRequest(@NotNull String userId,
                                  @NotNull String password,
                                  @NotNull String nickname,
                                  @NotNull String name,
                                  @NotNull String email,
                                  String phoneNumber) {
}
