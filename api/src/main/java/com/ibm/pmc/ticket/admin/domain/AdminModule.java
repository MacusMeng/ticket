package com.ibm.pmc.ticket.admin.domain;

import com.google.common.base.MoreObjects;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "admin_module_mapping")
public class AdminModule implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Column(name = "module")
    @Enumerated(EnumType.STRING)
    private Module module;

    public AdminModule() {

    }

    private AdminModule(Builder builder) {
        id = builder.id;
        admin = builder.admin;
        module = builder.module;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Admin getAdmin() {
        return admin;
    }

    public Module getModule() {
        return module;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminModule that = (AdminModule) o;
        return Objects.equals(admin.getId(), that.admin.getId()) &&
                Objects.equals(module, that.module);
    }

    @Override
    public int hashCode() {
        return Objects.hash(admin, module);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("admin", admin)
                .add("module", module)
                .toString();
    }

    public static final class Builder {
        private String id;
        private Admin admin;
        private Module module;

        private Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withAdmin(Admin admin) {
            this.admin = admin;
            return this;
        }

        public Builder withModule(Module module) {
            this.module = module;
            return this;
        }

        public AdminModule build() {
            return new AdminModule(this);
        }
    }
}
