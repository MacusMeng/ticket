package com.ibm.pmc.ticket.common.filter;

import javax.servlet.http.Cookie;
import java.util.Optional;

public class Cookies {
    /**
     * Create cookie.
     *
     * @param name    name
     * @param value   value
     * @param pathOpt pathOpt
     * @return cookie
     */
    public static Cookie toCookie(String name, String value, Optional<String> pathOpt) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath(pathOpt.orElse("/"));

        return cookie;
    }

    /**
     * Expire cookie.
     *
     * @param name    name
     * @param pathOpt pathOpt
     * @return cookie
     */
    public static Cookie toExpiredCookie(String name, Optional<String> pathOpt) {
        Cookie cookie = new Cookie(name, "");
        cookie.setMaxAge(0);
        cookie.setPath(pathOpt.orElse("/"));
        cookie.setHttpOnly(true);

        return cookie;
    }
}