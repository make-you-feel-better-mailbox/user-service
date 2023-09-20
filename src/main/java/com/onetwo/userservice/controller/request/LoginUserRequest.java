package com.onetwo.userservice.controller.request;

import jakarta.validation.constraints.NotEmpty;

public record LoginUserRequest(@NotEmpty String id,
                               @NotEmpty String pw) {
}
