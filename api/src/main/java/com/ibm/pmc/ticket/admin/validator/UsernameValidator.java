package com.ibm.pmc.ticket.admin.validator;

import com.google.common.base.Strings;
import com.ibm.pmc.ticket.common.validation.*;
import com.ibm.pmc.ticket.common.validator.Validator;

import static com.ibm.pmc.ticket.common.validation.Error.INVALID_USERNAME;

public class UsernameValidator implements Validator<String> {
    @Override
    public void validate(String username) {
        if (Strings.isNullOrEmpty(username)
                || !username.matches("^[a-zA-Z_][a-zA-Z0-9_]{5,}$")
                || username.length() > 100) {
            throw new ValidateException(INVALID_USERNAME);
        }
    }
}
