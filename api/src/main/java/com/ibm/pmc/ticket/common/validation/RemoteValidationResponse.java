package com.ibm.pmc.ticket.common.validation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Objects;

public class RemoteValidationResponse {
    private boolean isValid;

    private String value;

    public RemoteValidationResponse() {

    }

    private RemoteValidationResponse(Builder builder) {
        isValid = builder.isValid;
        value = builder.value;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @JsonProperty("isValid")
    public boolean isValid() {
        return isValid;
    }

    public String getValue() {
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
        RemoteValidationResponse that = (RemoteValidationResponse) obj;
        return Objects.equals(isValid, that.isValid)
                && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isValid, value);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("isValid", isValid)
                .add("value", value)
                .toString();
    }


    public static final class Builder {
        private boolean isValid;
        private String value;

        private Builder() {
        }

        public Builder withIsValid(boolean isValid) {
            this.isValid = isValid;
            return this;
        }

        public Builder withValue(String value) {
            this.value = value;
            return this;
        }

        public RemoteValidationResponse build() {
            return new RemoteValidationResponse(this);
        }
    }
}
