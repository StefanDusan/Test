package com.intpfyqa.gui.web.selenium.selenoid;

import com.intpfyqa.settings.MediaSettings;
import com.intpfyqa.settings.WebSettings;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Class for launch Chrome browser in Selenoid
 */
public class SelenoidChromeLauncher extends SelenoidLauncher {

    private static final String CUSTOM_BROWSER_IMAGE_SUFFIX = "interprefy";

    /**
     * @return new Chrome capabilities
     */
    @Override
    protected Capabilities createDriverCapabilities() {
        ChromeOptions options = new ChromeOptions();
        options.setCapability("version", WebSettings.instance().getProperty("selenoid.ChromeVersion"));
        options.setExperimentalOption("prefs", getPrefs());
        options.addArguments("disable-popup-blocking", "disable-infobars");
        setLocale(options);
        //options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.addArguments("use-fake-ui-for-media-stream");
        options.addArguments("use-fake-device-for-media-stream");
        configureMediaEmulation(options);
        return options;
    }

    private void configureMediaEmulation(ChromeOptions options) {

        if (StringUtils.containsIgnoreCase(options.getBrowserVersion(), CUSTOM_BROWSER_IMAGE_SUFFIX)) {

            MediaSettings mediaSettings = MediaSettings.instance();
            options.addArguments("use-file-for-fake-audio-capture=" + mediaSettings.getSelenoidAudioFilePath());
            options.addArguments("use-file-for-fake-video-capture=" + mediaSettings.getSelenoidVideoFilePath());
        }
    }

    /**
     * @return map with capabilities for ChromeDriver
     */
    private Map<String, Object> getPrefs() {
        HashMap<String, Object> prefs = new HashMap<>();
        prefs.put("plugins.always_open_pdf_externally", true);
        prefs.put("pdfjs.disabled", true);
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        return prefs;
    }

    /**
     * Sets locale for ChromeDriver
     *
     * @param options ChromeOptions for ChromeDriver
     */
    private void setLocale(ChromeOptions options) {
        Locale browserLocale = WebSettings.instance().getBrowserLocale();
        if (browserLocale != null) {
            options.addArguments("lang=" + browserLocale.getLanguage());
        }
    }
}
