package com.ibm.pmc.ticket.common.validator;

public interface Validator<T> {
    void validate(T t);
}
