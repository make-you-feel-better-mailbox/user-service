package com.onetwo.userservice.repository;

import com.onetwo.userservice.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, QUserRepository {
}
