package com.onetwo.userservice.adapter.in.web.user.request;

import jakarta.validation.constraints.NotEmpty;

public record LoginUserRequest(@NotEmpty String userId,
                               @NotEmpty String password) {
}
