package com.onetwo.userservice.controller.request;

import java.util.Objects;

import org.hibernate.annotations.NotFound;

import com.onetwo.userservice.entity.user.Role;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record RegisterUserRequest(@NotNull String userId,
								  @NotNull String password,
								  @NotNull String nickname,
								  @NotNull String name,
								  @NotNull String email,
								  String phoneNumber,
								  Role role) {
}
