package com.intpfy.run.web;

import com.intpfyqa.Environment;
import com.intpfyqa.gui.web.selenium.browser.BrowserSession;
import com.intpfyqa.run.IWebApplicationContext;

public class WebRunSession extends com.intpfyqa.run.RunSession {

    private static final int RESTART_DRIVER_EVERY_TESTS = 5;

    private int testCounter;

    public WebRunSession() {
        testCounter = 0;
    }

    @Override
    public WebApplicationContext getCurrentContext() {
        return (WebApplicationContext) super.getCurrentContext();
    }

    @Override
    protected void onceSessionAllocated() {

        WebApplicationContext context = getCurrentContext();

        if (context != null && context.isBrowserSetup()) {

            if (testCounter >= RESTART_DRIVER_EVERY_TESTS) {
                restartBrowserSession(context);
                testCounter = 0;

            } else {
                if (context.getBrowserSession().isDead()) {
                    restartBrowserSession(context);
                    testCounter = 0;
                } else {
                    testCounter++;
                }
            }

        } else if (context != null) {
            restartBrowserSession(context);
        }
    }

    @Override
    protected void beforeSessionFree() {
    }

    public void restartBrowserSessionBeforeCurrentTest() {
        // If 'testCounter == 0' then browser session has already been restarted.
        if (testCounter != 0) {
            restartBrowserSession(getCurrentContext());
        }
    }

    public void restartBrowserSessionAfterCurrentTest() {
        testCounter = RESTART_DRIVER_EVERY_TESTS;
    }

    public void restartBrowserSession(IWebApplicationContext context) {
        restartBrowserSession(context, Environment.instance().getAppUrl());
    }

    public void restartBrowserSession(IWebApplicationContext context, String url) {
        context.getBrowserSession().restart();
        context.reset();
        context.getBrowserSession().getActiveWindow().get(url);
        testCounter = 0;
    }

    public WebApplicationContext switchToNewContext(String url) {
        WebApplicationContext context = new WebApplicationContext();
        switchToContext(context);
        initWebApplicationContext(context, url);
        return context;
    }

    private void initWebApplicationContext(WebApplicationContext context, String url) {
        setNewBrowserSession(context);
        openUrl(context, url);
    }

    private void setNewBrowserSession(WebApplicationContext context) {
        context.setBrowserSession(BrowserSession.createSessionWithRetry(Environment.instance().getBrowser()));
    }

    private static void openUrl(IWebApplicationContext context, String url) {
        context.getBrowserSession().getActiveWindow().get(url);
    }
}
