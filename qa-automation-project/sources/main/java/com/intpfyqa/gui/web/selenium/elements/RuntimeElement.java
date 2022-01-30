package com.intpfyqa.gui.web.selenium.elements;

import com.intpfyqa.logging.LogManager;
import com.intpfyqa.utils.TestUtils;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Runtime link to desired application element
 * <p>
 * This wraps a static reference to the element of application, so allows to truly  manage state of desired application's element
 * </p>
 */
public class RuntimeElement {

    private final WebElement nativeElement;
    private final String originalName;

    RuntimeElement(String originalElementName, WebElement nativeElement) {
        this.nativeElement = nativeElement;
        this.originalName = originalElementName;
    }

    /**
     * Is reference still valid (e.g. this element is not refreshed in/disappeared from DOM)
     *
     * @return <code>true</code> if element still can be accessed
     */
    public boolean isValid() {
        trace("Is Valid?");
        try {
            nativeElement.getTagName();
            debug("Still valid");
            return true;
        } catch (StaleElementReferenceException ignore) {
            debug("Invalid");
            return false;
        }
    }

    /**
     * Is reference invalid (e.g. this element is refreshed in/disappeared from DOM)
     *
     * @return <code>true</code> if element can't be accessed anymore
     */
    public boolean isInvalid() {
        trace("Is Invalid?");
        try {
            nativeElement.getTagName();
            debug("Still valid");
            return false;
        } catch (StaleElementReferenceException ignore) {
            debug("Invalid");
            return true;
        }
    }

    /**
     * Wait reference become invalid
     *
     * @param timeout Timeout
     * @return <code>true</code> if element invalid
     * @see #isInvalid()
     */
    public boolean waitInvalid(Duration timeout) {
        trace("Wait Invalid?");
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);
        do {
            try {
                nativeElement.getTagName();
            } catch (StaleElementReferenceException ignore) {
                debug("Invalid");
                return true;
            }
            TestUtils.sleep(100);
        } while (LocalDateTime.now().isBefore(endTime));
        debug("Still valid");
        return false;
    }

    /**
     * Wait this element is invisible or invalid
     *
     * @param timeout Timeout
     * @return <code>true</code> if element invisible or invalid
     * @see #isInvalid()
     */
    public boolean notVisible(Duration timeout) {
        trace("Wait not visible");
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);
        do {
            try {
                if (!nativeElement.isDisplayed()) {
                    debug("Not visible");
                    return true;
                }
            } catch (StaleElementReferenceException ignore) {
                debug("Not visible and Invalid");
                return true;
            }
            TestUtils.sleep(100);
        } while (LocalDateTime.now().isBefore(endTime));
        debug("Still visible");
        return false;
    }

    /**
     * Wait this element is visible
     *
     * @param timeout Timeout
     * @return <code>true</code> if element visible
     */
    public boolean waitVisible(Duration timeout) {
        trace("Wait visible");
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);
        do {
            try {
                if (nativeElement.isDisplayed()) {
                    debug("Visible");
                    return true;
                }
            } catch (StaleElementReferenceException ignore) {
                debug("Not visible and Invalid");
                return false;
            }
            TestUtils.sleep(100);
        } while (LocalDateTime.now().isBefore(endTime));
        debug("Still not visible");
        return false;
    }

    /**
     * Is visible
     *
     * @return <code>true</code> if element visible
     */
    public boolean visible() {
        trace("Is visible?");
        try {
            boolean res = nativeElement.isDisplayed();
            debug(res ? "Visible" : "Invisible");
            return res;
        } catch (StaleElementReferenceException ignore) {
            debug("Invisible and invalid");
            return false;
        }
    }

    public String toString() {
        return "Runtime " + originalName;
    }

    protected WebElement getNativeElement() {
        return nativeElement;
    }

    protected void debug(String message) {
        LogManager.getCurrentTestLogger().debug(this.toString(), message);
    }

    protected void trace(String message) {
        LogManager.getCurrentTestLogger().trace(this.toString(), message);
    }
}