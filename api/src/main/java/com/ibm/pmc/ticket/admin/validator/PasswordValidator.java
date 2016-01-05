package com.ibm.pmc.ticket.admin.validator;

import com.google.common.base.Strings;
import com.ibm.pmc.ticket.common.validation.ValidateException;
import com.ibm.pmc.ticket.common.validator.Validator;

import static com.ibm.pmc.ticket.common.validation.Error.INVALID_PASSWORD;

public class PasswordValidator implements Validator<String> {
    @Override
    public void validate(String s) {
        if (Strings.isNullOrEmpty(s) || s.length() < 8 || s.matches("[0-9]+") || s.length() > 100) {
            throw new ValidateException(INVALID_PASSWORD);
        }
    }
}
