package com.onetwo.userservice.service.requset;

import jakarta.validation.constraints.NotEmpty;

public record LoginDto(@NotEmpty String id,
                       @NotEmpty String pw) {
}
