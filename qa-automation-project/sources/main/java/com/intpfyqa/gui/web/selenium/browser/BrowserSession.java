package com.intpfyqa.gui.web.selenium.browser;

import com.intpfyqa.gui.ITakeScreenshot;
import com.intpfyqa.gui.web.selenium.SeleniumDownloadHelper;
import com.intpfyqa.gui.web.selenium.browser.launcher.BaseLauncher;
import com.intpfyqa.gui.web.selenium.browser.launcher.LauncherFactory;
import com.intpfyqa.logging.LogManager;
import com.intpfyqa.logging.impl.ImageSnapshot;
import com.intpfyqa.settings.WebSettings;
import com.intpfyqa.utils.TestUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class BrowserSession implements ITakeScreenshot {

    public static final String UNHANDLED_ALERT_CAPABILITY = "unexpectedAlertBehaviour";

    private static final String LOG_HEADER = "Selenium Session";

    private final static String MAIN_WINDOW_HANDLE_STRING = "MAIN_WINDOW_HANDLE";
    private final static String ALERT_WINDOW_HANDLE = "ALERT_WINDOW_HANDLE";

    private final BaseLauncher launcher;
    private final String id;
    private final Map<String, Object> properties = new HashMap<>();
    private final Set<String> lastKnownWindowHandles = new HashSet<>();
    private BrowserWindow mainWindow;
    private BrowserWindow activeWindow;
    private String mainWindowHandle;
    private final SeleniumDownloadHelper downloadHelper;
    private Duration defaultTimeout = null;

    private BrowserSession(BaseLauncher launcher) {
        this.launcher = launcher;
        this.id = UUID.randomUUID() + "";
        this.downloadHelper = launcher.getDownloadHelper();
        initDriver();
    }

    public SeleniumDownloadHelper getDownloadHelper() {
        return downloadHelper;
    }

    public static BrowserSession createNew(String browserName) {
        return new BrowserSession(LauncherFactory.createLauncherFor(browserName));
    }

    public void setMaxTimeouts(Duration timeout) {
        this.defaultTimeout = timeout;
        updateTimeouts();
    }

    private void updateTimeouts() {
        if (null != defaultTimeout) {
            try {
                launcher.getDriver().manage().timeouts().scriptTimeout(defaultTimeout);
                launcher.getDriver().manage().timeouts().pageLoadTimeout(defaultTimeout);
            } catch (WebDriverException ignore) {

            }
        }
    }

    public boolean hasActiveWindow() {
        return null != activeWindow;
    }

    private void initDriver() {
        launcher.launch();
        mainWindowHandle = getDriver().getWindowHandle();
        mainWindow = new BrowserWindow(this, MAIN_WINDOW_HANDLE_STRING);
        activeWindow = mainWindow;
        mainWindow.maximize();
        updateWindowsInfo();
        updateTimeouts();
    }

    public boolean isAlertPresent(int timeout) {
        BrowserWindow current = getActiveWindow();
        if (current.getHandle().equalsIgnoreCase(ALERT_WINDOW_HANDLE)) {
            current = current.openedFromWindow();
        }
        LocalDateTime endTime = LocalDateTime.now().plusSeconds(timeout);
        do {
            try {
                switchToAlert();
                return true;
            } catch (WebDriverException ignore) {

            }
        } while (LocalDateTime.now().isBefore(endTime));
        switchDriverTo(current);
        return false;
    }

    public boolean isAlertNotPresent(int timeout) {
        BrowserWindow current = getActiveWindow();
        if (current.getHandle().equalsIgnoreCase(ALERT_WINDOW_HANDLE)) {
            current = current.openedFromWindow();
        } else {
            current = mainWindow;
        }
        LocalDateTime endTime = LocalDateTime.now().plusSeconds(timeout);
        do {
            try {
                switchToAlert();
            } catch (WebDriverException ignore) {
                switchDriverTo(current);
                return true;
            }
        } while (LocalDateTime.now().isBefore(endTime));

        return false;
    }

    public Alert switchToAlert() {
        return switchToAlert(Duration.ZERO);
    }

    public Alert switchToAlert(Duration timeout) {
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);
        Alert alert = null;
        WebDriverException last = null;
        do {
            try {
                alert = getDriver().switchTo().alert();
                break;
            } catch (WebDriverException e) {
                last = e;
            }
        } while (LocalDateTime.now().isBefore(endTime));

        if (null == alert) {
            if (null != last) throw last;
            throw new NoAlertPresentException();
        }

        switchDriverTo(new BrowserWindow(getActiveWindow(), ALERT_WINDOW_HANDLE));
        return alert;
    }

    public Capabilities getRuntimeCapabilities() {
        return launcher.getStartupCapabilities().merge(getDriver().getCapabilities());
    }

    public BrowserWindow getActiveWindow() {
        if (null == activeWindow)
            throw new IllegalStateException("Current browser window is undefined");
        return activeWindow;
    }

    private void resetProperties() {
        properties.clear();
    }

    public void setProperty(String propertyName, Object propertyValue) {
        properties.put(propertyName, propertyValue);
    }

    public Object getProperty(String propertyName) {
        return properties.get(propertyName);
    }

    public Set<Cookie> getCookies() {
        return getDriver().manage().getCookies();
    }

    public void closeAllWindowsExceptMain() {

        for (String handle : getDriver().getWindowHandles()) {
            if (handle.equals(mainWindow.getHandle()) || handle.equals(mainWindowHandle)) continue;
            switchDriverTo(new BrowserWindow(this, handle));
            try {
                getDriver().close();
            } catch (UnhandledAlertException ignore) {
                getDriver().switchTo().alert().dismiss();
            }
        }

        updateWindowsInfo();
        switchToMainWindow();
    }

    public String getActiveWindowHandle() {
        return getDriver().getWindowHandle();
    }

    public void clearCookies() {
        if (isIE()) {
            try {
                getDriver().manage().deleteAllCookies();
                quit();
                initDriver();
            } catch (Throwable t) {
                LogManager.getCurrentTestLogger().debug(LOG_HEADER, "Failed to clean IE cookies\n" +
                        TestUtils.getThrowableFullDescription(t));
            }
        } else {
            getDriver().manage().deleteAllCookies();
        }
    }

    private void updateWindowsInfo() {
        lastKnownWindowHandles.clear();
        lastKnownWindowHandles.addAll(getDriver().getWindowHandles());
    }

    public BrowserWindow waitForNewWindowToOpen(Duration timeout) {
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);
        do {
            Set<String> actual = getDriver().getWindowHandles();
            for (String a : actual) {
                if (!lastKnownWindowHandles.contains(a)) {
                    BrowserWindow newWindow = new BrowserWindow(this, a);
                    updateWindowsInfo();
                    switchDriverTo(newWindow);
                    return newWindow;
                }
            }
        } while (LocalDateTime.now().isBefore(endTime));

        return null;
    }

    public int getWindowsCount() {
        return getDriver().getWindowHandles().size();
    }

    private RemoteWebDriver getDriver() {
        return launcher.getDriver();
    }

    protected RemoteWebDriver switchDriverTo(BrowserWindow window) {
        LogManager.getCurrentTestLogger().trace(LOG_HEADER, "Switching to " + window + ". Active is: " + activeWindow);
        if (activeWindow.equals(window)) return getDriver();
        String handle = window.getHandle();
        if (handle.equals(MAIN_WINDOW_HANDLE_STRING)) {
            handle = mainWindowHandle;
        }
        if (!handle.equalsIgnoreCase(ALERT_WINDOW_HANDLE)) {
            getDriver().switchTo().window(handle);
        } else {
            getDriver().switchTo().alert();
        }
        activeWindow = window;
        return getDriver();
    }

    public void switchToWindow(BrowserWindow browserWindow) {
        switchDriverTo(browserWindow);
    }

    public void switchToMainWindow() {
        switchDriverTo(mainWindow);
    }

    public boolean equals(Object o) {
        if (o instanceof BrowserSession) {
            return ((BrowserSession) o).id.equals(this.id);
        }

        return super.equals(o);
    }

    public BrowserWindow openNewWindow() {
        return openNewWindow("window.open()");
    }

    public BrowserWindow openNewWindow(int sizeInPercent) {
        Dimension windowSize = getWindowSize();
        int newWindowWidth = getPercentOfNumber(windowSize.width, sizeInPercent);
        int newWindowHeight = getPercentOfNumber(windowSize.height, sizeInPercent);
        String openWindowScript = String.format("window.open('', '', 'width=%s,height=%s');", newWindowWidth, newWindowHeight);
        return openNewWindow(openWindowScript);
    }

    private Dimension getWindowSize() {
        return getDriver().manage().window().getSize();
    }

    private int getPercentOfNumber(int number, int percent) {
        return (int) (number * (percent / 100.0f));
    }

    private BrowserWindow openNewWindow(String jsScript) {
        getDriver().executeScript(jsScript);
        return waitForNewWindowToOpen(WebSettings.instance().getPageTimeout());
    }

    public boolean isIE() {
        return getDriver().getCapabilities().getBrowserName().equalsIgnoreCase(
                new InternetExplorerOptions().getBrowserName()
        );
    }

    public boolean isFF() {
        return getDriver().getCapabilities().getBrowserName().equalsIgnoreCase(
                new FirefoxOptions().getBrowserName()
        );
    }

    public boolean isChrome() {
        return getDriver().getCapabilities().getBrowserName().equalsIgnoreCase(
                new ChromeOptions().getBrowserName()
        );
    }

    public void restart() {
        quit();
        initDriver();
    }

    public boolean isDead() {
        try {
            getDriver().getTitle();
            return false;
        } catch (Exception ignore) {
            return true;
        }
    }

    public String getScreenShot() {
        try {
            return activeWindow.getScreenShot();
        } catch (WebDriverException ignore) {
            return mainWindow.getScreenShot();
        }

    }

    public void quit() {
        launcher.shutdown();
        resetProperties();
    }

    public ImageSnapshot takeScreenshot() {
        return getActiveWindow() != null ? getActiveWindow().takeScreenshot() : null;
    }

    public static BrowserSession createSessionWithRetry(String browserName) {
        LocalDateTime endTime = LocalDateTime.now().plusSeconds(5);
        Throwable lastException = null;
        BrowserSession result = null;
        do {
            try {
                result = BrowserSession.createNew(browserName);
                break;
            } catch (Throwable t) {
                lastException = t;
            }
            TestUtils.sleep(500);
        } while (LocalDateTime.now().isBefore(endTime));

        if (lastException != null) {
            if (lastException instanceof RuntimeException)
                throw (RuntimeException) lastException;
            else
                throw new RuntimeException(lastException);
        }
        return result;
    }
}
