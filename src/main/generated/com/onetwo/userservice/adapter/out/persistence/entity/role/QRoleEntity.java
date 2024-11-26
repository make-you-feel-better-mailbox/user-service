package com.onetwo.userservice.adapter.out.persistence.entity.role;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRoleEntity is a Querydsl query type for RoleEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoleEntity extends EntityPathBase<RoleEntity> {

    private static final long serialVersionUID = -1860684717L;

    public static final QRoleEntity roleEntity = new QRoleEntity("roleEntity");

    public final com.onetwo.userservice.adapter.out.persistence.entity.QBaseEntity _super = new com.onetwo.userservice.adapter.out.persistence.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.Instant> createdAt = _super.createdAt;

    //inherited
    public final StringPath createUser = _super.createUser;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.onetwo.userservice.domain.role.RoleNames> roleName = createEnum("roleName", com.onetwo.userservice.domain.role.RoleNames.class);

    //inherited
    public final DateTimePath<java.time.Instant> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updateUser = _super.updateUser;

    public QRoleEntity(String variable) {
        super(RoleEntity.class, forVariable(variable));
    }

    public QRoleEntity(Path<? extends RoleEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRoleEntity(PathMetadata metadata) {
        super(RoleEntity.class, metadata);
    }

}

