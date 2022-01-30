package com.intpfy.util;

import com.intpfy.gui.pages.web_rtc.WebRtcPage;
import com.intpfyqa.gui.web.selenium.browser.BrowserWindow;


public final class WebRtcUtil {

    private static final String URL = "chrome://webrtc-internals/";
    private static final int REDUCED_WINDOW_SIZE_IN_PERCENT = 80;

    private WebRtcUtil() {
    }

    public static WebRtcPage openWebRtcPage() {
        return openWebRtcPage(BrowserUtil.openNewWindow());
    }

    public static WebRtcPage openWebRtcPageInReducedWindow() {
        return openWebRtcPage(BrowserUtil.openNewWindow(REDUCED_WINDOW_SIZE_IN_PERCENT));
    }

    private static WebRtcPage openWebRtcPage(BrowserWindow browserWindow) {
        browserWindow.get(URL);
        return new WebRtcPage(browserWindow);
    }
}