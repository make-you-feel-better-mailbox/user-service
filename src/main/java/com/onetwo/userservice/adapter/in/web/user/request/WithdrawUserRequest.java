package com.onetwo.userservice.adapter.in.web.user.request;

import jakarta.validation.constraints.NotNull;

public record WithdrawUserRequest(@NotNull String userId,
                                  @NotNull String password) {
}
