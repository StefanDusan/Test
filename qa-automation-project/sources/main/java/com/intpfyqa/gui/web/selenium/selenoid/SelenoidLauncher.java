package com.intpfyqa.gui.web.selenium.selenoid;

import com.intpfyqa.gui.web.selenium.browser.launcher.BaseLauncher;
import com.intpfyqa.settings.WebSettings;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import static com.intpfyqa.gui.web.selenium.browser.BrowserSession.UNHANDLED_ALERT_CAPABILITY;

public abstract class SelenoidLauncher extends BaseLauncher {

    private final SelenoidDownloadHelper downloadHelper = new SelenoidDownloadHelper();


    public Capabilities getStartupCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.merge(createDriverCapabilities());
        capabilities.setCapability("enableVNC",
                Boolean.parseBoolean(WebSettings.instance().getProperty("selenoid.enableVNC", "false")));
        capabilities.setCapability("enableVideo",
                Boolean.parseBoolean(WebSettings.instance().getProperty("selenoid.enableVideo", "false")));
        capabilities.setCapability("sessionTimeout", "15m");
        capabilities.setCapability(UNHANDLED_ALERT_CAPABILITY, UnexpectedAlertBehaviour.DISMISS_AND_NOTIFY);
        return capabilities;
    }

    public RemoteWebDriver startDriver() {
        Capabilities capabilities = getStartupCapabilities();

        RemoteWebDriver driver = new RemoteWebDriver(SelenoidHelper.getSelenoidHubUrl(), capabilities);

        driver.setFileDetector(new LocalFileDetector());
        downloadHelper.setDriverSessionId(driver.getSessionId().toString());
        return driver;
    }

    protected abstract Capabilities createDriverCapabilities();


    public SelenoidDownloadHelper getDownloadHelper() {
        return downloadHelper;
    }
}
