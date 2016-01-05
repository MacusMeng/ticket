package com.ibm.pmc.ticket.common.validator;


import com.ibm.pmc.ticket.admin.validator.PasswordValidator;
import com.ibm.pmc.ticket.admin.validator.UsernameValidator;

public class Validators {

    public static Validator<String> newPasswordValidator() {
        return new PasswordValidator();
    }

    public static Validator<String> newUsernameValidator() {
        return new UsernameValidator();
    }

}
