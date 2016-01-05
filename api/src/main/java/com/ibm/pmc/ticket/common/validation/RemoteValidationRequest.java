package com.ibm.pmc.ticket.common.validation;

import com.google.common.base.MoreObjects;

import java.util.Objects;
import java.util.Optional;

public class RemoteValidationRequest {
    private Optional<String> value;

    public RemoteValidationRequest() {

    }

    private RemoteValidationRequest(Builder builder) {
        value = builder.value;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Optional<String> getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RemoteValidationRequest that = (RemoteValidationRequest) obj;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value)
                .toString();
    }

    public static final class Builder {
        private Optional<String> value;

        private Builder() {
        }

        public Builder withValue(Optional<String> value) {
            this.value = value;
            return this;
        }

        public RemoteValidationRequest build() {
            return new RemoteValidationRequest(this);
        }
    }
}
