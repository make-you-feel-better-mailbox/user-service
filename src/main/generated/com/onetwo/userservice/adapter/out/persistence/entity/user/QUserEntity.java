package com.onetwo.userservice.adapter.out.persistence.entity.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserEntity is a Querydsl query type for UserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEntity extends EntityPathBase<UserEntity> {

    private static final long serialVersionUID = 185117043L;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final com.onetwo.userservice.adapter.out.persistence.entity.QBaseEntity _super = new com.onetwo.userservice.adapter.out.persistence.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.Instant> createdAt = _super.createdAt;

    //inherited
    public final StringPath createUser = _super.createUser;

    public final StringPath email = createString("email");

    public final StringPath nickname = createString("nickname");

    public final BooleanPath oauth = createBoolean("oauth");

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath profileImageEndPoint = createString("profileImageEndPoint");

    public final StringPath registrationId = createString("registrationId");

    public final BooleanPath state = createBoolean("state");

    //inherited
    public final DateTimePath<java.time.Instant> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updateUser = _super.updateUser;

    public final StringPath userId = createString("userId");

    public final NumberPath<Long> uuid = createNumber("uuid", Long.class);

    public QUserEntity(String variable) {
        super(UserEntity.class, forVariable(variable));
    }

    public QUserEntity(Path<? extends UserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEntity(PathMetadata metadata) {
        super(UserEntity.class, metadata);
    }

}

