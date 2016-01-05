package com.ibm.pmc.ticket.user.transferdomains;

import com.google.common.base.MoreObjects;

import java.util.Objects;


public class User {
    private String userId;

    private String username;

    private String email;

    private String language;

    public User() {

    }

    private User(Builder builder) {
        userId = builder.userId;
        username = builder.username;
        email = builder.email;
        language = builder.language;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(username, user.username) &&
                Objects.equals(email, user.email) &&
                Objects.equals(language, user.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, email, language);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("username", username)
                .add("email", email)
                .add("language", language)
                .toString();
    }

    public static User empty() {
        return User.newBuilder().build();
    }

    public boolean isEmpty() {
        return this.equals(empty());
    }

    public static final class Builder {
        private String userId;
        private String username;
        private String email;
        private String language;

        private Builder() {
        }

        public Builder withUserId(String userId) {
            this.userId = userId;
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

        public Builder withLanguage(String language) {
            this.language = language;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
