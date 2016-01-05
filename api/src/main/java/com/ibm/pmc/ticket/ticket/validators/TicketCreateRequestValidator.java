package com.ibm.pmc.ticket.ticket.validators;

import com.google.common.base.Strings;
import com.ibm.pmc.ticket.common.validation.ValidateException;
import com.ibm.pmc.ticket.common.validator.Validator;
import com.ibm.pmc.ticket.ticket.jsons.TicketCreateRequest;
import org.springframework.stereotype.Component;

import static com.ibm.pmc.ticket.common.validation.Error.*;

@Component
public class TicketCreateRequestValidator implements Validator<TicketCreateRequest> {
    @Override
    public void validate(TicketCreateRequest ticketCreateRequest) {
        if (Strings.isNullOrEmpty(ticketCreateRequest.getTitle())) {
            throw new ValidateException(TICKET_TITLE_REQUIRED);
        }

        if (ticketCreateRequest.getTitle().length() > 255) {
            throw new ValidateException(TICKET_TITLE_LARGE_THAN_255);
        }

        if (null == ticketCreateRequest.getModule()) {
            throw new ValidateException(TICKET_MODULE_REQUIRED);
        }

        if (Strings.isNullOrEmpty(ticketCreateRequest.getContent())) {
            throw new ValidateException(TICKET_CONTENT_REQUIRED);
        }
    }
}
