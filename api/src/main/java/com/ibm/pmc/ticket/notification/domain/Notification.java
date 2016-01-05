package com.ibm.pmc.ticket.notification.domain;

import com.google.common.base.MoreObjects;
import com.ibm.pmc.ticket.reply.domains.Reply;
import com.ibm.pmc.ticket.ticket.domains.Ticket;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticketId;

    @OneToOne
    @JoinColumn(name = "reply_id")
    private Reply reply;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private NotificationCategory notificationCategory;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    private Notification(Builder builder) {
        id = builder.id;
        ticketId = builder.ticketId;
        reply = builder.reply;
        notificationCategory = builder.notificationCategory;
        notificationType = builder.notificationType;
        notificationStatus = builder.notificationStatus;
        createdAt = builder.createdAt;
        updatedAt = builder.updatedAt;
    }

    public Notification() {

    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public Ticket getTicketId() {
        return ticketId;
    }

    public NotificationCategory getNotificationCategory() {
        return notificationCategory;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Reply getReply() {
        return reply;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(reply, that.reply);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reply);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("ticketId", ticketId)
                .add("reply", reply)
                .add("notificationCategory", notificationCategory)
                .add("notificationType", notificationType)
                .add("notificationStatus", notificationStatus)
                .add("createdAt", createdAt)
                .add("updatedAt", updatedAt)
                .toString();
    }

    public static final class Builder {
        private String id;
        private Ticket ticketId;
        private Reply reply;
        private NotificationCategory notificationCategory;
        private NotificationType notificationType;
        private NotificationStatus notificationStatus;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        private Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withTicketId(Ticket ticketId) {
            this.ticketId = ticketId;
            return this;
        }

        public Builder withReply(Reply reply) {
            this.reply = reply;
            return this;
        }

        public Builder withNotificationCategory(NotificationCategory notificationCategory) {
            this.notificationCategory = notificationCategory;
            return this;
        }

        public Builder withNotificationType(NotificationType notificationType) {
            this.notificationType = notificationType;
            return this;
        }

        public Builder withNotificationStatus(NotificationStatus notificationStatus) {
            this.notificationStatus = notificationStatus;
            return this;
        }

        public Builder withCreatedAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder withUpdatedAt(OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Notification build() {
            return new Notification(this);
        }
    }

    public void setNotificationStatus(NotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
