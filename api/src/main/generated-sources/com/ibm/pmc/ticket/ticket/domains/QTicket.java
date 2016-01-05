package com.ibm.pmc.ticket.ticket.domains;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QTicket is a Querydsl query type for Ticket
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QTicket extends EntityPathBase<Ticket> {

    private static final long serialVersionUID = 291572474L;

    public static final QTicket ticket = new QTicket("ticket");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.OffsetDateTime> createdAt = createDateTime("createdAt", java.time.OffsetDateTime.class);

    public final StringPath id = createString("id");

    public final EnumPath<com.ibm.pmc.ticket.admin.domain.Module> module = createEnum("module", com.ibm.pmc.ticket.admin.domain.Module.class);

    public final NumberPath<Long> number = createNumber("number", Long.class);

    public final ListPath<com.ibm.pmc.ticket.reply.domains.Reply, com.ibm.pmc.ticket.reply.domains.QReply> replies = this.<com.ibm.pmc.ticket.reply.domains.Reply, com.ibm.pmc.ticket.reply.domains.QReply>createList("replies", com.ibm.pmc.ticket.reply.domains.Reply.class, com.ibm.pmc.ticket.reply.domains.QReply.class, PathInits.DIRECT2);

    public final EnumPath<Status> status = createEnum("status", Status.class);

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.OffsetDateTime> updatedAt = createDateTime("updatedAt", java.time.OffsetDateTime.class);

    public final StringPath userId = createString("userId");

    public final StringPath userMail = createString("userMail");

    public final StringPath userMobile = createString("userMobile");

    public final StringPath username = createString("username");

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QTicket(String variable) {
        super(Ticket.class, forVariable(variable));
    }

    public QTicket(Path<? extends Ticket> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTicket(PathMetadata<?> metadata) {
        super(Ticket.class, metadata);
    }

}

