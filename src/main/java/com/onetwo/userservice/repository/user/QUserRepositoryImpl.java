package com.onetwo.userservice.repository.user;

import com.onetwo.userservice.entity.user.User;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class QUserRepositoryImpl extends QuerydslRepositorySupport implements QUserRepository {

    public QUserRepositoryImpl() {
        super(User.class);
    }

}
