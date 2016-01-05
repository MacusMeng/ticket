package com.ibm.pmc.ticket.ticket.jsons;

import com.google.common.base.MoreObjects;
import com.ibm.pmc.ticket.admin.domain.Module;

import java.io.Serializable;
import java.util.Objects;

public class TicketCreateRequest implements Serializable {
    private String id;

    private String title;

    private Module module;

    private String content;

    public TicketCreateRequest() {

    }

    private TicketCreateRequest(Builder builder) {
        id = builder.id;
        title = builder.title;
        module = builder.module;
        content = builder.content;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getTitle() {
        return title;
    }

    public Module getModule() {
        return module;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketCreateRequest that = (TicketCreateRequest) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(module, that.module) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, module, content);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("title", title)
                .add("module", module)
                .add("content", content)
                .toString();
    }

    public static final class Builder {
        private String id;
        private String title;
        private Module module;
        private String content;

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

        public Builder withModule(Module module) {
            this.module = module;
            return this;
        }

        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public TicketCreateRequest build() {
            return new TicketCreateRequest(this);
        }
    }
}
