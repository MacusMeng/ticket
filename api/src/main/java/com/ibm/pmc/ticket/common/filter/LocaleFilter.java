package com.ibm.pmc.ticket.common.filter;

import com.google.common.base.Splitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Component
public class LocaleFilter extends IgnorableFilter {
    private static final Logger logger = LoggerFactory.getLogger(LocaleFilter.class);

    private static final String LOCALE = "locale";

    public static final String CURRENT_LOCALE = "CURRENT_LOCALE";

    public LocaleFilter() {
        this.ignore = new String[]{
                "/api/users/.*$",
                "/api/users$"
        };
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        Optional<String> localeOpt = Optional.ofNullable(httpServletRequest.getParameter(LOCALE));

        if (localeOpt.isPresent()) {
            List<String> languageAndCountry = Splitter.on("_").splitToList(localeOpt.get());
            String language = languageAndCountry.get(0);
            String country = languageAndCountry.get(1);
            httpServletRequest.setAttribute(CURRENT_LOCALE, new Locale(language, country));
        } else {
            httpServletRequest.setAttribute(CURRENT_LOCALE, httpServletRequest.getLocale());
        }

        chain.doFilter(request, response);
    }
}
