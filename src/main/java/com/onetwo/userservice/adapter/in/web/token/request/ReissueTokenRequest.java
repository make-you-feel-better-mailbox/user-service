package com.onetwo.userservice.adapter.in.web.token.request;

import jakarta.validation.constraints.NotEmpty;

public record ReissueTokenRequest(@NotEmpty String accessToken,
                                  @NotEmpty String refreshToken) {
}
