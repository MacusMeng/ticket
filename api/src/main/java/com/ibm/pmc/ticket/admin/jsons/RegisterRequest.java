package com.ibm.pmc.ticket.admin.jsons;

import com.google.common.base.MoreObjects;
import com.ibm.pmc.ticket.admin.domain.Module;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class RegisterRequest implements Serializable {
    private String username;

    private String password;

    private String email;

    private Set<Module> modules;

    public RegisterRequest() {

    }

    private RegisterRequest(Builder builder) {
        username = builder.username;
        password = builder.password;
        email = builder.email;
        modules = builder.modules;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Set<Module> getModules() {
        return modules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterRequest that = (RegisterRequest) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(email, that.email) &&
                Objects.equals(modules, that.modules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email, modules);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("username", username)
                .add("password", password)
                .add("email", email)
                .add("modules", modules)
                .toString();
    }

    public static final class Builder {
        private String username;
        private String password;
        private String email;
        private Set<Module> modules;

        private Builder() {
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withModules(Set<Module> modules) {
            this.modules = modules;
            return this;
        }

        public RegisterRequest build() {
            return new RegisterRequest(this);
        }
    }
}
