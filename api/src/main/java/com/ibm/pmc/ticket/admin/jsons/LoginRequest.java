package com.ibm.pmc.ticket.admin.jsons;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.Objects;

public class LoginRequest implements Serializable {
    private String usernameOrEmail;

    private String password;

    public LoginRequest() {

    }

    private LoginRequest(Builder builder) {
        usernameOrEmail = builder.usernameOrEmail;
        password = builder.password;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginRequest that = (LoginRequest) o;
        return Objects.equals(usernameOrEmail, that.usernameOrEmail) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usernameOrEmail, password);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("usernameOrEmail", usernameOrEmail)
                .add("password", password)
                .toString();
    }

    public static final class Builder {
        private String usernameOrEmail;
        private String password;

        private Builder() {
        }

        public Builder withUsernameOrEmail(String usernameOrEmail) {
            this.usernameOrEmail = usernameOrEmail;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public LoginRequest build() {
            return new LoginRequest(this);
        }
    }
}
