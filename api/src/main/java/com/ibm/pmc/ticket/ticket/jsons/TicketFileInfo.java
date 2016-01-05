package com.ibm.pmc.ticket.ticket.jsons;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.Objects;

public class TicketFileInfo implements Serializable {
    private String fileName;

    private String contentType;

    private TicketFileInfo(Builder builder) {
        fileName = builder.fileName;
        contentType = builder.contentType;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketFileInfo that = (TicketFileInfo) o;
        return Objects.equals(fileName, that.fileName) &&
                Objects.equals(contentType, that.contentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, contentType);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("fileName", fileName)
                .add("contentType", contentType)
                .toString();
    }

    public static final class Builder {
        private String fileName;
        private String contentType;

        private Builder() {
        }

        public Builder withFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder withContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public TicketFileInfo build() {
            return new TicketFileInfo(this);
        }
    }
}
