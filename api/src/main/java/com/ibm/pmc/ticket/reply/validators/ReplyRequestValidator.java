package com.ibm.pmc.ticket.reply.validators;

import com.google.common.base.Strings;
import com.ibm.pmc.ticket.common.validation.ValidateException;
import com.ibm.pmc.ticket.common.validator.Validator;
import com.ibm.pmc.ticket.reply.jsons.ReplyRequest;
import org.springframework.stereotype.Component;

import static com.ibm.pmc.ticket.common.validation.Error.REPLY_CONTENT_REQUIRED;

@Component
public class ReplyRequestValidator implements Validator<ReplyRequest> {
    @Override
    public void validate(ReplyRequest replyRequest) {
        if (Strings.isNullOrEmpty(replyRequest.getContent())) {
            throw new ValidateException(REPLY_CONTENT_REQUIRED);
        }
    }
}
