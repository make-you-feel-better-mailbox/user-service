package com.onetwo.userservice.adapter.in.web.user.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record OAuthLoginRequest(@NotNull String code,
                                @NotEmpty String registrationId) {
}
