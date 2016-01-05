package com.ibm.pmc.ticket.admin.auth;

import com.ibm.pmc.ticket.admin.domain.Admin;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.Optional;

public class AuthToken implements Serializable {
    private Optional<Admin> userOptional;

    private LocalDateTime lastOperationAt;

    public static final AuthToken EMPTY_AUTH_TOKEN;

    static {
        EMPTY_AUTH_TOKEN = new AuthToken(Optional.empty(), LocalDateTime.ofInstant(Instant.EPOCH, ZoneId.systemDefault()));
    }

    public AuthToken(Optional<Admin> userOptional, LocalDateTime lastOperationAt) {
        this.userOptional = userOptional;
        this.lastOperationAt = lastOperationAt;
    }

    public Optional<Admin> getUserOptional() {
        return userOptional;
    }

    public boolean isLoggedIn(int cookieTimeoutMinutes) {
        return userOptional.isPresent() && !isTimeout(cookieTimeoutMinutes);
    }

    public Long getLastOperationTime() {
        return lastOperationAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public boolean causedByTimeout(int cookieTimeoutMinutes) {
        return userOptional.isPresent() && isTimeout(cookieTimeoutMinutes);
    }

    private boolean isTimeout(int cookieTimeoutMinutes) {
        return cookieTimeoutMinutes >= 0 && lastOperationAt.plusMinutes(cookieTimeoutMinutes).isBefore(LocalDateTime.now());
    }
}