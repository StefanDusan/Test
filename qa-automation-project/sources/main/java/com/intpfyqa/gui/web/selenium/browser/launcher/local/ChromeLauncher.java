package com.intpfyqa.gui.web.selenium.browser.launcher.local;

import com.intpfyqa.gui.web.selenium.SeleniumDownloadHelper;
import com.intpfyqa.gui.web.selenium.browser.launcher.BaseLauncher;
import com.intpfyqa.settings.MediaSettings;
import com.intpfyqa.settings.WebSettings;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ChromeLauncher extends BaseLauncher {

    private final SeleniumDownloadHelper downloadHelper;

    public ChromeLauncher() {
        downloadHelper = LocalSeleniumDownloadHelper.create(false, WebSettings.instance().getDownloadDirectory());
    }

    public SeleniumDownloadHelper getDownloadHelper() {
        return downloadHelper;
    }

    @Override
    protected RemoteWebDriver startDriver() {
        ChromeOptions options = (ChromeOptions) getStartupCapabilities();
        RemoteWebDriver driver;

        ChromeDriverService driverService = new ChromeDriverService.Builder()
                .usingDriverExecutable(setupExecutable(WebSettings.instance().getProperty("chromedriver")))
                .build();

        driver = new ChromeDriver(
                driverService,
                options);

        return driver;
    }

    public Capabilities getStartupCapabilities() {
        Map<String, Object> prefs = new HashMap<>();
        ChromeOptions options = new ChromeOptions();
        options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

        if (null != downloadHelper.getDownloadDir())
            prefs.put("download.default_directory", downloadHelper.getDownloadDir().getAbsolutePath());

        prefs.put("plugins.always_open_pdf_externally", true);
        prefs.put("pdfjs.disabled", true);
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("disable-popup-blocking");
        options.addArguments("disable-infobars");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        // options.addArguments("single-process");
        options.addArguments("use-fake-ui-for-media-stream");
        options.addArguments("use-fake-device-for-media-stream");
        MediaSettings mediaSettings = MediaSettings.instance();
        options.addArguments("use-file-for-fake-audio-capture=" + mediaSettings.getLocalAudioFilePath());
        options.addArguments("use-file-for-fake-video-capture=" + mediaSettings.getLocalVideoFilePath());
        options.setBinary("C:/Program Files/Google/Chrome Beta/Application/chrome.exe");

        if (WebSettings.instance().getBrowserLocale() != null) {
            options.addArguments("lang=" + WebSettings.instance().getBrowserLocale().getLanguage());
        }

        return options;
    }
}
