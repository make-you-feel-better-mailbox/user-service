package com.onetwo.userservice.adapter.out.persistence.entity.role;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserRoleEntity is a Querydsl query type for UserRoleEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserRoleEntity extends EntityPathBase<UserRoleEntity> {

    private static final long serialVersionUID = 2023439742L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserRoleEntity userRoleEntity = new QUserRoleEntity("userRoleEntity");

    public final com.onetwo.userservice.adapter.out.persistence.entity.QBaseEntity _super = new com.onetwo.userservice.adapter.out.persistence.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.Instant> createdAt = _super.createdAt;

    //inherited
    public final StringPath createUser = _super.createUser;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QRoleEntity role;

    //inherited
    public final DateTimePath<java.time.Instant> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updateUser = _super.updateUser;

    public final com.onetwo.userservice.adapter.out.persistence.entity.user.QUserEntity user;

    public QUserRoleEntity(String variable) {
        this(UserRoleEntity.class, forVariable(variable), INITS);
    }

    public QUserRoleEntity(Path<? extends UserRoleEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserRoleEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserRoleEntity(PathMetadata metadata, PathInits inits) {
        this(UserRoleEntity.class, metadata, inits);
    }

    public QUserRoleEntity(Class<? extends UserRoleEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.role = inits.isInitialized("role") ? new QRoleEntity(forProperty("role")) : null;
        this.user = inits.isInitialized("user") ? new com.onetwo.userservice.adapter.out.persistence.entity.user.QUserEntity(forProperty("user")) : null;
    }

}

