package com.onetwo.userservice.controller.request;

import jakarta.validation.constraints.NotEmpty;

public record LoginUserRequest(@NotEmpty String userId,
                               @NotEmpty String password) {
}
