package com.intpfyqa.gui.web.selenium.browser.launcher;

import com.intpfyqa.gui.web.selenium.SeleniumDownloadHelper;
import com.intpfyqa.gui.web.selenium.browser.GridDownloadHelper;
import com.intpfyqa.settings.WebSettings;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.HashMap;
import java.util.Map;

public class SeleniumGridLauncher extends BaseLauncher {

    private SeleniumDownloadHelper downloadHelper;
    private final String browserName;

    public SeleniumGridLauncher(String browserName) {
        downloadHelper = new GridDownloadHelper(WebSettings.instance().getGridUrl().toString());
        this.browserName = browserName;
    }

    @Override
    public Capabilities getStartupCapabilities() {
        Capabilities capabilities;
        switch (browserName.toLowerCase()) {
            case "chrome":
                capabilities = createChromeCapabilities();
                break;
            case "ff":
            case "firefox":
                capabilities = createFFCapabilities();
                break;
            case "ie":
            case "internet explorer":
            case "iexplorer":
            case "iexplore":
                capabilities = createIECapabilities();
                break;
            default:
                throw new RuntimeException("Unknown browser name: " + browserName);
        }
        return capabilities;
    }

    private Capabilities createChromeCapabilities() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

        if (null != downloadHelper.getDownloadDir())
            prefs.put("download.default_directory", downloadHelper.getDownloadDir().toString());

        prefs.put("plugins.always_open_pdf_externally", true);
        prefs.put("pdfjs.disabled", true);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("disable-popup-blocking");
        options.addArguments("disable-infobars");
        options.addArguments("ignore-certificate-errors");
        // options.addArguments("single-process");

        if (WebSettings.instance().getBrowserLocale() != null) {
            options.addArguments("lang=" + WebSettings.instance().getBrowserLocale().getLanguage());
        }

        return options;
    }

    private Capabilities createFFCapabilities() {
        FirefoxProfile profile = new FirefoxProfile();
        if (null != downloadHelper.getDownloadDir())
            profile.setPreference("browser.download.dir", downloadHelper.getDownloadDir().toString());

        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                "text/csv,application/pdf,application/doc," +
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document," +
                        "image/jpeg,application/zip,application/vnd.ms-excel," +
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
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

        if (WebSettings.instance().getBrowserLocale() != null) {
            profile.setPreference("intl.accept_languages", WebSettings.instance().getBrowserLocale().getLanguage());
        } else {
            profile.setPreference("intl.accept_languages", "en-us");
        }

        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);

        return options;
    }

    private Capabilities createIECapabilities() {
        InternetExplorerOptions options = new InternetExplorerOptions();

        // capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true); //Obligatorily
        options.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false); //Obligatorily
        options.setCapability(InternetExplorerDriver.NATIVE_EVENTS, true); //Obligatorily
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
        options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        options.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
        options.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        options.setCapability("ie.fileUploadDialogTimeout", 90000);
        return options;
    }

    @Override
    protected RemoteWebDriver startDriver() {
        RemoteWebDriver remoteWebDriver = new RemoteWebDriver(WebSettings.instance().getGridUrl(), getStartupCapabilities());
        ((GridDownloadHelper) downloadHelper).setDriver(remoteWebDriver);
        return remoteWebDriver;
    }

    @Override
    public SeleniumDownloadHelper getDownloadHelper() {
        return downloadHelper;
    }
}
