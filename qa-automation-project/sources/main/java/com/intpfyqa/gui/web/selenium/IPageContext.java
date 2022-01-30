package com.intpfyqa.gui.web.selenium;

import com.intpfyqa.gui.web.selenium.browser.BrowserWindow;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Context of web page
 */
public interface IPageContext {

    /**
     * Driver switched into this context
     *
     * @return Driver switched into this context
     */
    RemoteWebDriver getDriver();

    /**
     * A window of browser this context belongs to
     *
     * @return A window of browser this context belongs to
     */
    BrowserWindow getBrowserWindow();
}
