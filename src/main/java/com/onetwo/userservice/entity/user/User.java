package com.onetwo.userservice.entity.user;

import com.onetwo.userservice.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false, length = 300)
    private String password;

    @Column(nullable = false, length = 15)
    private String nickname;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 123)
    private String email;

    @Column(length = 20)
    private String phoneNumber;

    @Column(nullable = false, columnDefinition = "bit(1) default 0", length = 1)
    private Boolean state;

    public void setDefaultState() {
        this.state = false;
    }

    public void setEncodePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
