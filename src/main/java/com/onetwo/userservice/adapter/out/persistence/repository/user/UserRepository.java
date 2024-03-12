package com.onetwo.userservice.adapter.out.persistence.repository.user;

import com.onetwo.userservice.adapter.out.persistence.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, QUserRepository {
    Optional<UserEntity> findByUserId(String id);

    Optional<UserEntity> findByUserIdAndOauthAndState(String oAuthUserId, Boolean persistenceUserIsOauth, Boolean persistenceNotDeleted);
}
