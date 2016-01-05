package com.ibm.pmc.ticket.common.jersey;

import com.ibm.pmc.ticket.admin.resource.AdminResource;
import com.ibm.pmc.ticket.common.validation.BadRequestExceptionMapper;
import com.ibm.pmc.ticket.common.validation.ConflictExceptionMapper;
import com.ibm.pmc.ticket.reply.resources.ReplyResource;
import com.ibm.pmc.ticket.ticket.resources.TicketResource;
import com.ibm.pmc.ticket.user.resources.UsersResource;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {

    /**
     * Constructor.
     */
    public JerseyConfig() {
        register(UsersResource.class);
        register(TicketResource.class);
        register(ReplyResource.class);
        register(AdminResource.class);
        register(TicketObjectMapperModulesProvider.class);
        register(OptionalParamFeature.class);
        register(MultiPartFeature.class);
        register(BadRequestExceptionMapper.class);
        register(ConflictExceptionMapper.class);
    }

}
