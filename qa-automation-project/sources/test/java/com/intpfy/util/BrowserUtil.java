package com.intpfy.util;

import com.intpfyqa.Environment;
import com.intpfyqa.gui.web.selenium.browser.BrowserSession;
import com.intpfyqa.gui.web.selenium.browser.BrowserWindow;

public final class BrowserUtil {

    private BrowserUtil() {
    }

    public static BrowserWindow getActiveWindow() {
        return getCurrentBrowserSession().getActiveWindow();
    }

    public static BrowserWindow openNewWindow() {
        return getCurrentBrowserSession().openNewWindow();
    }

    public static BrowserWindow openNewWindow(String url) {
        BrowserWindow window = openNewWindow();
        window.get(url);
        return window;
    }

    public static BrowserWindow openNewWindow(int sizeInPercent) {
        return getCurrentBrowserSession().openNewWindow(sizeInPercent);
    }

    public static void switchToMainWindow() {
        getCurrentBrowserSession().switchToMainWindow();
    }

    public static void closeAllWindowsExceptMain() {
        getCurrentBrowserSession().closeAllWindowsExceptMain();
    }

    public static void openUrl(String url) {
        getActiveWindow().get(url);
    }

    private static BrowserSession getCurrentBrowserSession() {
        return WebContextUtil.getCurrentContext().getBrowserSession();
    }
}
