package com.ibm.pmc.ticket.admin.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QAdmin is a Querydsl query type for Admin
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QAdmin extends EntityPathBase<Admin> {

    private static final long serialVersionUID = -276360489L;

    public static final QAdmin admin = new QAdmin("admin");

    public final DateTimePath<java.time.OffsetDateTime> createdAt = createDateTime("createdAt", java.time.OffsetDateTime.class);

    public final StringPath email = createString("email");

    public final StringPath id = createString("id");

    public final StringPath mobile = createString("mobile");

    public final SetPath<AdminModule, QAdminModule> modules = this.<AdminModule, QAdminModule>createSet("modules", AdminModule.class, QAdminModule.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final DateTimePath<java.time.OffsetDateTime> updatedAt = createDateTime("updatedAt", java.time.OffsetDateTime.class);

    public final StringPath username = createString("username");

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QAdmin(String variable) {
        super(Admin.class, forVariable(variable));
    }

    public QAdmin(Path<? extends Admin> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdmin(PathMetadata<?> metadata) {
        super(Admin.class, metadata);
    }

}

