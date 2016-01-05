package com.ibm.pmc.ticket.ticket.jsons;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.ibm.pmc.ticket.admin.domain.Module;
import com.ibm.pmc.ticket.reply.jsons.ReplyRequest;
import com.ibm.pmc.ticket.ticket.domains.Status;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

public class TicketInfo {
    private String id;

    private String title;

    private String content;

    private long number;

    private Module module;

    private Status status;

    private String userId;

    private String username;

    private String adminId;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private List<ReplyRequest> replies = Lists.newArrayList();

    private List<TicketFileInfo> fileNames = Lists.newArrayList();

    private TicketInfo(Builder builder) {
        id = builder.id;
        title = builder.title;
        content = builder.content;
        number = builder.number;
        module = builder.module;
        status = builder.status;
        userId = builder.userId;
        username = builder.username;
        adminId = builder.adminId;
        createdAt = builder.createdAt;
        updatedAt = builder.updatedAt;
        replies = builder.replies;
        fileNames = builder.fileNames;
    }

    public static Builder newBuilder() {
        return new Builder();
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
                .add("adminId", adminId)
                .add("createdAt", createdAt)
                .add("updatedAt", updatedAt)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketInfo that = (TicketInfo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

    public long getNumber() {
        return number;
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

    public String getAdminId() {
        return adminId;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<ReplyRequest> getReplies() {
        return replies;
    }

    public List<TicketFileInfo> getFileNames() {
        return fileNames;
    }

    public static final class Builder {
        private String id;
        private String title;
        private String content;
        private long number;
        private Module module;
        private Status status;
        private String userId;
        private String username;
        private String adminId;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;
        private List<ReplyRequest> replies;
        private List<TicketFileInfo> fileNames;

        private Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public Builder withNumber(long number) {
            this.number = number;
            return this;
        }

        public Builder withModule(Module module) {
            this.module = module;
            return this;
        }

        public Builder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withAdminId(String adminId) {
            this.adminId = adminId;
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

        public Builder withReplies(List<ReplyRequest> replies) {
            this.replies = replies;
            return this;
        }

        public Builder withFileNames(List<TicketFileInfo> fileNames) {
            this.fileNames = fileNames;
            return this;
        }

        public TicketInfo build() {
            return new TicketInfo(this);
        }
    }
}
