package com.onetwo.userservice.entity.user;

import com.onetwo.userservice.entity.BaseEntity;
import com.onetwo.userservice.repository.converter.BooleanNumberConverter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.Instant;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uuid;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false, length = 300)
    private String password;

    @Column(nullable = false)
    @Convert(converter = Jsr310JpaConverters.InstantConverter.class)
    private Instant birth;

    @Column(nullable = false, length = 15)
    private String nickname;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 123)
    private String email;

    @Column(length = 20)
    private String phoneNumber;

    @Column(nullable = false, length = 1)
    @Convert(converter = BooleanNumberConverter.class)
    private Boolean state;

    public void setDefaultState() {
        this.state = false;
        setCreatedAt(Instant.now());
        setCreateUser(this.userId);
    }

    public void setEncodePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public boolean isUserWithdraw() {
        return this.getState();
    }
}
