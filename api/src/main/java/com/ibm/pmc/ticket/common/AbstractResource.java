package com.ibm.pmc.ticket.common;

import com.ibm.pmc.ticket.admin.domain.Admin;
import com.ibm.pmc.ticket.common.filter.AdminAuthenticationFilter;
import com.ibm.pmc.ticket.common.filter.LocaleFilter;
import com.ibm.pmc.ticket.common.filter.UserAuthenticationFilter;
import com.ibm.pmc.ticket.user.transferdomains.User;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.util.Locale;

public abstract class AbstractResource {
    @Context
    private HttpServletRequest httpServletRequest;

    protected User currentUser() {
        return (User) httpServletRequest.getAttribute(UserAuthenticationFilter.LOGON_USER);
    }

    protected Admin currentAdmin() {
        return (Admin) httpServletRequest.getAttribute(AdminAuthenticationFilter.LOGON_ADMIN);
    }

    protected Locale currentLocale() {
        return (Locale) httpServletRequest.getAttribute(LocaleFilter.CURRENT_LOCALE);
    }
}
