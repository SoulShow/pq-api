package com.pq.api.web.interceptor;

import com.pq.api.web.context.SimpleClientResolver;
import org.springframework.session.Session;
import org.springframework.session.web.http.HttpSessionManager;
import org.springframework.session.web.http.MultiHttpSessionStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * 使session兼容xtoken
 */
@Component
public class XTokenHttpSessionStrategy implements MultiHttpSessionStrategy, HttpSessionManager {

    private String headerName = SimpleClientResolver.XToken;

    static final String DEFAULT_ALIAS = "0";

    static final String DEFAULT_SESSION_ALIAS_PARAM_NAME = "_s";

    private Pattern ALIAS_PATTERN = Pattern.compile("^[\\w-]{1,50}$");

    private String cookieName = "SESSION";

    private String sessionParam = DEFAULT_SESSION_ALIAS_PARAM_NAME;

    private boolean isServlet3Plus = isServlet3();

    private MultiHttpSessionStrategy httpSessionStrategy;

    public void setHttpSessionStrategy(MultiHttpSessionStrategy httpSessionStrategy) {
        this.httpSessionStrategy = httpSessionStrategy;
    }

    @Override
    public String getRequestedSessionId(HttpServletRequest request) {
        String sessionId = request.getHeader(headerName);
        if (null == sessionId) {
            sessionId = httpSessionStrategy.getRequestedSessionId(request);
        }
        return sessionId;
    }

    @Override
    public void onNewSession(Session session, HttpServletRequest request, HttpServletResponse response) {
        String sessionId = request.getHeader(headerName);
        if (null == sessionId) {
            httpSessionStrategy.onNewSession(session, request, response);
        } else {
            response.setHeader(headerName, session.getId());
        }
    }

    @Override
    public void onInvalidateSession(HttpServletRequest request, HttpServletResponse response) {
        String sessionId = request.getHeader(headerName);
        if (null == sessionId) {
            httpSessionStrategy.onInvalidateSession(request, response);
        } else {
            response.setHeader(headerName, "");
        }
    }

    @Override
    public HttpServletRequest wrapRequest(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(HttpSessionManager.class.getName(), this);
        return request;
    }

    @Override
    public HttpServletResponse wrapResponse(HttpServletRequest request, HttpServletResponse response) {
        return new MultiSessionHttpServletResponse(response, request);

    }

    class MultiSessionHttpServletResponse extends HttpServletResponseWrapper {
        private final HttpServletRequest request;

        public MultiSessionHttpServletResponse(HttpServletResponse response, HttpServletRequest request) {
            super(response);
            this.request = request;
        }

        @Override
        public String encodeRedirectURL(String url) {
            url = super.encodeRedirectURL(url);
            return XTokenHttpSessionStrategy.this.encodeURL(url, getCurrentSessionAlias(request));
        }

        @Override
        public String encodeURL(String url) {
            url = super.encodeURL(url);

            String alias = getCurrentSessionAlias(request);
            return XTokenHttpSessionStrategy.this.encodeURL(url, alias);
        }
    }

    @Override
    public String getCurrentSessionAlias(HttpServletRequest request) {
        return httpSessionStrategy.getRequestedSessionId(request);
    }

    @Override
    public Map<String, String> getSessionIds(HttpServletRequest request) {
        Cookie session = getCookie(request, cookieName);
        String sessionCookieValue = session == null ? "" : session.getValue();
        Map<String,String> result = new LinkedHashMap<String,String>();
        StringTokenizer tokens = new StringTokenizer(sessionCookieValue, " ");
        if(tokens.countTokens() == 1) {
            result.put(DEFAULT_ALIAS, tokens.nextToken());
            return result;
        }
        while(tokens.hasMoreTokens()) {
            String alias = tokens.nextToken();
            if(!tokens.hasMoreTokens()) {
                break;
            }
            String id = tokens.nextToken();
            result.put(alias, id);
        }
        return result;
    }

    /**
     * Retrieve the first cookie with the given name. Note that multiple
     * cookies can have the same name but different paths or domains.
     * @param request current servlet request
     * @param name cookie name
     * @return the first cookie with the given name, or {@code null} if none is found
     */
    private static Cookie getCookie(HttpServletRequest request, String name) {
        if(request == null) {
            throw new IllegalArgumentException("request cannot be null");
        }
        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

    @Override
    public String encodeURL(String url, String sessionAlias) {
        String encodedSessionAlias = urlEncode(sessionAlias);
        int queryStart = url.indexOf("?");
        boolean isDefaultAlias = DEFAULT_ALIAS.equals(encodedSessionAlias);
        if(queryStart < 0) {
            return isDefaultAlias ? url : url + "?" + sessionParam + "=" + encodedSessionAlias;
        }
        String path = url.substring(0, queryStart);
        String query = url.substring(queryStart + 1, url.length());
        String replacement = isDefaultAlias ? "" : "$1"+encodedSessionAlias;
        query = query.replaceFirst( "((^|&)" + sessionParam + "=)([^&]+)?", replacement);
        if(!isDefaultAlias && url.endsWith(query)) {
            // no existing alias
            if(!(query.endsWith("&") || query.length() == 0)) {
                query += "&";
            }
            query += sessionParam + "=" + encodedSessionAlias;
        }

        return path + "?" + query;
    }

    private String urlEncode(String value) {
        if (value == null) {
            return "";
        }
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns true if the Servlet 3 APIs are detected.
     * @return
     */
    private boolean isServlet3() {
        try {
            ServletRequest.class.getMethod("startAsync");
            return true;
        } catch(NoSuchMethodException e) {}
        return false;
    }

    @Override
    public String getNewSessionAlias(HttpServletRequest request) {
        Set<String> sessionAliases = getSessionIds(request).keySet();
        if(sessionAliases.isEmpty()) {
            return DEFAULT_ALIAS;
        }
        long lastAlias = Long.decode(DEFAULT_ALIAS);
        for(String alias : sessionAliases) {
            long selectedAlias = safeParse(alias);
            if(selectedAlias > lastAlias) {
                lastAlias = selectedAlias;
            }
        }
        return Long.toHexString(lastAlias + 1);
    }

    private long safeParse(String hex) {
        try {
            return Long.decode("0x" + hex);
        } catch(NumberFormatException notNumber) {
            return 0;
        }
    }
}
