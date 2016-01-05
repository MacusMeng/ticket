package com.ibm.pmc.ticket.ticket.resources;

import com.ibm.pmc.ticket.common.AbstractResource;
import com.ibm.pmc.ticket.common.validation.NumberValidation;
import com.ibm.pmc.ticket.ticket.domains.Status;
import com.ibm.pmc.ticket.ticket.domains.Ticket;
import com.ibm.pmc.ticket.ticket.jsons.Identity;
import com.ibm.pmc.ticket.ticket.jsons.TicketCreateRequest;
import com.ibm.pmc.ticket.ticket.jsons.TicketInfo;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.EnumSet;
import java.util.Optional;

@Path("/tickets")
@Component
public class TicketResource extends AbstractResource {
    private static final Logger logger = LoggerFactory.getLogger(TicketResource.class);

    @Autowired
    private TicketDelegateResource ticketDelegateResource;
    @Autowired
    private NumberValidation numberValidation;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Page<Ticket> queryTickets(@QueryParam("status") Optional<String> status,
                                     @QueryParam("number") Optional<Long> number,
                                     @QueryParam("page") Integer page,
                                     @QueryParam("pageSize") Integer pageSize) {
        if(number.isPresent()){
            numberValidation.validate(Long.toString(number.get()));
        }
        return ticketDelegateResource.queryTickets(status,
                number, page, pageSize, currentAdmin(), currentUser());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public TicketInfo queryTicketInformation(@PathParam("id") String id) {
        return ticketDelegateResource.queryTicketInfo(id);
    }

    @POST
    @Path("/identity")
    @Produces(MediaType.APPLICATION_JSON)
    public Identity generateTicketIdentity() {
        return ticketDelegateResource.generateTicketIdentity();
    }

    @POST
    @Path("/files")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void upload(@FormDataParam("file") InputStream inputStream,
                       @FormDataParam("ticketId") String ticketId,
                       @FormDataParam("fileName") String fileName) {
        ticketDelegateResource.upload(inputStream, ticketId, fileName);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void submit(TicketCreateRequest ticketCreateRequest) {
        ticketDelegateResource.submit(ticketCreateRequest, currentUser());
    }


    @DELETE
    @Path("/{id}/files/{fileName}/delete")
    public void delete(@PathParam("id") String id,
                       @PathParam("fileName") String fileName) {
        ticketDelegateResource.delete(id, fileName);
    }

    @GET
    @Path("/{id}/files/{fileName}/get")
    public void findFile(@PathParam("id") String id,
                         @PathParam("fileName") String fileName,
                         @Context HttpServletResponse response) {
        ticketDelegateResource.findFile(id, fileName, response);
    }

    @GET
    @Path("/{id}/files/{fileName}/download")
    public void downloadFile(@PathParam("id") String id,
                             @PathParam("fileName") String fileName,
                             @Context HttpServletResponse response) {
        ticketDelegateResource.downloadFile(id, fileName, response);
    }
}