package com.intpfyqa.gui.web.selenium.elements;

import com.intpfyqa.gui.web.selenium.BaseAutomationPage;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.elements.util.DragAndDropAction;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import com.intpfyqa.gui.web.selenium.elements.util.NoScrollToChildren;
import com.intpfyqa.gui.web.selenium.elements.util.ScrollableContainer;
import com.intpfyqa.gui.web.selenium.elements.util.matchers.ElementFromListMatcherByIndex;
import com.intpfyqa.gui.web.selenium.elements.util.matchers.SequentialMatcher;
import com.intpfyqa.gui.web.selenium.elements.wait.ExpectedElementConditions;
import com.intpfyqa.gui.web.selenium.elements.wait.WebElementWait;
import com.intpfyqa.gui.web.selenium.exceptions.ElementException;
import com.intpfyqa.gui.web.selenium.factories.WebFactoryHelper;
import com.intpfyqa.logging.ITestLogger;
import com.intpfyqa.logging.LogManager;
import com.intpfyqa.settings.WebSettings;
import com.intpfyqa.utils.TestUtils;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.util.Strings;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

/**
 * Wraps generic Web element of WebDriver
 * <p>
 * You can create an instance of this element whenever and wherever you want. The reference to an element is resolved only when it is required.
 * Reference to real element in application is not cached so we always try to re-find the desired element. This may produce unexpected behavior in specific cases
 * If you need to use static reference to real application element use {@link: Element#toRunTimeElement}
 * </p>
 */
public class Element implements IParent {

    protected static final Duration NO_TIMEOUT = Duration.ofSeconds(0);

    private final By locator;
    private final String name;
    private final ElementFromListMatcher elementsMatcher;
    private final IParent parent;
    private boolean allowBeInvisible = false;

    private boolean doNotUseScrollableForChildren = false;

    public void setDoNotUseScrollableForChildren(boolean value) {
        this.doNotUseScrollableForChildren = value;
    }

    /**
     * Element with matcher
     * <p>
     * Try to do your best to avoid using of matcher and use it only when it is really impossible to locate element using unique locator
     * Matcher always affect performance (accessing element becomes slower) and it may cause unexpected behavior for dynamic pages
     * </p>
     *
     * @param name            element logical name in application
     * @param parent          Parent (element, component, page, frame)
     * @param locator         element locator
     * @param elementsMatcher Matcher for elements collection
     */
    public Element(String name, IParent parent, By locator, ElementFromListMatcher elementsMatcher) {
        this.parent = parent;
        this.locator = locator;
        this.name = name;
        this.elementsMatcher = elementsMatcher;
    }

    /**
     * Regular element
     *
     * @param logicalName element logical name in application
     * @param parent      Parent (element, component, page, frame)
     * @param locator     element locator
     */
    public Element(String logicalName, IParent parent, By locator) {
        this(logicalName, parent, locator, null);
    }

    /**
     * Resolve reference to native Selenium WebElement within default timeout using provided locator, parent and matcher
     * <p>
     * This method always tries re-find an element, e.g. reference is not cached
     * </p>
     *
     * @return Selenium WebElement if found
     * @see WebSettings#getElementAjaxTimeout()
     */
    protected RemoteWebElement getNativeElement() {
        return getNativeElement(AJAX_TIMEOUT);
    }

    public void setCanBeInvisible(boolean value) {
        this.allowBeInvisible = value;
    }

    /**
     * Resolve reference to native Selenium WebElement within desired timeout using provided locator, parent and matcher
     * <p>
     * This method always tries re-find an element, e.g. reference is not cached
     * </p>
     *
     * @param timeout Timeout to wait
     * @return Selenium WebElement if found
     */
    protected RemoteWebElement getNativeElement(Duration timeout) {
        return (RemoteWebElement) getDefaultWait(timeout).until(ExpectedElementConditions.presenceOfElementLocated(locator, elementsMatcher));
    }

    public String getTagName() {
        trace("Get tagName");
        try {
            String tagName = getNativeElement().getTagName();
            debug("Tag name: " + tagName);
            return tagName;
        } catch (Throwable t) {
            throw new ElementException(this, "Get tagName", t);
        }
    }

    /**
     * Send keys to the element. Do not use it to fill in a field. This should be used only for irregular cases (like press Ctrl+a or so on)
     *
     * @param keys Keys to sent
     * @see WebElement#sendKeys(CharSequence...)
     */
    public void sendKeys(final CharSequence keys) {
        trace("Enter symbols " + keys);
        try {
            getNativeElement().sendKeys(keys);
            debug("Entered symbols " + keys);
        } catch (Throwable t) {
            throw new ElementException(this, "Enter symbols", t, keys);
        }
    }

    /**
     * Class attribute value of element
     *
     * @return Class attribute value of element
     * @see #getProperty(String)
     */
    public String getCSSClass() {
        return getProperty("class");
    }

    /**
     * Checks if element doesn't exists or invisible
     * <p>
     * Element does not exist if it can't be re-located using current element's parent, locator and matcher
     * </p>
     *
     * @param timeout Timeout to wait
     * @return true if element not found or invisible
     * @see #exists()
     * @see #visible(Duration)
     */
    public boolean notVisible(Duration timeout) {

        trace("Checking if not visible");

        try {
            getDefaultWait(timeout).until(
                    ExpectedElementConditions.invisibilityOfElementLocated(locator, elementsMatcher));
            debug("Not visible");
            return true;
        } catch (TimeoutException e) {
            ElementException elementException = new ElementException(this, "Waiting for element to become visible", e);
            if (elementException.isScriptError())
                throw elementException;
            debug("Visible. Searched by %s", locator);
            return false;
        }
    }

    /**
     * Checks if element doesn't exists or invisible<br/>
     * Waits within default timeout
     *
     * @return true if element not found or invisible
     */
    public boolean notVisible() {
        return notVisible(AJAX_TIMEOUT);
    }

    /**
     * Is element exists
     *
     * @return true if element could be located on page (including if element is invisible)
     */
    public boolean exists() {
        return exists(AJAX_TIMEOUT);
    }

    public boolean notExists() {
        return notExists(AJAX_TIMEOUT);
    }

    /**
     * Is element exists
     *
     * @param timeout timeout to wait
     * @return true if element could be located on page (including if element is invisible)
     */
    public boolean exists(Duration timeout) {
        trace("Checking if exists");
        try {
            getNativeElement(timeout);
            debug("Exists");
            return true;
        } catch (WebDriverException e) {
            ElementException elementException = new ElementException(this, "Waiting for element to appear on page", e);
            if (elementException.isScriptError())
                throw elementException;
            debug("Doesn't exists. Searched by %s", locator);
            return false;
        }
    }

    public boolean notExists(Duration timeout) {
        trace("Checking if not exists");
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);
        do {
            try {
                getNativeElement(Duration.ZERO);
            } catch (WebDriverException e) {
                ElementException elementException = new ElementException(this, "Waiting for element to be absent on page", e);
                if (elementException.isScriptError())
                    throw elementException;
                debug("Doesn't exists. Searched by %s", locator);
                return true;
            }
            TestUtils.sleep(100);
        } while (LocalDateTime.now().isBefore(endTime));
        debug("Exists");
        return false;
    }

    /**
     * Is element exists and visible
     *
     * @param timeout Timeout to wait in seconds
     * @return true if element exists and visible on page
     */
    public boolean visible(Duration timeout) {
        trace("Checking if visible");

        try {
            getDefaultWait(timeout).until(
                    ExpectedElementConditions.visibilityOfElementLocated(locator, elementsMatcher));
            debug("Visible");
            return true;
        } catch (WebDriverException e) {
            ElementException elementException = new ElementException(this, "Waiting for element to appear on page", e);
            if (elementException.isScriptError())
                throw elementException;
            debug("Not visible. Searched by %s", locator);
            return false;
        }
    }

    public boolean waitForVisibilityOfElementWithRefresh(Duration timeout) {
        trace("Wait test for element will presented on page with page refreshing");
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);
        do {
            if (visible()) {
                debug("Element is visible " + getLogicalName());
                return true;
            }

            getPage().refresh();
        } while (LocalDateTime.now().isBefore(endTime));

        debug("Element " + getLogicalName() + " after timeout = [" + timeout.getSeconds() + " sec] is: " + (visible() ? "visible" : "not visible"));

        return false;
    }

    /**
     * Is element exists and visible within default timeout
     *
     * @return true if element exists and visible on page
     */
    public boolean visible() {
        return visible(AJAX_TIMEOUT);
    }


    /**
     * Get element text
     *
     * @return element text
     * @see WebElement#getText()
     */
    public String getText() {
        trace("Get Text");
        requireVisible("Get Text");
        try {
            String text = getNativeElement().getText();
            debug("Text is: " + text);
            return text;
        } catch (Throwable t) {
            throw new ElementException(this, "Get text", t);
        }
    }

    public void requireVisible(String operationName, Object... args) {
//        if (allowBeInvisible) return;
//
//        if (!visible(Duration.ZERO)) {
//            if (!exists(Duration.ZERO))
//                throw new ElementException(this, operationName, new NoSuchElementException("Element not found"), args);
//            else
//                throw new ElementException(this, operationName, new ElementNotVisibleException("Element not visible"), args);
//        }
    }

    /**
     * Get element property title
     *
     * @return element title
     * @see #getProperty(String)
     */
    public String getTitle() {
        return getProperty("title");
    }

    /**
     * Wait for element become disabled
     *
     * @param timeout Timeout to wait
     * @return true if element exists and becomes disabled or invisible (but exists in DOM)
     * @see WebElement#isEnabled()
     */
    public boolean disabled(Duration timeout) {
        return waitEnabledState(timeout, false);
    }

    /**
     * Wait for element become enabled
     *
     * @param timeout Timeout to wait
     * @return true if element exists and becomes enabled and visible
     * @see WebElement#isEnabled()
     */
    public boolean enabled(Duration timeout) {
        return waitEnabledState(timeout, true);
    }

    /**
     * Wait for desired element enabled state
     * <p>
     * If element disappeared from DOM ({@link: Element#exists} throws exception
     * </p>
     *
     * @return true if element enters required state within timeout
     * @see #enabled(Duration)
     * @see #disabled(Duration)
     */
    private boolean waitEnabledState(Duration timeout, boolean expected) {
        trace("Wait for element enabled state equals to " + expected + " for " + timeout);
        try {
            LocalDateTime endTime = LocalDateTime.now().plus(timeout);
            boolean result;
            do {
                RemoteWebElement nativeElement = getNativeElement();
                result = nativeElement.isDisplayed() && nativeElement.isEnabled();

                if (result) {
                    String disabled = nativeElement.getAttribute("disabled");
                    result = disabled == null;
                    if (result) {
                        String readonly = nativeElement.getAttribute("readonly");
                        result = readonly == null;
                    }
                }

                if (result == expected) {
                    break;
                } else {
                    TestUtils.sleep(100);
                }
            } while (LocalDateTime.now().isBefore(endTime));

            if (result) {
                debug("Enabled");
            } else {
                debug("Disabled");
            }

            return result;
        } catch (Throwable e) {
            throw new ElementException(this, "Waiting for element to be enabled", e, expected, timeout);
        }
    }

    /**
     * Is element visible and enabled
     *
     * @param timeout Timeout to wait in seconds
     * @return true if element exists and visible on page
     */
    public boolean clickable(Duration timeout) {
        trace("Checking if clickable");

        try {
            getDefaultWait(timeout).until(
                    ExpectedElementConditions.elementToBeClickable(locator, elementsMatcher));
            debug("Clickable");
            return true;
        } catch (WebDriverException e) {
            ElementException elementException = new ElementException(this, "Waiting for element to be enabled for click", e);
            if (elementException.isScriptError())
                throw elementException;
            debug("Not clickable. Searched by %s", locator);
            return false;
        }
    }

    /**
     * Wait for element text will be equal to expected text.
     * Refreshes page and compares value.
     *
     * @param expectedText expected text
     * @param timeout      time for waiting
     * @return
     */
    public boolean waitWithRefreshTextIs(String expectedText, Duration timeout) {
        trace("Wait text as expected (" + expectedText + ") with page refresh");
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);
        String actual = null;
        do {
            try {
                actual = getText();
                if (null != actual && actual.equalsIgnoreCase(expectedText)) {
                    debug("Text as expected: " + actual);
                    return true;
                }
            } catch (ElementException e) {
                if (e.isScriptError())
                    throw e;
            } catch (Exception e) {
                ElementException elementException = new ElementException(this, "Waiting for text", e, expectedText);
                if (elementException.isScriptError())
                    throw elementException;
            }

            getPage().refresh();

        } while (LocalDateTime.now().isBefore(endTime));

        debug("Last read text: " + actual);

        return false;
    }

    /**
     * Wait for element text will be equal to expected text.
     * Refreshes and scrolls page and compares value.
     *
     * @param expectedText expected text
     * @param timeout      time for waiting
     * @return
     */
    public boolean waitTextWithRefreshAndScroll(String expectedText, Duration timeout) {
        trace("Wait text as expected (" + expectedText + ") with page refreshing and scrolling to element");
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);
        do {
            trace("Scroll to element");
            scrollIntoView();
            if (waitWithRefreshTextIs(expectedText, Duration.ZERO)) {
                return true;
            }
        } while (LocalDateTime.now().isBefore(endTime));
        return false;
    }

    /**
     * Is element enabled now
     *
     * @return true if element exists and visible and enabled
     * @see #enabled(Duration)
     */
    public boolean enabled() {
        return enabled(NO_TIMEOUT);
    }

    /**
     * Is element disabled now
     *
     * @return true if element exists and not visible or disabled
     * @see #disabled(Duration)
     */
    public boolean disabled() {
        return disabled(NO_TIMEOUT);
    }

    public void click() {
        trace("Click");
        try {
            getNativeElement().click();
            debug("Clicked");
        } catch (Throwable t) {
            throw new ElementException(this, "Click", t);
        }
    }

    /**
     * Click using {@link Actions#click(WebElement)}.
     */
    public void clickWithActions() {

        trace("Click with Actions");

        try {

            new Actions(getDriver())
                    .click(getNativeElement())
                    .build()
                    .perform();

            debug("Clicked");

        } catch (Throwable t) {
            throw new ElementException(this, "Click with Actions", t);
        }
    }

    public void clickAndMoveMouseOut() {
        click();
        moveMouseOut();
    }

    public Rectangle getRect() {
        trace("Get Rectangle");
        try {
            Rectangle rectangle = getNativeElement().getRect();
            debug("Got Rectangle: " + rectToString(rectangle));
            return rectangle;
        } catch (Throwable t) {
            throw new ElementException(this, "Get position and size", t);
        }
    }

    private String rectToString(Rectangle rectangle) {
        return String.format("(%s, %s), [%s x %s]", rectangle.getX(), rectangle.getY(),
                rectangle.getWidth(), rectangle.getHeight());
    }

    /**
     * Double click on element
     *
     * @see Actions#doubleClick(WebElement)
     */
    public void doubleClick() {
        trace("Double click");

        try {
            Actions action = createActions();
            action.doubleClick(getNativeElement()).perform();
            debug("Double clicked");
        } catch (Throwable t) {
            throw new ElementException(this, "Double click", t);
        }
    }


    /**
     * Clicks object executing JavaScript<br/>
     * Use this method only if default click unavailable
     */
    public void clickByJS() {

        try {

            trace("Click by JS");

            if (notVisible()) throw new ElementNotVisibleException(this.toString());

            WebElement element = getNativeElement(NO_TIMEOUT);

            String script = "";

            String tagName = element.getTagName();

            String elementType;

            try {
                elementType = element.getAttribute("type");
            } catch (WebDriverException ignore) {
                elementType = "";
            }

            if (elementType == null) elementType = "";

            if (tagName.equalsIgnoreCase("input") && elementType.equalsIgnoreCase("submitProperties")) {
                script = "arguments[0].click('onClick');";
            } else {
                script = "var evt = document.createEvent('MouseEvents');" +
                        "evt.initMouseEvent('click',true, true, window, " +
                        "0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
                        "arguments[0].dispatchEvent(evt);";
            }

            executeScriptInBrowser(script, element);
            debug("Clicked by JS");
        } catch (Throwable e) {
            throw new ElementException(this, "Click with JS", e);
        }
    }


    /**
     * Scroll element into view (at bottom of page) using JS (see documentation of W3C DOM element scrollIntoView(boolean))
     *
     * @see #scrollIntoView(boolean)
     */
    public void scrollIntoView() {
        scrollIntoView(false);
    }

    /**
     * Scroll element into view using JS (see documentation of W3C DOM element scrollIntoView(boolean))
     *
     * @param alignToTop Scroll to top of page
     */
    public void scrollIntoView(boolean alignToTop) {
        trace("Scrolling into view");
        ScrollableContainer scrollableContainer = getScrollableParent();
        if (null != scrollableContainer)
            scrollableContainer.scrollIntoView(this);
        else {
            try {
                executeScriptInBrowser("arguments[0].scrollIntoView(" + (alignToTop ? "true" : "false") + ");",
                        getNativeElement());
                debug("Scrolled Into view (align top: " + alignToTop + ")");
            } catch (Exception e) {
                throw new ElementException(this, "Scroll element to visible zone", e);
            }
        }
    }

    public ScrollableContainer getScrollableParent() {
        if (null != getParent()) {
            if (getParent() instanceof NoScrollToChildren)
                return null;

            if (getParent() instanceof Element)
                if (((Element) getParent()).doNotUseScrollableForChildren) return null;

            if (getParent() instanceof ScrollableContainer)
                return (ScrollableContainer) getParent();
            else if (getParent() instanceof Element)
                return ((Element) getParent()).getScrollableParent();
            else if (getParent() instanceof BaseComponent)
                return ((BaseComponent) getParent()).getComponentElement().getScrollableParent();
        }

        return null;
    }


    /**
     * Simulate move mouse over element event
     *
     * @see Actions#moveToElement(WebElement)
     */
    public void moveMouseIn() {
        trace("Moving mouse in");
        try {
            scrollIntoView();
            createActions().moveToElement(getNativeElement()).moveByOffset(0, 0).build().perform();
            debug("Mouse moved in");
        } catch (Exception e) {
            throw new ElementException(this, "Hover mouse", e);
        }
    }

    /**
     * Simulate move mouse out of element event
     *
     * @see Actions#moveToElement(WebElement)
     * @see Actions#moveByOffset(int, int)
     */
    public void moveMouseOut() {
        trace("Moving mouse out");
        try {
            scrollIntoView();
            createActions().moveToElement(getNativeElement()).moveByOffset(-100, -100).build().perform();
            debug("Mouse moved out");
        } catch (Exception e) {
            throw new ElementException(this, "Move mouse out from element", e);
        }
    }


    @Deprecated
    /**
     * @deprecated user {@link #getRect()}
     */
    public Dimension getSize() {
        trace("Get size");
        try {
            Dimension size = getNativeElement().getSize();
            debug("Size is: " + size);
            return size;
        } catch (Throwable t) {
            throw new ElementException(this, "Get size", t);
        }
    }

    public String getInnerHtml() {
        return getProperty("innerHTML");
    }

    /**
     * Get element's attribute value
     *
     * @param propertyName Name of attribute
     * @return Value of attribute or <code>null</code> if no such attribute
     * @see WebElement#getAttribute(String)
     */
    public String getProperty(final String propertyName) {
        trace("Get property " + propertyName);
        try {
            String value = getNativeElement().getAttribute(propertyName);
            debug(String.format("Property '%s' value is '%s'", propertyName, value));
            return value;
        } catch (Throwable t) {
            throw new ElementException(this, "Get attribute", t, propertyName);
        }
    }

    public boolean waitProperty(String propertyName, String value, Duration timeout) {
        trace("Wait property " + propertyName + " = " + value);
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);
        String lastValue = null;
        do {
            try {
                lastValue = getNativeElement().getAttribute(propertyName);
                if (lastValue != null && lastValue.equalsIgnoreCase(value)) break;
            } catch (Throwable t) {
                ElementException exception = new ElementException(this, "Expecting attribute", t, propertyName,
                        value, timeout);
                if (exception.isScriptError())
                    throw exception;
                TestUtils.sleep(100);
            }
        } while (LocalDateTime.now().isBefore(endTime));

        debug("Last value of property " + propertyName + " is " + lastValue);
        return (lastValue != null && lastValue.equalsIgnoreCase(value));
    }

    public boolean waitPropertyNot(String propertyName, String value, Duration timeout) {
        trace("Wait property " + propertyName + " != " + value);
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);
        String lastValue = null;
        do {
            try {
                lastValue = getNativeElement().getAttribute(propertyName);
                if (lastValue == null || !lastValue.equalsIgnoreCase(value)) break;
            } catch (Throwable t) {
                ElementException exception = new ElementException(this, "Waiting a property to become not equal", t, propertyName,
                        value, timeout);
                if (exception.isScriptError())
                    throw exception;
                TestUtils.sleep(100);
            }
        } while (LocalDateTime.now().isBefore(endTime));

        debug("Last value of property " + propertyName + " is " + lastValue);
        return (lastValue == null || !lastValue.equalsIgnoreCase(value));
    }

    public boolean waitPropertyEmpty(String propertyName, Duration timeout) {
        trace("Wait property [" + propertyName + "] empty");
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);
        String lastValue = null;
        do {
            try {
                lastValue = getNativeElement().getAttribute(propertyName);
                if (Strings.isNullOrEmpty(lastValue)) break;
            } catch (Throwable t) {
                ElementException exception = new ElementException(this, "Waiting attribute to be empty", t, propertyName,
                        timeout);
                if (exception.isScriptError())
                    throw exception;
                TestUtils.sleep(100);
            }
        } while (LocalDateTime.now().isBefore(endTime));

        debug("Last value of property " + propertyName + " is " + lastValue);
        return Strings.isNullOrEmpty(lastValue);
    }

    public boolean waitPropertyNotEmpty(String propertyName, Duration timeout) {
        trace("Wait property [" + propertyName + "] not empty");
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);
        String lastValue = null;
        do {
            try {
                lastValue = getNativeElement().getAttribute(propertyName);
                if (!Strings.isNullOrEmpty(lastValue)) break;
            } catch (Throwable t) {
                ElementException exception = new ElementException(this, "Waiting attribute to be non-empty", t, propertyName,
                        timeout);
                if (exception.isScriptError())
                    throw exception;
                TestUtils.sleep(100);
            }
        } while (LocalDateTime.now().isBefore(endTime));

        debug("Last value of property " + propertyName + " is " + lastValue);
        return !Strings.isNullOrEmpty(lastValue);
    }

    public boolean waitForTextNotEmpty(Duration timeout) {
        trace("Wait for text not empty");
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);
        String lastValue = null;
        do {
            try {
                lastValue = getText();
                if (!Strings.isNullOrEmpty(lastValue)) break;
            } catch (Throwable t) {
                ElementException exception = new ElementException(this, "Waiting that element text will not have empty value", t,
                        timeout);
                if (exception.isScriptError())
                    throw exception;
                TestUtils.sleep(100);
            }
        } while (LocalDateTime.now().isBefore(endTime));

        debug("Last value of text: " + lastValue);
        return !Strings.isNullOrEmpty(lastValue);
    }

    public boolean waitForTextEmpty(Duration timeout) {
        return waitForTextEquals("", timeout);
    }

    public boolean waitForValueEquals(int value, Duration timeout) {
        String stringValue = String.valueOf(value);
        trace("Wait for integer value equals to " + stringValue);
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);
        String lastValue = null;
        do {
            try {
                lastValue = removeLeadingZeroes(getText());
                if (lastValue != null && lastValue.equalsIgnoreCase(stringValue)) {
                    break;
                }
            } catch (Throwable t) {
                ElementException exception = new ElementException(this, "Waiting element integer value to be equal to " + stringValue, t,
                        timeout);
                if (exception.isScriptError())
                    throw exception;
                TestUtils.sleep(100);
            }
        } while (LocalDateTime.now().isBefore(endTime));

        debug("Last integer value: " + lastValue);
        return lastValue != null && lastValue.equalsIgnoreCase(stringValue);
    }

    public boolean waitForTextEquals(String text, Duration timeout) {
        trace("Wait for text equals to " + text);
        String lastValue = getText();
        if (Strings.isNullOrEmpty(text) && Strings.isNullOrEmpty(lastValue)) {
            return true;
        }
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);
        do {
            try {
                lastValue = getText();
                if (lastValue != null && lastValue.equalsIgnoreCase(text)) {
                    break;
                }
            } catch (Throwable t) {
                ElementException exception = new ElementException(this, "Waiting element text to have value " + text, t,
                        timeout);
                if (exception.isScriptError())
                    throw exception;
                TestUtils.sleep(100);
            }
        } while (LocalDateTime.now().isBefore(endTime));

        debug("Last value of text: " + lastValue);
        return lastValue != null && lastValue.equalsIgnoreCase(text);
    }

    public boolean waitForTextNotEquals(String text, Duration timeout) {
        trace("Wait for text not equals to " + text);
        waitForTextNotEmpty(text, timeout);
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);
        String lastValue = null;
        do {
            try {
                lastValue = getText();
                if (lastValue == null || !lastValue.equalsIgnoreCase(text)) break;
            } catch (Throwable t) {
                ElementException exception = new ElementException(this, "Waiting element text to have value different from " + text, t,
                        timeout);
                if (exception.isScriptError())
                    throw exception;
                TestUtils.sleep(100);
            }
        } while (LocalDateTime.now().isBefore(endTime));

        debug("Last value of text: " + lastValue);
        return lastValue == null || !lastValue.equalsIgnoreCase(text);
    }

    private void waitForTextNotEmpty(String text, Duration timeout) {
        if (Strings.isNullOrEmpty(text)) {
            waitForTextNotEmpty(timeout);
        }
    }

    public boolean waitForTextNotContains(String text, Duration timeout) {
        trace("Wait for text not contains " + text);
        waitForTextNotEmpty(text, timeout);
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);
        String lastValue = null;
        do {
            try {
                lastValue = getText();
                if (!StringUtils.containsIgnoreCase(lastValue, text)) break;
            } catch (Throwable t) {
                ElementException exception = new ElementException(this, "Waiting element text to contain value different from " + text, t,
                        timeout);
                if (exception.isScriptError())
                    throw exception;
                TestUtils.sleep(100);
            }
        } while (LocalDateTime.now().isBefore(endTime));

        debug("Last value of text: " + lastValue);
        return !StringUtils.containsIgnoreCase(lastValue, text);
    }

    public boolean waitForTextContains(String text, Duration timeout) {
        trace("Wait for text contains " + text);
        waitForTextNotEmpty(text, timeout);
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);
        String lastValue = null;
        do {
            try {
                lastValue = getText();
                if (StringUtils.containsIgnoreCase(lastValue, text)) break;
            } catch (Throwable t) {
                ElementException exception = new ElementException(this, "Waiting element text to contain value different from " + text, t,
                        timeout);
                if (exception.isScriptError())
                    throw exception;
                TestUtils.sleep(100);
            }
        } while (LocalDateTime.now().isBefore(endTime));

        debug("Last value of text: " + lastValue);
        return StringUtils.containsIgnoreCase(lastValue, text);
    }

    /**
     * Get CSS value of element
     *
     * @param propertyName Property name
     * @return Property value of <code>null</code> if no such property
     * @see WebElement#getCssValue(String)
     */
    public String getCssValue(String propertyName) {
        trace("Get value of css property " + propertyName);
        try {
            String prop = getNativeElement().getCssValue(propertyName);
            debug("Css property " + propertyName + " value: " + prop);
            return prop;
        } catch (Throwable t) {
            throw new ElementException(this, "Get css value of attribute", t, propertyName);
        }
    }

    public void dragAndDrop(Element targetElement) {
        dragAndDrop(DragAndDropAction.toMiddleOfElement(targetElement));
    }

    public void dragAndDropBelow(Element targetElement) {
        dragAndDrop(DragAndDropAction.toBottomOfElement(targetElement));
    }

    public void dragAndDropAbove(Element targetElement) {
        dragAndDrop(DragAndDropAction.toTopOfElement(targetElement));
    }

    public void dragAndDropRight(Element targetElement) {
        dragAndDrop(DragAndDropAction.toRightOfElement(targetElement));
    }

    public void dragAndDropLeft(Element targetElement) {
        dragAndDrop(DragAndDropAction.toLeftOfElement(targetElement));
    }

    /**
     * Drag and drop element. Element to dragged must be visible on screen. Target position (drop) also should be visible on screen
     *
     * @param action Drag and drop definition
     * @see Actions#moveToElement(WebElement)
     * @see Actions#clickAndHold(WebElement)
     * @see Actions#moveByOffset(int, int)
     */
    public void dragAndDrop(DragAndDropAction action) {
        trace("Drag and drop element in way: " + action);
        Point targetPoint = action.calcTargetPoint();
        try {

            Rectangle rectangle = this.getRect();
            Point currentLocation = rectangle.getPoint();
            Dimension currentSize = rectangle.getDimension();

            currentLocation = currentLocation.moveBy(currentSize.getWidth() / 2, currentSize.getHeight() / 2);

            trace("Drag and drop from point " + currentLocation + " to location " + targetPoint + " with offset\n" +
                    "xOffset: " + (targetPoint.getX() - currentLocation.getX()) + "\n" +
                    "yOffset: " + (targetPoint.getY() - currentLocation.getY()));

            Actions actions = createActions();
            actions.clickAndHold(getNativeElement());
            actions.perform();

            actions = createActions();
            actions.moveToElement(getNativeElement()).perform();
            TestUtils.sleep(500);
            createActions().moveByOffset(0, 0).perform();
            TestUtils.sleep(500);
            createActions().moveByOffset(targetPoint.getX() - currentLocation.getX(),
                    targetPoint.getY() - currentLocation.getY()).perform();
            TestUtils.sleep(1000);

            actions = createActions();
            actions.release();
            actions.perform();

            debug("Dragged and dropped " + action);

        } catch (WebDriverException | ElementException e) {
            throw new ElementException(this, "Drag and drop", e, action);
        }
    }

    /**
     * Right click on element
     *
     * @see Actions#contextClick(WebElement)
     */
    public void rightClick() {

        trace("Right click");

        try {
            Actions actions = createActions();
            actions.contextClick(getNativeElement());
            actions.perform();

            debug("Right clicked");
        } catch (WebDriverException | ElementException e) {
            throw new ElementException(this, "Right click", e);
        }
    }


    /**
     * @deprecated user {@link #getRect()}
     */
    @Deprecated
    public Point getLocation() {
        trace("Get Location");
        try {
            Point point = getNativeElement().getLocation();
            debug("Location is: " + point);
            return point;
        } catch (Throwable t) {
            throw new ElementException(this, "Get coordinates", t);
        }
    }

    public boolean waitCssClassContains(String contains, Duration timeout) {
        trace("Wait css class contains " + contains);
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);

        String cssClass = null;

        do {
            try {
                cssClass = getNativeElement().getAttribute("class");
                if (cssClass.toLowerCase().contains(contains.toLowerCase())) {
                    break;
                }
            } catch (ElementException e) {
                if (e.isScriptError()) throw e;
            } catch (WebDriverException e) {
                ElementException elementException = new ElementException(this, "Waiting for css Class to contain", e,
                        contains, timeout);
                if (elementException.isScriptError()) throw elementException;
            }
        } while (LocalDateTime.now().isBefore(endTime));

        debug("Last css class: " + cssClass);

        return cssClass != null && cssClass.toLowerCase().contains(contains.toLowerCase());
    }

    public boolean waitCssClassNotContains(String contains, Duration timeout) {
        trace("Wait css class not contains " + contains);
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);

        String cssClass = null;

        do {
            try {
                cssClass = getNativeElement().getAttribute("class");
                if (!cssClass.toLowerCase().contains(contains.toLowerCase())) {
                    break;
                }
            } catch (ElementException e) {
                if (e.isScriptError()) throw e;
            } catch (WebDriverException e) {
                ElementException elementException = new ElementException(this, "Waiting for css Class to not contain", e,
                        contains, timeout);
                if (elementException.isScriptError()) throw elementException;
            }
        } while (LocalDateTime.now().isBefore(endTime));

        debug("Last css class: " + cssClass);

        return cssClass != null && !cssClass.toLowerCase().contains(contains.toLowerCase());
    }

    /**
     * Collect child elements of this element
     * <p>
     * Try to not use this method: it is slow enough
     * Result collection of elements contains same Element wrappers and each of them has own matcher by index
     * </p>
     *
     * @param by       Locator of children
     * @param tClass   Cast to class
     * @param maxCount Max size of collection, negative means all possible
     * @param <T>      Class
     * @return Collection of found elements
     * @see WebElement#findElements(By)
     */
    public <T extends Element> java.util.List<T> children(By by, Class<T> tClass, int maxCount) {
        return children(by, tClass, maxCount, null);
    }

    /**
     * Collect child elements of this element
     * <p>
     * Try to not use this method: it is slow enough
     * Result collection of elements contains same Element wrappers and each of them has own matcher by index
     * </p>
     *
     * @param by       Locator of children
     * @param tClass   Cast to class
     * @param maxCount Max size of collection, negative means all possible
     * @param matcher  collect only elements matching this matcher
     * @param <T>      Class
     * @return Collection of found elements
     * @see WebElement#findElements(By)
     */
    public <T extends Element> java.util.List<T> children(By by, Class<T> tClass, int maxCount,
                                                          ElementFromListMatcher matcher) {
        trace("Children by " + by + " as " + tClass + " of max count " + maxCount + " with matcher " +
                matcher);
        try {
            java.util.List<WebElement> elementList = getNativeElement().findElements(by);

            if (null != matcher) {
                elementList = matcher.getAllMatched(elementList);
            }

            java.util.List<T> result = new ArrayList<>();
            int count = elementList.size() < maxCount || maxCount < 1 ? elementList.size() : maxCount;

            for (int i = 0; i < count; i++) {
                ElementFromListMatcher finalMatcher = new ElementFromListMatcherByIndex(i + 1);

                if (null != matcher) {
                    finalMatcher = new SequentialMatcher(matcher).then(finalMatcher);
                }
                T element = WebFactoryHelper.getElementFactory().createElement(tClass, this,
                        getLogicalName() + " child element #" + (i + 1),
                        by, finalMatcher);
                result.add(element);
            }
            debug("Found totally " + result.size() + " children");
            return result;
        } catch (Throwable t) {
            throw new ElementException(this, "Getting of child elements", t, by, tClass, maxCount, matcher);
        }
    }

    /**
     * Collect all child elements
     *
     * @param by Locator
     * @return Collection of children
     * @see #children(By, Class, int)
     */
    public java.util.List<Element> children(By by) {
        return children(by, Element.class, -1);
    }

    /**
     * Collect all child elements
     *
     * @param by      Locator
     * @param matcher collect only elements matching this matcher
     * @return Collection of children
     * @see #children(By, Class, int, ElementFromListMatcher)
     */
    public java.util.List<Element> children(By by, ElementFromListMatcher matcher) {
        return children(by, Element.class, -1, matcher);
    }

    /**
     * Collect all child elements
     *
     * @param by     Locator
     * @param tClass Cast elements to class
     * @return Collection of children
     * @see #children(By, Class, int)
     */
    public <T extends Element> java.util.List<T> children(By by, Class<T> tClass) {
        return children(by, tClass, -1);
    }

    /**
     * Collect child elements
     *
     * @param by       Locator
     * @param maxCount Max size of result collection, negative means all possible elements
     * @return Collection of children
     * @see #children(By, Class, int)
     */
    public java.util.List<Element> children(By by, int maxCount) {
        return children(by, Element.class, maxCount);
    }

    /**
     * Simplified way of creation of child element
     *
     * @param name    Name of Element in application
     * @param locator Locate by
     * @return Element where this element set as parent
     * @see #Element(String, IParent, By)
     */
    public Element createChild(String name, By locator) {
        return WebFactoryHelper.getElementFactory().createElement(Element.class, name, locator, this);
    }

    /**
     * Execute JavaScript code in current browser context (window, or frame)
     *
     * @param script Script to be executed
     * @param args   Args to pass into executor
     * @return Result of script
     * @see JavascriptExecutor#executeScript(String, Object...)
     */
    protected Object executeScriptInBrowser(String script, Object... args) {
        return getPage().getPageContext().getBrowserWindow().executeScript(script, args);
    }


    protected RemoteWebDriver getDriver() {
        return getPage().getPageContext().getDriver();
    }

    protected Actions createActions() {
        return new Actions(getDriver());
    }


    /**
     * Provides default wait instance to wait for condition
     *
     * @return WebDriverWait if parentLocator and parentElement are not specified, WebElementWait else
     */
    protected WebElementWait getDefaultWait() {
        return getDefaultWait(AJAX_TIMEOUT);
    }

    /**
     * Provides default wait instance to wait for condition
     *
     * @param timeout Timeout to wait
     * @return WebDriverWait if parentLocator and parentElement are not specified, WebElementWait else
     */
    protected WebElementWait getDefaultWait(Duration timeout) {
        WebElementWait webElementWait = (WebElementWait) new WebElementWait(getParent(), timeout)
                .ignoring(NoSuchElementException.class, ParentNotFoundException.class)
                .ignoring(StaleElementReferenceException.class);

        if (isInIE())
            webElementWait.ignoring(JavascriptException.class);

        return webElementWait;
    }

    public IParent getParent() {
        return parent;
    }

    public BaseAutomationPage getPage() {
        if (parent instanceof BaseAutomationPage) {
            return (BaseAutomationPage) parent;
        } else {
            return parent.getPage();
        }
    }

    public SearchContext getSearchContext() {
        return getNativeElement();
    }

    public By getLocator() {
        return locator;
    }

    protected ElementFromListMatcher getElementsMatcher() {
        return elementsMatcher;
    }

    public String getLogicalName() {
        return name;
    }

    /**
     * Provides current class name (for example, Element)
     *
     * @return Current class name
     */
    private String getThisClassName() {
        return getClass().getSimpleName();
    }

    public String locateString() {
        return String.format("%s->[%s%s]", getParent().locateString(), locator,
                (null != elementsMatcher ? (", and matches condition " + elementsMatcher.describeCondition()) : ""));
    }

    /**
     * Is current browser IE
     *
     * @return <code>true</code> if browser session of this element is of IE instance
     */
    protected boolean isInIE() {
        return getPage().getPageContext().getBrowserWindow().getSession().isIE();
    }

    /**
     * Is current browser FF
     *
     * @return <code>true</code> if browser session of this element is of firefox instance
     */
    protected boolean isInFF() {
        return getPage().getPageContext().getBrowserWindow().getSession().isFF();
    }

    protected ITestLogger getLogger() {
        return LogManager.getCurrentTestLogger();
    }


    /**
     * Logs component level message
     *
     * @param formattedMessage Message to debug
     * @param args             format args
     * @see String#format(String, Object...)
     */
    protected void debug(String formattedMessage, Object... args) {
        if (args == null || args.length < 1) {
            getLogger().debug(this.toString(), formattedMessage);
        } else {
            getLogger().debug(this.toString(), String.format(formattedMessage, args));
        }
    }

    /**
     * Logs trace level message
     *
     * @param formattedMessage Message to debug
     * @param args             format args
     * @see String#format(String, Object...)
     */
    protected void trace(String formattedMessage, Object... args) {
        if (args == null || args.length < 1) {
            getLogger().trace(this.toString(), formattedMessage);
        } else {
            getLogger().trace(this.toString(), String.format(formattedMessage, args));
        }
    }

    public String toString() {
        return getLogicalName() + " " + getThisClassName();
    }

    public <T extends Element> T as(Class<T> tClass) {
        if (null != getElementsMatcher()) {
            return WebFactoryHelper.getElementFactory().createElement(tClass, getLogicalName(), getParent(), getLocator(),
                    getElementsMatcher());
        } else {
            return WebFactoryHelper.getElementFactory().createElement(tClass, getLogicalName(), getParent(), getLocator());
        }
    }

    /**
     * Create runtime link of this element (element should exists at current moment)
     *
     * @return Runtime element
     */
    public RuntimeElement toRunTimeElement() {
        trace("Create runtime element");
        try {
            RuntimeElement element = new RuntimeElement(toString(), getNativeElement());
            debug("Runtime element created");
            return element;
        } catch (Throwable t) {
            throw new ElementException(this, "Create Runtime element", t);
        }
    }

    /**
     * Removes leading zeroes, but leaves one if necessary (i.e. it wouldn't just turn "0" to a blank string).
     *
     * @return <code>null</code> if passed string is equal to <code>null</code>.
     */
    private String removeLeadingZeroes(String str) {
        return str == null ? null : str.replaceFirst("^0+(?!$)", "");
    }
}
