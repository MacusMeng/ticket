package com.ibm.pmc.ticket.reply.resources;

import com.ibm.pmc.ticket.common.AbstractResource;
import com.ibm.pmc.ticket.reply.jsons.ReplyRequest;
import com.ibm.pmc.ticket.reply.validators.ReplyRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.util.Optional;


@Path("/replies")
@Component
public class ReplyResource extends AbstractResource {
    private static final Logger logger = LoggerFactory.getLogger(ReplyResource.class);

    @Autowired
    private ReplyDelegateResource replyDelegateResource;

    @Autowired
    private ReplyRequestValidator replyRequestValidator;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void submit(ReplyRequest replyRequest) {
        replyRequestValidator.validate(replyRequest);
        replyDelegateResource.submit(replyRequest, Optional.of(currentAdmin()), Optional.empty(), currentLocale());
    }
}
