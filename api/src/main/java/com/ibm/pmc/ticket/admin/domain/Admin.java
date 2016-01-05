package com.ibm.pmc.ticket.admin.domain;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;
import org.hibernate.annotations.GenericGenerator;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "admins")
public class Admin implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "version")
    private int version;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private Set<AdminModule> modules = Sets.newHashSet();

    private Admin(Builder builder) {
        id = builder.id;
        username = builder.username;
        password = builder.password;
        email = builder.email;
        mobile = builder.mobile;
        version = builder.version;
        createdAt = builder.createdAt;
        updatedAt = builder.updatedAt;
        modules = builder.modules;
    }

    public Admin() {

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

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
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

    public Set<AdminModule> getModules() {
        return modules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(username, admin.username) &&
                Objects.equals(email, admin.email) &&
                Objects.equals(mobile, admin.mobile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, mobile);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("username", username)
                .add("password", password)
                .add("email", email)
                .add("mobile", mobile)
                .add("version", version)
                .add("createdAt", createdAt)
                .add("updatedAt", updatedAt)
                .toString();
    }


    public static final class Builder {
        private String id;
        private String username;
        private String password;
        private String email;
        private String mobile;
        private int version;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;
        private Set<AdminModule> modules;

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

        public Builder withPassword(String password) {
            this.password = BCrypt.hashpw(password, BCrypt.gensalt());
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withMobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public Builder withVersion(int version) {
            this.version = version;
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

        public Builder withModules(Set<AdminModule> modules) {
            this.modules = modules;
            return this;
        }

        public Admin build() {
            return new Admin(this);
        }
    }

    public void setModules(Set<AdminModule> modules) {
        this.modules = modules;
    }
}
