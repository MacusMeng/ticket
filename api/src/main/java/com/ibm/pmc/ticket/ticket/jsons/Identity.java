package com.ibm.pmc.ticket.ticket.jsons;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public class Identity {
    private String id;

    public Identity() {

    }

    private Identity(Builder builder) {
        id = builder.id;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identity that = (Identity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .toString();
    }

    public static final class Builder {
        private String id;

        private Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Identity build() {
            return new Identity(this);
        }
    }
}
