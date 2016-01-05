package com.ibm.pmc.ticket.notification.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QNotification is a Querydsl query type for Notification
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QNotification extends EntityPathBase<Notification> {

    private static final long serialVersionUID = 426459205L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNotification notification = new QNotification("notification");

    public final DateTimePath<java.time.OffsetDateTime> createdAt = createDateTime("createdAt", java.time.OffsetDateTime.class);

    public final StringPath id = createString("id");

    public final EnumPath<NotificationCategory> notificationCategory = createEnum("notificationCategory", NotificationCategory.class);

    public final EnumPath<NotificationStatus> notificationStatus = createEnum("notificationStatus", NotificationStatus.class);

    public final EnumPath<NotificationType> notificationType = createEnum("notificationType", NotificationType.class);

    public final com.ibm.pmc.ticket.reply.domains.QReply reply;

    public final com.ibm.pmc.ticket.ticket.domains.QTicket ticketId;

    public final DateTimePath<java.time.OffsetDateTime> updatedAt = createDateTime("updatedAt", java.time.OffsetDateTime.class);

    public QNotification(String variable) {
        this(Notification.class, forVariable(variable), INITS);
    }

    public QNotification(Path<? extends Notification> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QNotification(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QNotification(PathMetadata<?> metadata, PathInits inits) {
        this(Notification.class, metadata, inits);
    }

    public QNotification(Class<? extends Notification> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reply = inits.isInitialized("reply") ? new com.ibm.pmc.ticket.reply.domains.QReply(forProperty("reply"), inits.get("reply")) : null;
        this.ticketId = inits.isInitialized("ticketId") ? new com.ibm.pmc.ticket.ticket.domains.QTicket(forProperty("ticketId")) : null;
    }

}

