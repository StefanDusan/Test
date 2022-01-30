package com.intpfyqa.gui.web.selenium.browser.launcher.local;

import com.intpfyqa.gui.web.selenium.SeleniumDownloadHelper;
import com.intpfyqa.gui.web.selenium.browser.launcher.BaseLauncher;
import com.intpfyqa.settings.WebSettings;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;

public class FFLauncher extends BaseLauncher {

    private SeleniumDownloadHelper downloadHelper;

    public FFLauncher() {
        downloadHelper = LocalSeleniumDownloadHelper.create(false, WebSettings.instance().getDownloadDirectory());
    }

    public SeleniumDownloadHelper getDownloadHelper() {
        return downloadHelper;
    }

    @Override
    protected RemoteWebDriver startDriver() {
        Capabilities capabilities = getStartupCapabilities();
        RemoteWebDriver driver;

        driver = new FirefoxDriver(new FirefoxOptions(capabilities));
        return driver;
    }

    public Capabilities getStartupCapabilities() {
        File executable = setupExecutable(WebSettings.instance().getProperty("geckodriver"));
        System.setProperty(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY, executable.getAbsolutePath());

        FirefoxProfile profile = new FirefoxProfile();
        if (null != downloadHelper.getDownloadDir())
            profile.setPreference("browser.download.dir", downloadHelper.getDownloadDir().getAbsolutePath());

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

        if (WebSettings.instance().getBrowserLocale() != null) {
            profile.setPreference("intl.accept_languages", WebSettings.instance().getBrowserLocale().getLanguage());
        } else {
            profile.setPreference("intl.accept_languages", "en-us");
        }

        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);

        return options;
    }
}
