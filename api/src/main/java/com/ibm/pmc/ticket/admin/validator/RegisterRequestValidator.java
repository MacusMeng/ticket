package com.ibm.pmc.ticket.admin.validator;

import com.ibm.pmc.ticket.admin.jsons.RegisterRequest;
import com.ibm.pmc.ticket.common.validation.Error;
import com.ibm.pmc.ticket.common.validation.ValidateException;
import com.ibm.pmc.ticket.common.validator.Validator;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

@Component
public class RegisterRequestValidator implements Validator<RegisterRequest> {
    @Override
    public void validate(RegisterRequest registerRequest) {
        new UsernameValidator().validate(registerRequest.getUsername());
        new PasswordValidator().validate(registerRequest.getPassword());

        if (!EmailValidator.getInstance().isValid(registerRequest.getEmail()) || registerRequest.getEmail().length() > 100) {
            throw new ValidateException(Error.INVALID_EMAIL_ADDRESS);
        }

        if (null == registerRequest.getModules() || registerRequest.getModules().isEmpty()) {
            throw new ValidateException(Error.MODULES_REQUIRED);
        }
    }
}
