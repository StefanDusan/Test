package com.intpfyqa.settings;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class WebSettings extends BaseSettings {

    private static final String PREFIX = "web";
    private static final WebSettings instance = new WebSettings();
    public static final Duration AJAX_TIMEOUT = instance().getElementAjaxTimeout();

    private WebSettings() {
    }

    @Override
    String getPrefix() {
        return PREFIX;
    }

    @Override
    public Map<String, String> listProps() {
        Map<String, String> out = new HashMap<>();
        out.put("Page load timeout", getPageTimeout().getSeconds() + " seconds");
        out.put("Locale", getBrowserLocale().toString());
        if (useGrid())
            out.put("WebDriver", "GRID: " + getGridUrl().toString());
        else if (useSelenoid()) {
            out.put("WebDriver", "Selenoid: " + getSelenoidUrl().toString());
        } else {
            out.put("WebDriver", "Local");
        }
        return out;
    }

    public static WebSettings instance() {
        return instance;
    }

    public File getDownloadDirectory() {
        return new File(getProperty("download.dir", System.getProperty("java.io.tmpdir")));
    }

    public Locale getBrowserLocale() {
        return new Locale(getProperty("locale", Locale.getDefault().getLanguage()));
    }

    public boolean useGrid() {
        return Boolean.parseBoolean(getProperty("grid.enabled", "false"));
    }

    public boolean useSelenoid() {
        return Boolean.parseBoolean(getProperty("selenoid.enabled", "false"));
    }

    public URL getGridUrl() {
        try {
            return new URL(getProperty("grid.url"));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public URL getSelenoidUrl() {
        try {
            return new URL(getProperty("selenoid.driverURI"));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public Duration getPageTimeout() {
        return Duration.ofSeconds(Integer.parseInt(getProperty("timeouts.page", "30")));
    }

    public Duration getElementAjaxTimeout() {
        return Duration.ofSeconds(Integer.parseInt(getProperty("timeouts.ajax", "0")));
    }
}