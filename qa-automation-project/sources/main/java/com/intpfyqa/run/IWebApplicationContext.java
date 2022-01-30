package com.intpfyqa.run;

import com.intpfyqa.gui.web.selenium.BaseAutomationPage;
import com.intpfyqa.gui.web.selenium.browser.BrowserSession;

public interface IWebApplicationContext extends IApplicationContext {

    /**
     * @return Browser session
     */
    BrowserSession getBrowserSession();

    /**
     * @param session Browser session
     */
    void setBrowserSession(BrowserSession session);

    /**
     * Reset the context
     */
    void reset();

    /**
     * @return Currently opened page
     */
    BaseAutomationPage getCurrentPage();
}