package com.ibm.pmc.ticket.reply.validators;

import com.ibm.pmc.ticket.common.validation.ValidateException;
import com.ibm.pmc.ticket.common.validator.Validator;
import com.ibm.pmc.ticket.ticket.domains.Status;
import com.ibm.pmc.ticket.ticket.domains.Ticket;
import org.springframework.stereotype.Component;

import static com.ibm.pmc.ticket.common.validation.Error.TICKET_INPUT_CLOSED;

@Component
public class ReplyCloseValidator implements Validator<Ticket> {
    @Override
    public void validate(Ticket ticket) {
        if(ticket.getStatus().equals(Status.CLOSED)){
            throw new ValidateException(TICKET_INPUT_CLOSED);
        }
    }
}
