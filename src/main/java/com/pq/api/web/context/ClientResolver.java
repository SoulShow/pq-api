package com.pq.api.web.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 当前的Client分解器,目前的流程只有在 {@link ClientContextHolder#getClient()}的时候，
 * 才会被调用
 *
 * @author liken
 * @date 15/3/13
 */
public interface ClientResolver {

    /**
     * 目前这个方法
     * <p/>
     * Resolve the current locale via the given request.
     * Should return a default locale as fallback in any case.
     *
     * @param request the request to resolve the locale for
     * @return the current locale (never <code>null</code>)
     */
    Client resolveClient(HttpServletRequest request);

    /**
     * Set the current locale to the given one.
     *
     * @param request  the request to be used for locale modification
     * @param response the response to be used for locale modification
     * @param client   the new client, or <code>null</code> to clear the locale
     * @throws UnsupportedOperationException if the LocaleResolver implementation
     *                                       does not support dynamic changing of the theme
     */
    void setClient(HttpServletRequest request, HttpServletResponse response, Client client);

}
