package com.ibm.pmc.ticket.reply.domains;

import com.google.common.base.MoreObjects;
import com.ibm.pmc.ticket.admin.domain.Admin;
import com.ibm.pmc.ticket.ticket.domains.Ticket;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "replies")
public class Reply {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "username")
    private String username;

    @OneToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public Admin getAdmin() {
        return admin;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reply reply = (Reply) o;
        return Objects.equals(id, reply.id) &&
                Objects.equals(content, reply.content) &&
                Objects.equals(ticket, reply.ticket) &&
                Objects.equals(userId, reply.userId) &&
                Objects.equals(username, reply.username) &&
                Objects.equals(admin, reply.admin) &&
                Objects.equals(createdAt, reply.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, ticket, userId, username, admin, createdAt);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("content", content)
                .add("ticket", ticket)
                .add("userId", userId)
                .add("username", username)
                .add("admin", admin)
                .add("createdAt", createdAt)
                .toString();
    }

    public static class ReplyBuilder {
        private Reply reply;

        private ReplyBuilder() {
            reply = new Reply();
        }

        public ReplyBuilder withId(String id) {
            reply.id = id;
            return this;
        }

        public ReplyBuilder withContent(String content) {
            reply.content = content;
            return this;
        }

        public ReplyBuilder withTicket(Ticket ticket) {
            reply.ticket = ticket;
            return this;
        }

        public ReplyBuilder withUserId(String userId) {
            reply.userId = userId;
            return this;
        }

        public ReplyBuilder withUsername(String username) {
            reply.username = username;
            return this;
        }

        public ReplyBuilder withAdmin(Admin admin) {
            reply.admin = admin;
            return this;
        }

        public ReplyBuilder withCreatedAt(OffsetDateTime createdAt) {
            reply.createdAt = createdAt;
            return this;
        }

        public static ReplyBuilder replies() {
            return new ReplyBuilder();
        }

        public Reply build() {
            return reply;
        }
    }
}
