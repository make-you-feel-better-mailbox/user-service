package com.onetwo.userservice.entity.role;

import com.onetwo.userservice.entity.BaseEntity;
import com.onetwo.userservice.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class UserRole extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id")
    private Role role;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }
}
