package com.ibm.pmc.ticket.reply.jsons;


import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Objects;

public class ReplyRequest implements Serializable {
    private String id;

    private String content;

    private String ticketId;

    private String userId;

    private String username;

    private String adminName;

    private OffsetDateTime createdAt;

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getAdminName() {
        return adminName;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("content", content)
                .add("ticketId", ticketId)
                .add("userId", userId)
                .add("username", username)
                .add("adminId", adminName)
                .add("createdAt", createdAt)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReplyRequest replyRequest = (ReplyRequest) o;
        return Objects.equals(id, replyRequest.id) &&
                Objects.equals(content, replyRequest.content) &&
                Objects.equals(ticketId, replyRequest.ticketId) &&
                Objects.equals(userId, replyRequest.userId) &&
                Objects.equals(username, replyRequest.username) &&
                Objects.equals(adminName, replyRequest.adminName) &&
                Objects.equals(createdAt, replyRequest.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, ticketId, userId, username, adminName, createdAt);
    }

    public static class ReplyInfoBuilder {
        private ReplyRequest replyRequest;

        private ReplyInfoBuilder() {
            replyRequest = new ReplyRequest();
        }

        public ReplyInfoBuilder withId(String id) {
            replyRequest.id = id;
            return this;
        }

        public ReplyInfoBuilder withContent(String content) {
            replyRequest.content = content;
            return this;
        }

        public ReplyInfoBuilder withTicketId(String ticketId) {
            replyRequest.ticketId = ticketId;
            return this;
        }

        public ReplyInfoBuilder withUserId(String userId) {
            replyRequest.userId = userId;
            return this;
        }

        public ReplyInfoBuilder withUsername(String username) {
            replyRequest.username = username;
            return this;
        }

        public ReplyInfoBuilder withAdminName(String adminName) {
            replyRequest.adminName = adminName;
            return this;
        }

        public ReplyInfoBuilder withCreatedAt(OffsetDateTime createdAt) {
            replyRequest.createdAt = createdAt;
            return this;
        }

        public static ReplyInfoBuilder replyInfo() {
            return new ReplyInfoBuilder();
        }

        public ReplyRequest build() {
            return replyRequest;
        }
    }
}
