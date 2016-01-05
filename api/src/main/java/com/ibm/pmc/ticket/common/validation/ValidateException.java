package com.ibm.pmc.ticket.common.validation;

public class ValidateException extends RuntimeException {
    public ValidateException(Error error) {
        super(error.name());
    }
}
