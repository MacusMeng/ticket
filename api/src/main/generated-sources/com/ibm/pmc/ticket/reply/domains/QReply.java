package com.ibm.pmc.ticket.reply.domains;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QReply is a Querydsl query type for Reply
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QReply extends EntityPathBase<Reply> {

    private static final long serialVersionUID = 33497492L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReply reply = new QReply("reply");

    public final com.ibm.pmc.ticket.admin.domain.QAdmin admin;

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.OffsetDateTime> createdAt = createDateTime("createdAt", java.time.OffsetDateTime.class);

    public final StringPath id = createString("id");

    public final com.ibm.pmc.ticket.ticket.domains.QTicket ticket;

    public final StringPath userId = createString("userId");

    public final StringPath username = createString("username");

    public QReply(String variable) {
        this(Reply.class, forVariable(variable), INITS);
    }

    public QReply(Path<? extends Reply> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QReply(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QReply(PathMetadata<?> metadata, PathInits inits) {
        this(Reply.class, metadata, inits);
    }

    public QReply(Class<? extends Reply> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.admin = inits.isInitialized("admin") ? new com.ibm.pmc.ticket.admin.domain.QAdmin(forProperty("admin")) : null;
        this.ticket = inits.isInitialized("ticket") ? new com.ibm.pmc.ticket.ticket.domains.QTicket(forProperty("ticket")) : null;
    }

}

