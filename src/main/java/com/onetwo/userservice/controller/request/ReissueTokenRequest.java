package com.onetwo.userservice.controller.request;

import jakarta.validation.constraints.NotEmpty;

public record ReissueTokenRequest(@NotEmpty String accessToken,
                                  @NotEmpty String refreshToken) {
}
