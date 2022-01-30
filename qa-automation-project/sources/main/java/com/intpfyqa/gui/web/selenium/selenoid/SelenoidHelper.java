package com.intpfyqa.gui.web.selenium.selenoid;

import com.intpfyqa.settings.WebSettings;

import java.net.MalformedURLException;
import java.net.URL;

public class SelenoidHelper {

    public static String getSelenoidServerUrl() {
        return WebSettings.instance().getSelenoidUrl().toString();
    }

    public static URL getSelenoidHubUrl() {
        try {
            return new URL(getSelenoidServerUrl() + "/wd/hub");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}