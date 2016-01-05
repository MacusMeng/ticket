package com.ibm.pmc.ticket.common.validation;

import com.google.common.collect.ImmutableMap;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static com.ibm.pmc.ticket.common.validation.Error.UNKNOWN_ERROR;

@Provider
public class ConflictExceptionMapper implements ExceptionMapper<ConflictException> {
    @Override
    public Response toResponse(ConflictException exception) {
        return Response.status(Response.Status.CONFLICT)
                .entity(ImmutableMap.of("message",
                        (null == exception) ? UNKNOWN_ERROR.toString() : exception.getMessage()))
                .type(MediaType.APPLICATION_JSON).build();
    }
}
