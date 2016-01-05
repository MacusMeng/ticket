package com.ibm.pmc.ticket.common.filter;

import com.ibm.pmc.ticket.user.transferdomains.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static com.ibm.pmc.ticket.common.filter.UserAuthenticationFilter.LOGON_USER;
import static java.util.Optional.ofNullable;

@Component
public class ExternalLanguageFilter extends IgnorableFilter {
    private static Logger logger = LoggerFactory.getLogger(UserAuthenticationFilter.class);

    private static final String EXTERNAL_LANGUAGE = "language";

    public ExternalLanguageFilter() {
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

        Optional<String> languageOpt = getLanguage(httpServletRequest);

        Optional<User> userOpt = ofNullable((User) httpServletRequest.getAttribute(LOGON_USER));

        userOpt.ifPresent(user -> languageOpt.ifPresent(user::setLanguage));

        chain.doFilter(request, response);
    }

    private Optional<String> getLanguage(HttpServletRequest request) {
        Optional<Cookie[]> cookiesOpt = ofNullable(request.getCookies());

        if (!cookiesOpt.isPresent()) {
            return Optional.empty();
        }

        for (Cookie cookie : cookiesOpt.get()) {
            if (EXTERNAL_LANGUAGE.equals(cookie.getName())) {
                try {
                    return Optional.of(new EncryptDecrypt().decryptParam(cookie.getValue()));
                } catch (Exception e) {
                    logger.error("Decrypt cookie language exception", e);
                    return Optional.empty();
                }
            }
        }

        return Optional.empty();
    }
}
