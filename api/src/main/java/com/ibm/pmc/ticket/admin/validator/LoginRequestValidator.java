package com.ibm.pmc.ticket.admin.validator;

import com.google.common.base.Strings;
import com.ibm.pmc.ticket.admin.jsons.LoginRequest;
import com.ibm.pmc.ticket.common.validation.ValidateException;
import com.ibm.pmc.ticket.common.validator.Validator;
import org.springframework.stereotype.Component;

import static com.ibm.pmc.ticket.common.validation.Error.*;

@Component
public class LoginRequestValidator implements Validator<LoginRequest> {

    @Override
    public void validate(LoginRequest loginRequest) {
        if (Strings.isNullOrEmpty(loginRequest.getUsernameOrEmail())) {
            throw new ValidateException(USERNAME_OR_EMAIL_REQUIRED);
        }

        if (Strings.isNullOrEmpty(loginRequest.getPassword())) {
            throw new ValidateException(PASSWORD_REQUIRED);
        }

        if (loginRequest.getUsernameOrEmail().length() > 100) {
            throw new ValidateException(USERNAME_OR_EMAIL_LARGE_THAN_100);
        }

        if (loginRequest.getPassword().length() > 100) {
            throw new ValidateException(PASSWORD_LARGE_THAN_100);
        }
    }
}
