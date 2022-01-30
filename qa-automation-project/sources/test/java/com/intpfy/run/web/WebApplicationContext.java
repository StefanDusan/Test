package com.intpfy.run.web;

import com.intpfy.user.BaseUser;
import com.intpfyqa.Environment;
import com.intpfyqa.gui.web.selenium.BaseAutomationPage;
import com.intpfyqa.gui.web.selenium.browser.BrowserSession;
import com.intpfyqa.logging.ITakeSnapshot;
import com.intpfyqa.run.IWebApplicationContext;
import com.intpfyqa.settings.WebSettings;

public class WebApplicationContext implements IWebApplicationContext {

    private BrowserSession browserSession;
    private BaseUser currentUser;

    @Override
    public BrowserSession getBrowserSession() {
        if (browserSession == null)
            throw new IllegalStateException("Browser is not initialized.");
        return browserSession;
    }

    @Override
    public void setBrowserSession(BrowserSession browserSession) {
        this.browserSession = browserSession;
        this.browserSession.setMaxTimeouts(WebSettings.instance().getPageTimeout());
    }

    @Override
    public String getAlias() {
        return "Interprefy";
    }

    @Override
    public String getEnvironmentName() {
        return Environment.getEnvironmentName();
    }

    @Override
    public ITakeSnapshot getTakeSnapshot() {
        return browserSession;
    }

    @Override
    public void close() {
        if (browserSession != null) {
            browserSession.quit();
        }
    }

    public BaseUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(BaseUser currentUser) {
        this.currentUser = currentUser;
    }

    public void reset() {
        currentUser = null;
    }

    @Override
    public BaseAutomationPage getCurrentPage() {
        return getBrowserSession().getActiveWindow().getActivePage();
    }

    public boolean isBrowserSetup() {
        return browserSession != null && browserSession.hasActiveWindow();
    }
}
