package com.onetwo.userservice.adapter.in.web.user.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record UpdateUserRequest(@NotNull Instant birth,
                                @NotEmpty String nickname,
                                @NotEmpty String name,
                                @NotEmpty String email,
                                String phoneNumber) {
}
