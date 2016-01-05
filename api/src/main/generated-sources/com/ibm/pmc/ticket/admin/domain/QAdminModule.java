package com.ibm.pmc.ticket.admin.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QAdminModule is a Querydsl query type for AdminModule
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QAdminModule extends EntityPathBase<AdminModule> {

    private static final long serialVersionUID = 360572963L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAdminModule adminModule = new QAdminModule("adminModule");

    public final QAdmin admin;

    public final StringPath id = createString("id");

    public final EnumPath<Module> module = createEnum("module", Module.class);

    public QAdminModule(String variable) {
        this(AdminModule.class, forVariable(variable), INITS);
    }

    public QAdminModule(Path<? extends AdminModule> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QAdminModule(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QAdminModule(PathMetadata<?> metadata, PathInits inits) {
        this(AdminModule.class, metadata, inits);
    }

    public QAdminModule(Class<? extends AdminModule> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.admin = inits.isInitialized("admin") ? new QAdmin(forProperty("admin")) : null;
    }

}

