package com.ibm.pmc.ticket.common.filter;

import com.ibm.pmc.ticket.admin.auth.AuthHandler;
import com.ibm.pmc.ticket.admin.auth.AuthToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AdminAuthenticationFilter extends IgnorableFilter {
    private static Logger logger = LoggerFactory.getLogger(AdminAuthenticationFilter.class);

    public static final String LOGON_ADMIN = "LOGON_ADMIN";

    @Autowired
    private AuthHandler authHandler;

    @Value("${cookie.timeout.minutes}")
    private int cookieTimeoutMinutes;

    public AdminAuthenticationFilter() {
        this.ignore = new String[]{
                "/api/users/.*$",
                "/api/users$",
                "/api/admins/login",
                "/api/admins/register"
        };
    }

    /**
     * Parse request and set logon user in request scope.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param chain    FilterChain
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (isIgnored(httpServletRequest)) {
            logger.info("Ignore request by adminAuthenticationFilter");
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        AuthToken authToken = authHandler.toAuthToken(httpServletRequest);
        Boolean loggedIn = authToken.isLoggedIn(cookieTimeoutMinutes);

        if (!loggedIn) {
            unauthenticated(httpServletResponse);
            return;
        } else {
            authenticated(httpServletRequest, httpServletResponse, authToken);
        }

        chain.doFilter(request, response);
    }

    private void unauthenticated(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.flushBuffer();
    }

    private void authenticated(HttpServletRequest request, HttpServletResponse response, AuthToken authToken) {
        request.setAttribute(LOGON_ADMIN, authToken.getUserOptional().get());
        response.addCookie(authHandler.toCookie(authToken.getUserOptional().get(), request.getContextPath()));
    }
}