package com.ibm.pmc.ticket.ticket.domains;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.ibm.pmc.ticket.admin.domain.Module;
import com.ibm.pmc.ticket.reply.domains.Reply;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tickets")
public class Ticket implements Serializable {
    @Id
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "number")
    private long number;

    @Column(name = "module")
    @Enumerated(EnumType.STRING)
    private Module module;


    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "username")
    private String username;

    @Column(name = "user_email")
    private String userMail;

    @Column(name = "user_mobile")
    private String userMobile;

    @Column(name = "version")
    private int version;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    @OrderBy("createdAt")
    private List<Reply> replies = Lists.newArrayList();

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("title", title)
                .add("content", content)
                .add("number", number)
                .add("module", module)
                .add("status", status)
                .add("userId", userId)
                .add("username", username)
                .add("userMail", userMail)
                .add("userMobile", userMobile)
                .add("version", version)
                .add("createdAt", createdAt)
                .add("updatedAt", updatedAt)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(number, ticket.number) &&
                Objects.equals(title, ticket.title) &&
                Objects.equals(content, ticket.content) &&
                Objects.equals(module, ticket.module) &&
                Objects.equals(status, ticket.status) &&
                Objects.equals(userId, ticket.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content, number, module, status, userId);
    }

    public List<Reply> getReplies() {
        return replies;
    }


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Module getModule() {
        return module;
    }

    public Status getStatus() {
        return status;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getUserMail() {
        return userMail;
    }

    public String getUserMobile() {
        return userMobile;
    }


    public int getVersion() {
        return version;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public long getNumber() {
        return number;
    }

    public static class TicketBuilder {
        private Ticket ticket;

        private TicketBuilder() {
            ticket = new Ticket();
        }

        public TicketBuilder withId(String id) {
            ticket.id = id;
            return this;
        }

        public TicketBuilder withTitle(String title) {
            ticket.title = title;
            return this;
        }

        public TicketBuilder withContent(String content) {
            ticket.content = content;
            return this;
        }

        public TicketBuilder withNumber(long number) {
            ticket.number = number;
            return this;
        }

        public TicketBuilder withModule(Module module) {
            ticket.module = module;
            return this;
        }

        public TicketBuilder withStatus(Status status) {
            ticket.status = status;
            return this;
        }

        public TicketBuilder withUserId(String userId) {
            ticket.userId = userId;
            return this;
        }

        public TicketBuilder withUsername(String username) {
            ticket.username = username;
            return this;
        }

        public TicketBuilder withUserMail(String userMail) {
            ticket.userMail = userMail;
            return this;
        }

        public TicketBuilder withUserMobile(String userMobile) {
            ticket.userMobile = userMobile;
            return this;
        }


        public TicketBuilder withVersion(int version) {
            ticket.version = version;
            return this;
        }

        public TicketBuilder withCreatedAt(OffsetDateTime createdAt) {
            ticket.createdAt = createdAt;
            return this;
        }

        public TicketBuilder withUpdatedAt(OffsetDateTime updatedAt) {
            ticket.updatedAt = updatedAt;
            return this;
        }

        public static TicketBuilder ticket() {
            return new TicketBuilder();
        }

        public Ticket build() {
            return ticket;
        }
    }
}
