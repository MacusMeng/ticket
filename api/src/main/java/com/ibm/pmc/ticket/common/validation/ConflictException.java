package com.ibm.pmc.ticket.common.validation;

public class ConflictException extends RuntimeException {
    public ConflictException(Error error) {
        super(error.toString());
    }
}
