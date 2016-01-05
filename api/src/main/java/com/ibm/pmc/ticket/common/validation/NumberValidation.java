package com.ibm.pmc.ticket.common.validation;

import com.ibm.pmc.ticket.common.validator.Validator;
import org.springframework.stereotype.Component;

import static com.ibm.pmc.ticket.common.validation.Error.TICKET_INPUT_ERROR;
@Component
public class NumberValidation implements Validator<String> {

    @Override
    public void validate(String s) {
        if (!s.matches("^[0-9]+$")) {
            throw new ValidateException(TICKET_INPUT_ERROR);
        }
    }
}
