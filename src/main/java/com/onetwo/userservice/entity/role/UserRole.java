package com.onetwo.userservice.entity.role;

import com.onetwo.userservice.common.GlobalStatus;
import com.onetwo.userservice.entity.BaseEntity;
import com.onetwo.userservice.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor
public class UserRole extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
        setCreateUser(GlobalStatus.SYSTEM);
        setCreatedAt(Instant.now());
    }
}
