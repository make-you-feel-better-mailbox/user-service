package com.onetwo.userservice.adapter.out.persistence.repository.user;

import com.onetwo.userservice.adapter.out.persistence.entity.user.User;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class QUserRepositoryImpl extends QuerydslRepositorySupport implements QUserRepository {

    public QUserRepositoryImpl() {
        super(User.class);
    }

}
