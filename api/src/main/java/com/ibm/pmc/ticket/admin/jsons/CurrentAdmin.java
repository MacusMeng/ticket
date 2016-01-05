package com.ibm.pmc.ticket.admin.jsons;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.Objects;

public class CurrentAdmin implements Serializable{
    private String id;

    private String username;

    private String email;

    private CurrentAdmin(Builder builder) {
        id = builder.id;
        username = builder.username;
        email = builder.email;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrentAdmin that = (CurrentAdmin) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(username, that.username) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("username", username)
                .add("email", email)
                .toString();
    }

    public static final class Builder {
        private String id;
        private String username;
        private String email;

        private Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public CurrentAdmin build() {
            return new CurrentAdmin(this);
        }
    }
}
