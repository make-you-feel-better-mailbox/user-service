package com.onetwo.userservice.adapter.out.persistence.repository.user;

import com.onetwo.userservice.adapter.out.persistence.entity.user.UserEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class QUserRepositoryImpl extends QuerydslRepositorySupport implements QUserRepository {

    public QUserRepositoryImpl() {
        super(UserEntity.class);
    }

}
