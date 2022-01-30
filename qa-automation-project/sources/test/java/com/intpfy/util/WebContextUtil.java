package com.intpfy.util;

import com.intpfy.run.web.WebApplicationContext;
import com.intpfy.run.web.WebRunSession;
import com.intpfy.test.BaseWebTest;
import com.intpfyqa.Environment;
import com.intpfyqa.gui.web.selenium.BaseAutomationPage;
import com.intpfyqa.run.RunSessions;

public final class WebContextUtil {

    private WebContextUtil() {
    }

    public static BaseAutomationPage getCurrentPage() {
        return getCurrentContext().getBrowserSession().getActiveWindow().getActivePage();
    }

    public static WebApplicationContext getCurrentContext() {
        return getCurrentRunSession().getCurrentContext();
    }

    public static void switchToContext(WebApplicationContext context) {
        getCurrentRunSession().switchToContext(context);
    }

    public static WebApplicationContext switchToNewContext() {
        return switchToNewContext(Environment.instance().getAppUrl());
    }

    public static WebApplicationContext switchToNewContext(String url) {
        return getCurrentRunSession().switchToNewContext(url);
    }

    public static WebApplicationContext switchToNewContextOnDomain(String domain) {
        return switchToNewContext(BaseWebTest.createUrlWithDomain(domain));
    }

    public static void switchToDefaultContext() {
        getCurrentRunSession().switchToDefaultContext();
    }

    public static void closeCurrentContext() {
        getCurrentContext().close();
    }

    public static void restartBrowserSession() {
        getCurrentRunSession().restartBrowserSession(getCurrentContext());
    }

    public static void restartBrowserSession(String urlToOpen) {
        getCurrentRunSession().restartBrowserSession(getCurrentContext(), urlToOpen);
    }

    private static WebRunSession getCurrentRunSession() {
        return (WebRunSession) RunSessions.current();
    }
}
