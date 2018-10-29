package com.pq.api.web.context;

import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.core.NamedThreadLocal;

/**
 * 存放当前客户端信息的类
 *
 * @author liken
 * @date 15/3/13
 */
public abstract class ClientContextHolder {

    private static final ThreadLocal<ClientContext> clientContextHolder = new NamedThreadLocal<ClientContext>(
            "Client context");

    private static final ThreadLocal<ClientContext> inheritableClientContextHolder = new NamedInheritableThreadLocal<ClientContext>(
            "Client context");


    /**
     * Reset the LocaleContext for the current thread.
     */
    public static void resetClientContext() {
        clientContextHolder.remove();
        inheritableClientContextHolder.remove();
    }

    /**
     * Associate the given LocaleContext with the current thread.
     *
     * @param clientContext the current ClientContext,
     *                      or <code>null</code> to reset the thread-bound context
     * @param inheritable   whether to expose the LocaleContext as inheritable
     *                      for child threads (using an {@link InheritableThreadLocal})
     */
    public static void setClientContext(ClientContext clientContext, boolean inheritable) {
        if (clientContext == null) {
            resetClientContext();
        } else {
            if (inheritable) {
                inheritableClientContextHolder.set(clientContext);
                clientContextHolder.remove();
            } else {
                clientContextHolder.set(clientContext);
                inheritableClientContextHolder.remove();
            }
        }
    }

    /**
     * Return the LocaleContext associated with the current thread, if any.
     *
     * @return the current LocaleContext, or <code>null</code> if none
     */
    public static ClientContext getClientContext() {
        ClientContext clientContext = clientContextHolder.get();
        if (clientContext == null) {
            clientContext = inheritableClientContextHolder.get();
        }
        return clientContext;
    }

    /**
     * Associate the given LocaleContext with the current thread,
     * <i>not</i> exposing it as inheritable for child threads.
     *
     * @param clientContext the current LocaleContext
     */
    public static void setClientContext(ClientContext clientContext) {
        setClientContext(clientContext, false);
    }

    /**
     * Associate the given Locale with the current thread.
     * <p>Will implicitly create a LocaleContext for the given Locale.
     *
     * @param client      the current Locale, or <code>null</code> to reset
     *                    the thread-bound context
     * @param inheritable whether to expose the LocaleContext as inheritable
     *                    for child threads (using an {@link InheritableThreadLocal})
     * @see org.springframework.context.i18n.SimpleLocaleContext#SimpleLocaleContext(java.util.Locale)
     */
    public static void setClient(Client client, boolean inheritable) {
        ClientContext clientContext = (client != null ? new SimpleClientContext(client) : null);
        setClientContext(clientContext, inheritable);
    }

    /**
     * Return the Locale associated with the current thread, if any,
     * or the system default Locale else.
     * 永远都不会返回空
     *
     * @return the current Locale, or the system default Locale if no
     * specific Locale has been associated with the current thread
     * @see org.springframework.context.i18n.LocaleContext#getLocale()
     * @see java.util.Locale#getDefault()
     */
    public static Client getClient() {
        ClientContext clientContext = getClientContext();
        return (clientContext != null ? clientContext.getClient() : Client.getDefault());
    }

    /**
     * Associate the given Locale with the current thread.
     * <p>Will implicitly create a LocaleContext for the given Locale,
     * <i>not</i> exposing it as inheritable for child threads.
     *
     * @param client the current Locale, or <code>null</code> to reset
     *               the thread-bound context
     * @see org.springframework.context.i18n.SimpleLocaleContext#SimpleLocaleContext(java.util.Locale)
     */
    public static void setClient(Client client) {
        setClient(client, false);
    }

    /**
     * clean current Client
     */
    public static void clearContext() {
        resetClientContext();
    }


}

