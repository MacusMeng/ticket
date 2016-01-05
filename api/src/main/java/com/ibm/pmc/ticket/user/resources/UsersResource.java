package com.ibm.pmc.ticket.user.resources;

import com.ibm.pmc.ticket.common.AbstractResource;
import com.ibm.pmc.ticket.common.filter.UserAuthenticationFilter;
import com.ibm.pmc.ticket.common.validation.NumberValidation;
import com.ibm.pmc.ticket.reply.jsons.ReplyRequest;
import com.ibm.pmc.ticket.reply.resources.ReplyDelegateResource;
import com.ibm.pmc.ticket.reply.validators.ReplyRequestValidator;
import com.ibm.pmc.ticket.ticket.domains.Status;
import com.ibm.pmc.ticket.ticket.domains.Ticket;
import com.ibm.pmc.ticket.ticket.jsons.Identity;
import com.ibm.pmc.ticket.ticket.jsons.TicketCreateRequest;
import com.ibm.pmc.ticket.ticket.jsons.TicketInfo;
import com.ibm.pmc.ticket.ticket.resources.TicketDelegateResource;
import com.ibm.pmc.ticket.ticket.services.TicketsService;
import com.ibm.pmc.ticket.ticket.validators.TicketCreateRequestValidator;
import com.ibm.pmc.ticket.user.transferdomains.User;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.EnumSet;
import java.util.Optional;

@Path("/users")
@Component
public class UsersResource extends AbstractResource {
    private static final Logger logger = LoggerFactory.getLogger(UsersResource.class);

    @Autowired
    private TicketsService ticketsService;

    @Autowired
    private TicketDelegateResource ticketDelegateResource;

    @Autowired
    private ReplyDelegateResource replyDelegateResource;

    @Autowired
    private TicketCreateRequestValidator ticketCreateRequestValidator;

    @Autowired
    private ReplyRequestValidator replyRequestValidator;
    @Autowired
    private NumberValidation numberValidation;

    /**
     * Get current logon user.
     *
     * @param httpServletRequest HttpServletRequest
     * @return a user
     */
    @GET
    @Path("/current-user")
    @Produces(MediaType.APPLICATION_JSON)
    public User getCurrentUser(@Context HttpServletRequest httpServletRequest) {
        return (User) httpServletRequest.getAttribute(UserAuthenticationFilter.LOGON_USER);
    }


    @GET
    @Path("/user-tickets")
    @Produces(MediaType.APPLICATION_JSON)
    public Page<Ticket> queryTickets(@QueryParam("status") Optional<String> status,
                                     @QueryParam("number") Optional<Long> number,
                                     @QueryParam("page") Integer page,
                                     @QueryParam("pageSize") Integer pageSize) {
        if(number.isPresent()){
            numberValidation.validate(Long.toString(number.get()));
        }
        return ticketDelegateResource.queryTickets(status,
                number,
                page,
                pageSize,
                currentAdmin(),
                currentUser());
    }

    @POST
    @Path("/user-tickets/identity")
    @Produces(MediaType.APPLICATION_JSON)
    public Identity generateTicketIdentity() {
        return ticketDelegateResource.generateTicketIdentity();
    }

    @POST
    @Path("/user-tickets/files")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void upload(@FormDataParam("file") InputStream inputStream,
                       @FormDataParam("ticketId") String ticketId,
                       @FormDataParam("fileName") String fileName) {
        ticketDelegateResource.upload(inputStream,
                ticketId,
                fileName);
    }

    @POST
    @Path("/user-tickets")
    @Consumes(MediaType.APPLICATION_JSON)
    public void submitTicket(TicketCreateRequest ticketCreateRequest) {
        ticketCreateRequestValidator.validate(ticketCreateRequest);
        ticketDelegateResource.submit(ticketCreateRequest, currentUser());
    }

    @POST
    @Path("/user-replies")
    @Consumes(MediaType.APPLICATION_JSON)
    public void submitReply(ReplyRequest replyRequest) {
        replyRequestValidator.validate(replyRequest);
        replyDelegateResource.submit(replyRequest, Optional.empty(), Optional.of(currentUser()), currentLocale());
    }

    @GET
    @Path("/user-tickets/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public TicketInfo queryTicketInformation(@PathParam("id") String id) {
        return ticketDelegateResource.queryTicketInfo(id);
    }

    @PUT
    @Path("/user-tickets/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void updateTicket(@PathParam("id") String id) {
        ticketsService.updateTicketStatus(id, Status.CLOSED);
    }

    @DELETE
    @Path("/user-tickets/{id}/files/{fileName}/delete")
    public void delete(@PathParam("id") String id,
                       @PathParam("fileName") String fileName) {
        ticketDelegateResource.delete(id, fileName);
    }

    @GET
    @Path("/user-tickets/{id}/files/{fileName}/get")
    public void findFile(@PathParam("id") String id,
                         @PathParam("fileName") String fileName,
                         @Context HttpServletResponse response) {
        ticketDelegateResource.findFile(id, fileName, response);
    }

    @GET
    @Path("/user-tickets/{id}/files/{fileName}/download")
    public void downloadFile(@PathParam("id") String id,
                             @PathParam("fileName") String fileName,
                             @Context HttpServletResponse response) {
        ticketDelegateResource.downloadFile(id, fileName, response);
    }

}
