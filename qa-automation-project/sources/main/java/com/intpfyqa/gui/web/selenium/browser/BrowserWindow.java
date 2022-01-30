package com.intpfyqa.gui.web.selenium.browser;

import com.intpfyqa.gui.ITakeScreenshot;
import com.intpfyqa.gui.web.selenium.BaseAutomationPage;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.exceptions.BrowserException;
import com.intpfyqa.logging.LogManager;
import com.intpfyqa.logging.impl.ImageSnapshot;
import com.intpfyqa.utils.TestUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Base64;

public class BrowserWindow implements ITakeScreenshot, IPageContext {

    private static final String LOG_HEADER = "Browser";

    private final BrowserSession session;
    private final String handle;
    private final BrowserWindow fromWindow;
    private BaseAutomationPage activePage = null;

    BrowserWindow(BrowserSession session, String handle) {
        this.session = session;
        this.handle = handle;
        this.fromWindow = null;
    }

    BrowserWindow(BrowserWindow fromWindow, String handle) {
        this.session = fromWindow.getSession();
        this.handle = handle;
        this.fromWindow = fromWindow;
    }

    public void setActivePage(BaseAutomationPage page) {
        this.activePage = page;
    }

    public BaseAutomationPage getActivePage() {
        return activePage;
    }

    BrowserWindow openedFromWindow() {
        return fromWindow;
    }

    public BrowserSession getSession() {
        return session;
    }

    public String getCurrentUrl() {
        try {
            return getDriver().getCurrentUrl();
        } catch (UnhandledAlertException e) {
            getDriver().switchTo().alert().accept();
        }
        return getDriver().getCurrentUrl();
    }

    public void get(String url) {
        LogManager.getCurrentTestLogger().debug(LOG_HEADER, "Navigating to " + url);
        getDriver().navigate().to(url);
    }

    public void maximize() {
        getDriver().manage().window().maximize();
    }

    public synchronized String getScreenShot() {
        try {
            return getDriver().getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ImageSnapshot takeScreenshot() {
        try {
            return new ImageSnapshot(getDriver().getScreenshotAs(OutputType.BASE64));
        } catch (Throwable t) {
            try {
                LogManager.getCurrentTestLogger().fail(LOG_HEADER, "Error while getting screenshot\n" +
                        TestUtils.getThrowableFullDescription(t));
            } catch (Exception ignore) {

            }

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                ImageIO.write(image, "PNG", outputStream);
                return new ImageSnapshot(Base64.getEncoder().encodeToString(outputStream.toByteArray()));
            } catch (IOException | AWTException ignore) {
                return null;
            }
        }
    }

    public String getPageSource() {
        try {
            return getDriver().getPageSource();
        } catch (Throwable t) {
            return "Couldn't get page source:\n" + TestUtils.getThrowableFullDescription(t);
        }
    }

    public void refresh() {
        LogManager.getCurrentTestLogger().trace(LOG_HEADER, "Refresh page");
        try {
            getDriver().navigate().refresh();
        } catch (UnhandledAlertException e) {
            LogManager.getCurrentTestLogger().trace(LOG_HEADER, "Confirm alert");
            getDriver().switchTo().alert().accept();
        }
        LogManager.getCurrentTestLogger().debug(LOG_HEADER, "Page refreshed");
    }

    public boolean hasAlert() {
        try {
            getAlert();
            return true;
        } catch (NoAlertPresentException ignore) {
            return false;
        }
    }

    public boolean hasAlert(Duration timeout) {
        try {
            getAlert(timeout);
            return true;
        } catch (NoAlertPresentException ignore) {
            return false;
        }
    }

    public void refreshWithoutAlertClosure() {
        LogManager.getCurrentTestLogger().trace(LOG_HEADER, "Refresh page");
        getDriver().navigate().refresh();
        LogManager.getCurrentTestLogger().debug(LOG_HEADER, "Page refreshed");
    }

    public void back() {
        LogManager.getCurrentTestLogger().trace(LOG_HEADER, "Navigate back in browser");
        getDriver().navigate().back();
        LogManager.getCurrentTestLogger().debug(LOG_HEADER, "Navigated back in browser");
    }

    public RemoteWebDriver getDriver() {
        return session.switchDriverTo(this);
    }

    String getHandle() {
        return handle;
    }

    public boolean equals(Object o) {
        if (o instanceof BrowserWindow) {
            return ((BrowserWindow) o).getHandle().equals(this.handle);
        }

        return super.equals(o);
    }

    public Object executeScript(String script, Object... args) {
        LogManager.getCurrentTestLogger().trace(LOG_HEADER, "Executing script:\n" + script);
        try {
            Object result = getDriver().executeScript(script, expandArgs(args));
            LogManager.getCurrentTestLogger().debug(LOG_HEADER, "Execute script result: " + result);
            return result;
        } catch (Throwable t) {
            throw BrowserException.onCommand(this, "executeScript", new Object[]{script,
                    "[" + StringUtils.join(args, ", ") + "]"}, t);
        }
    }

    private Object[] expandArgs(Object[] args) {
        Object[] newArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Element) {
                newArgs[i] = getNativeElement((Element) args[i]);
            } else if (args[i] instanceof BaseComponent) {
                newArgs[i] = getNativeElement(((BaseComponent) args[i]).getComponentElement());
            } else {
                newArgs[i] = args[i];
            }
        }

        return newArgs;
    }

    private WebElement getNativeElement(Element fromElement) {
        try {
            Method m = Element.class.getDeclaredMethod("getNativeElement");
            m.setAccessible(true);
            return (WebElement) m.invoke(fromElement);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Failed to resolve native element for JS execution " +
                    "(element: " + fromElement.toString() + ")", e);
        }
    }

    public void close() {
        try {
            getDriver().close();
            TestUtils.sleep(500);
        } catch (UnhandledAlertException e) {
            LogManager.getCurrentTestLogger().debug(LOG_HEADER, "Explicit accepting of the alert after the refresh");
            getDriver().switchTo().alert().accept();
        } catch (SessionNotCreatedException ignore) {

        }
        session.switchToMainWindow();
    }

    public void closeBrowserWithoutAlertClosure() {
        LogManager.getCurrentTestLogger().info(LOG_HEADER, "Window closing");
        // using 'close' calls closing browser when there is 1 tab. When we use quit Alert is not appeared
        getDriver().close();
        TestUtils.sleep(500);
    }

    public boolean isIE() {
        return session.isIE();
    }

    public boolean isFF() {
        return session.isFF();
    }

    public boolean isChrome() {
        return session.isChrome();
    }

    public String toString() {
        return "Browser window " + handle;
    }

    /**
     * @return true if driver is alive else false
     */
    public Boolean isAlive() {
        try {
            getDriver().getCurrentUrl();
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public BrowserWindow getBrowserWindow() {
        return this;
    }

    public Alert getAlert() {
        return getSession().switchToAlert();
    }

    public Alert getAlert(Duration timeout) {
        return getSession().switchToAlert(timeout);
    }
}
