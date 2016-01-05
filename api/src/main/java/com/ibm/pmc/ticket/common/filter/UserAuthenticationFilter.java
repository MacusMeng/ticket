package com.ibm.pmc.ticket.common.filter;

import com.ibm.pmc.ticket.user.transferdomains.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Component
public class UserAuthenticationFilter extends IgnorableFilter {
    private static Logger logger = LoggerFactory.getLogger(UserAuthenticationFilter.class);

    @Value("${test.user.id}")
    private String testUserId;

    @Value("${test.user.username}")
    private String testUsername;

    @Value("${test.user.email}")
    private String testUserEmail;

    @Value("${test.user.enabled}")
    private String testUserEnabled;

    private static final String USER_ID = "user_id";

    public static final String LOGON_USER = "LOGON_USER";

    public UserAuthenticationFilter() {
        this.ignore = new String[]{};
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
            logger.info("Ignore request by userAuthenticationFilter");
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        Optional<String> userIdOpt = getUserId(httpServletRequest);

        Optional<User> userOpt = userIdOpt
                .map(userId ->
                        Optional.of(User.newBuilder()
                                .withEmail(userId)
                                .withUserId(userId)
                                .withUsername(userId)
                                .build()))
                .orElseGet(() -> {
                    if (Boolean.valueOf(testUserEnabled)) {
                        User user = User.newBuilder()
                                .withEmail(testUserEmail)
                                .withUserId(testUserId)
                                .withUsername(testUsername)
                                .build();
                        return Optional.of(user);
                    } else {
                        return Optional.empty();
                    }
                });

        if (hasLogon(userOpt)) {
            authenticated(httpServletRequest, userOpt);
        } else {
            unauthenticated(httpServletResponse);
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean hasLogon(Optional<User> userOpt) {
        return userOpt.isPresent();
    }

    private void authenticated(HttpServletRequest request, Optional<User> userOpt) {
        request.setAttribute(LOGON_USER, userOpt.get());
    }

    private void unauthenticated(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.flushBuffer();
    }

    private Optional<String> getUserId(HttpServletRequest request) {
        Optional<Cookie[]> cookiesOpt = ofNullable(request.getCookies());

        if (!cookiesOpt.isPresent()) {
            return Optional.empty();
        }

        for (Cookie cookie : cookiesOpt.get()) {
            if (USER_ID.equals(cookie.getName())) {
                try {
                    return Optional.of(new EncryptDecrypt().decryptParam(cookie.getValue()));
                } catch (Exception e) {
                    logger.error("Decrypt cookie user exception", e);
                    return Optional.empty();
                }
            }
        }

        return Optional.empty();
    }

}