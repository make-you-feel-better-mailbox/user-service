package com.onetwo.userservice.adapter.in.web.user.request;

import jakarta.validation.constraints.NotEmpty;

public record UpdateUserRequest(
        @NotEmpty String nickname,
        @NotEmpty String email,
        String profileImageEndPoint,
        String phoneNumber) {
}
