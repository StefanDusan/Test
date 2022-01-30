package com.intpfyqa.gui.web.selenium.selenoid;

import com.intpfyqa.settings.WebSettings;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;

/**
 * Class for launch FirefoxDriver in Selenoid
 */
public class SelenoidFirefoxLauncher extends SelenoidLauncher {

    /**
     * Creates and sets up FirefoxDriver
     *
     * @return
     */
    @Override
    protected Capabilities createDriverCapabilities() {
        FirefoxProfile profile = new FirefoxProfile();
        setPreferences(profile);
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);
        setCapabilities(options);
        options.setBrowserVersion(WebSettings.instance().getProperty("selenoid.FirefoxVersion"));

        return options;
    }

    /**
     * Sets up FirefoxProfile
     *
     * @param profile FirefoxProfile
     */
    private void setPreferences(FirefoxProfile profile) {
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                "text/csv,application/pdf,application/doc,application/xml,application/xhtml+xml," +
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document," +
                        "image/jpeg,application/zip,application/vnd.ms-excel," +
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/octet-stream");
        profile.setPreference("pdfjs.disabled", true);
        profile.setPreference("plugin.scan.Acrobat", "99.0");
        profile.setPreference("plugin.scan.plid.all", true);
        profile.setPreference("browser.download.animateNotifications", false);
        profile.setPreference("browser.download.manager.showAlertOnComplete", false);
        profile.setPreference("app.update.auto", false);
        profile.setPreference("app.update.enabled", false);
        profile.setPreference("app.update.silent", false);
        profile.setPreference("app.update.migrated.updateDir", false);
        profile.setPreference("app.update.service.enabled", false);
        profile.setPreference("acceptInsecureCerts", true);
        if (WebSettings.instance().getBrowserLocale() != null) {
            profile.setPreference("intl.accept_languages", WebSettings.instance().getBrowserLocale().getLanguage());
        } else {
            profile.setPreference("intl.accept_languages", "en-us");
        }
    }

    /**
     * Sets up DesiredCapabilities
     *
     * @param options DesiredCapabilities
     */
    private void setCapabilities(FirefoxOptions options) {
        options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
    }
}