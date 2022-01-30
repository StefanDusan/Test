package com.intpfyqa.gui.web.selenium.browser.launcher.local;

import com.intpfyqa.gui.web.selenium.SeleniumDownloadHelper;
import com.intpfyqa.gui.web.selenium.browser.launcher.BaseLauncher;
import com.intpfyqa.settings.WebSettings;
import com.intpfyqa.utils.TestUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.concurrent.TimeUnit;

public class IELauncher extends BaseLauncher {

    private SeleniumDownloadHelper downloadHelper;

    public IELauncher() {
        downloadHelper = LocalSeleniumDownloadHelper.create(true, WebSettings.instance().getDownloadDirectory());
    }

    public SeleniumDownloadHelper getDownloadHelper() {
        return downloadHelper;
    }

    public synchronized void launch() {
        try {
            Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe").waitFor(10, TimeUnit.SECONDS);
        } catch (Throwable ignore) {

        }
        try {
            super.launch();
        } catch (Throwable ignore) {
            TestUtils.sleep(5000);
            try {
                Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe").waitFor(10, TimeUnit.SECONDS);
            } catch (Throwable ignore2) {

            }
            super.launch();
        }
    }

    @Override
    protected RemoteWebDriver startDriver() {

        Capabilities capabilities = getStartupCapabilities();

        InternetExplorerDriverService service = new InternetExplorerDriverService.Builder().usingAnyFreePort()
                .usingDriverExecutable(setupExecutable(WebSettings.instance().getProperty("iedriver"))).build();

        return new InternetExplorerDriver(service, new InternetExplorerOptions(capabilities));
    }

    public Capabilities getStartupCapabilities() {
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

    protected long getStartTimeoutSeconds() {
        return 60;
    }
}
