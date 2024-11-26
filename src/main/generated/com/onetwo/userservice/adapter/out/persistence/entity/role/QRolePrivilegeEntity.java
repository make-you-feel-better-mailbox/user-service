package com.onetwo.userservice.adapter.out.persistence.entity.role;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRolePrivilegeEntity is a Querydsl query type for RolePrivilegeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRolePrivilegeEntity extends EntityPathBase<RolePrivilegeEntity> {

    private static final long serialVersionUID = -1461884028L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRolePrivilegeEntity rolePrivilegeEntity = new QRolePrivilegeEntity("rolePrivilegeEntity");

    public final com.onetwo.userservice.adapter.out.persistence.entity.QBaseEntity _super = new com.onetwo.userservice.adapter.out.persistence.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.Instant> createdAt = _super.createdAt;

    //inherited
    public final StringPath createUser = _super.createUser;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPrivilegeEntity privilege;

    public final QRoleEntity role;

    //inherited
    public final DateTimePath<java.time.Instant> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updateUser = _super.updateUser;

    public QRolePrivilegeEntity(String variable) {
        this(RolePrivilegeEntity.class, forVariable(variable), INITS);
    }

    public QRolePrivilegeEntity(Path<? extends RolePrivilegeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRolePrivilegeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRolePrivilegeEntity(PathMetadata metadata, PathInits inits) {
        this(RolePrivilegeEntity.class, metadata, inits);
    }

    public QRolePrivilegeEntity(Class<? extends RolePrivilegeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.privilege = inits.isInitialized("privilege") ? new QPrivilegeEntity(forProperty("privilege")) : null;
        this.role = inits.isInitialized("role") ? new QRoleEntity(forProperty("role")) : null;
    }

}

