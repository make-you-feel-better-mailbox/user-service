package com.onetwo.userservice.repository.user;

import com.onetwo.userservice.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, QUserRepository {
    Optional<User> findByUserId(String id);
}
