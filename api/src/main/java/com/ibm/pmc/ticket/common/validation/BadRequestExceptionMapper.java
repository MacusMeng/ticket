package com.ibm.pmc.ticket.common.validation;

import com.google.common.collect.ImmutableMap;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static com.ibm.pmc.ticket.common.validation.Error.UNKNOWN_ERROR;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ImmutableMap.of("message",
                        (null == exception) ? UNKNOWN_ERROR.toString() : exception.getMessage()))
                .type(MediaType.APPLICATION_JSON).build();
    }
}
