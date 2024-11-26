package com.onetwo.userservice.adapter.in.web.user.request;

import jakarta.validation.constraints.NotEmpty;

public record UpdateUserPasswordRequest(@NotEmpty String currentPassword,
                                        @NotEmpty String newPassword,
                                        @NotEmpty String newPasswordCheck) {
}
