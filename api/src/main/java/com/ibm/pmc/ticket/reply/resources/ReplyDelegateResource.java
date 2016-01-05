package com.ibm.pmc.ticket.reply.resources;

import com.ibm.pmc.ticket.admin.domain.Admin;
import com.ibm.pmc.ticket.reply.domains.Reply;
import com.ibm.pmc.ticket.reply.jsons.ReplyRequest;
import com.ibm.pmc.ticket.reply.services.ReplyService;
import com.ibm.pmc.ticket.reply.validators.ReplyCloseValidator;
import com.ibm.pmc.ticket.ticket.domains.Ticket;
import com.ibm.pmc.ticket.ticket.services.TicketsService;
import com.ibm.pmc.ticket.user.transferdomains.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Locale;
import java.util.Optional;

@Component
public class ReplyDelegateResource {

    @Autowired
    private TicketsService ticketsService;

    @Autowired
    private ReplyService replyService;


    @Autowired
    private ReplyCloseValidator replyCloseValidator;

    public void submit(ReplyRequest replyRequest,
                       Optional<Admin> currentAdminOpt,
                       Optional<User> currentUserOpt,
                       Locale locale) {
        Ticket ticket = ticketsService.findTicketById(replyRequest.getTicketId());

        Reply reply = getReply(replyRequest, currentAdminOpt, currentUserOpt, ticket);
        replyService.addReply(reply, locale);
    }

    private Reply getReply(ReplyRequest replyRequest, Optional<Admin> currentAdminOpt, Optional<User> currentUserOpt, Ticket ticket) {
        if (currentAdminOpt.isPresent()) {
            replyCloseValidator.validate(ticket);
            Admin admin = currentAdminOpt.get();
            return Reply.ReplyBuilder.replies()
                    .withAdmin(Admin.newBuilder()
                            .withId(admin.getId())
                            .withModules(admin.getModules())
                            .withUsername(admin.getUsername())
                            .withCreatedAt(admin.getCreatedAt())
                            .withEmail(admin.getEmail())
                            .withMobile(admin.getMobile())
                            .withPassword(admin.getPassword())
                            .withUpdatedAt(admin.getUpdatedAt())
                            .build())
                    .withContent(replyRequest.getContent())
                    .withTicket(ticket)
                    .withCreatedAt(OffsetDateTime.now())
                    .build();
        } else {
            User user = currentUserOpt.get();
            return Reply.ReplyBuilder.replies()
                    .withUserId(user.getUserId())
                    .withUsername(user.getUsername())
                    .withContent(replyRequest.getContent())
                    .withTicket(ticket)
                    .withCreatedAt(OffsetDateTime.now())
                    .build();
        }
    }
}
