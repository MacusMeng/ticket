package com.ibm.pmc.ticket.common.mail;

import com.google.common.base.MoreObjects;
import com.ibm.pmc.ticket.notification.domain.Notification;

import java.util.Locale;
import java.util.Objects;

public class MailEvent {
    private Locale locale;

    private Notification notification;

    private MailEvent(Builder builder) {
        locale = builder.locale;
        notification = builder.notification;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Locale getLocale() {
        return locale;
    }

    public Notification getNotification() {
        return notification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailEvent mailEvent = (MailEvent) o;
        return Objects.equals(locale, mailEvent.locale) &&
                Objects.equals(notification, mailEvent.notification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locale, notification);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("locale", locale)
                .add("notification", notification)
                .toString();
    }

    public static final class Builder {
        private Locale locale;
        private Notification notification;

        private Builder() {
        }

        public Builder withLocale(Locale locale) {
            this.locale = locale;
            return this;
        }

        public Builder withNotification(Notification notification) {
            this.notification = notification;
            return this;
        }

        public MailEvent build() {
            return new MailEvent(this);
        }
    }
}
