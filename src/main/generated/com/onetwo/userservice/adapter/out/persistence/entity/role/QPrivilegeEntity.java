package com.onetwo.userservice.adapter.out.persistence.entity.role;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPrivilegeEntity is a Querydsl query type for PrivilegeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPrivilegeEntity extends EntityPathBase<PrivilegeEntity> {

    private static final long serialVersionUID = 1821135322L;

    public static final QPrivilegeEntity privilegeEntity = new QPrivilegeEntity("privilegeEntity");

    public final com.onetwo.userservice.adapter.out.persistence.entity.QBaseEntity _super = new com.onetwo.userservice.adapter.out.persistence.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.Instant> createdAt = _super.createdAt;

    //inherited
    public final StringPath createUser = _super.createUser;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.onetwo.userservice.domain.role.PrivilegeNames> privilegeName = createEnum("privilegeName", com.onetwo.userservice.domain.role.PrivilegeNames.class);

    //inherited
    public final DateTimePath<java.time.Instant> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updateUser = _super.updateUser;

    public QPrivilegeEntity(String variable) {
        super(PrivilegeEntity.class, forVariable(variable));
    }

    public QPrivilegeEntity(Path<? extends PrivilegeEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPrivilegeEntity(PathMetadata metadata) {
        super(PrivilegeEntity.class, metadata);
    }

}

