package com.intpfyqa.gui.web.selenium.browser.launcher;

import com.intpfyqa.gui.web.selenium.browser.launcher.local.ChromeLauncher;
import com.intpfyqa.gui.web.selenium.browser.launcher.local.FFLauncher;
import com.intpfyqa.gui.web.selenium.browser.launcher.local.IELauncher;
import com.intpfyqa.gui.web.selenium.selenoid.SelenoidChromeLauncher;
import com.intpfyqa.gui.web.selenium.selenoid.SelenoidFirefoxLauncher;
import com.intpfyqa.settings.WebSettings;

public class LauncherFactory {

    public static BaseLauncher createLauncherFor(String browserName) {
        browserName = browserName.toLowerCase();
        BaseLauncher launcher;

        if (WebSettings.instance().useSelenoid()) {
            if (browserName.matches("chrome")) {
                launcher = new SelenoidChromeLauncher();
            } else if (browserName.matches("firefox")) {
                launcher = new SelenoidFirefoxLauncher();
            } else {
                throw new RuntimeException("Unsupported browser for Selenoid. Launcher for browser type: '" + browserName + "' is not implemented");
            }
        } else if (!WebSettings.instance().useGrid()) {
            if (browserName.matches("ie|iexplorer|iexplore|internet explorer")) {
                launcher = new IELauncher();
            } else if (browserName.matches("ff|firefox|gecko")) {
                launcher = new FFLauncher();
            } else if (browserName.matches("chrome")) {
                launcher = new ChromeLauncher();
            } else {
                throw new RuntimeException("Unsupported browser. Browser type " + browserName + " is unknown");
            }
        } else {
            return new SeleniumGridLauncher(browserName);
        }
        return launcher;
    }
}
