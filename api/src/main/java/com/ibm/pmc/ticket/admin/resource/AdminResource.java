package com.ibm.pmc.ticket.admin.resource;

import com.ibm.pmc.ticket.admin.auth.AuthHandler;
import com.ibm.pmc.ticket.admin.domain.Admin;
import com.ibm.pmc.ticket.admin.jsons.CurrentAdmin;
import com.ibm.pmc.ticket.admin.jsons.LoginRequest;
import com.ibm.pmc.ticket.admin.jsons.RegisterRequest;
import com.ibm.pmc.ticket.admin.services.AdminService;
import com.ibm.pmc.ticket.admin.validator.LoginRequestValidator;
import com.ibm.pmc.ticket.admin.validator.RegisterRequestValidator;
import com.ibm.pmc.ticket.common.AbstractResource;
import com.ibm.pmc.ticket.common.filter.AdminAuthenticationFilter;
import com.ibm.pmc.ticket.common.validation.ConflictException;
import com.ibm.pmc.ticket.common.validation.Error;
import com.ibm.pmc.ticket.common.validation.ValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/admins")
@Component
public class AdminResource extends AbstractResource {
    private static final Logger logger = LoggerFactory.getLogger(AdminResource.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthHandler authHandler;

    @Autowired
    private LoginRequestValidator loginRequestValidator;

    @Autowired
    private RegisterRequestValidator registerRequestValidator;

    /**
     * Get current logon user.
     *
     * @param httpServletRequest HttpServletRequest
     * @return a user
     */
    @GET
    @Path("/current-admin")
    @Produces(MediaType.APPLICATION_JSON)
    public CurrentAdmin getCurrentAdmin(@Context HttpServletRequest httpServletRequest) {
        Admin admin = (Admin) httpServletRequest.getAttribute(AdminAuthenticationFilter.LOGON_ADMIN);
        return CurrentAdmin.newBuilder()
                .withId(admin.getId())
                .withUsername(admin.getUsername())
                .withEmail(admin.getEmail())
                .build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public void login(LoginRequest loginRequest,
                      @Context HttpServletRequest request,
                      @Context HttpServletResponse response) {
        loginRequestValidator.validate(loginRequest);

        Optional<Admin> adminOptional = adminService.login(loginRequest);
        Admin admin = adminOptional
                .orElseThrow(() -> new ValidateException(Error.INVALID_USERNAME_OR_PASSWORD));
        response.addCookie(authHandler.toCookie(admin, request.getContextPath()));
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public void register(RegisterRequest registerRequest,
                         @Context HttpServletRequest request,
                         @Context HttpServletResponse response) {
        registerRequestValidator.validate(registerRequest);
        Admin admin;
        try {
            admin = adminService.register(registerRequest);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(Error.DUPLICATE_USERNAME_OR_EMAIL);
        }
        response.addCookie(authHandler.toCookie(admin, request.getContextPath()));
    }

    @POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public void logout(@Context HttpServletRequest request,
                       @Context HttpServletResponse response) {
        response.addCookie(authHandler.toExpiredCookie(request));
    }
}
