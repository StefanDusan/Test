package com.intpfyqa.gui.web.selenium;

import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import com.intpfyqa.gui.web.selenium.elements.util.matchers.ElementFromListMatcherByIndex;
import com.intpfyqa.gui.web.selenium.elements.util.matchers.SequentialMatcher;
import com.intpfyqa.gui.web.selenium.exceptions.ElementException;
import com.intpfyqa.gui.web.selenium.factories.IComponentFactory;
import com.intpfyqa.gui.web.selenium.factories.IElementFactory;
import com.intpfyqa.gui.web.selenium.factories.WebFactoryHelper;
import com.intpfyqa.logging.ITakeSnapshot;
import com.intpfyqa.logging.LogManager;
import com.intpfyqa.settings.WebSettings;
import com.intpfyqa.test.Verify;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;
import java.util.ArrayList;

public abstract class BaseAutomationPage implements IParent {

    private static final IElementFactory elementFactory = WebFactoryHelper.getElementFactory();
    private static final IComponentFactory componentFactory = WebFactoryHelper.getComponentFactory();

    private final IPageContext pageContext;
    private final String name;

    protected BaseAutomationPage(String name, IPageContext pageContext) {
        this.name = name;
        this.pageContext = pageContext;
        WebFactoryHelper.getElementDecorator().decoratePage(this, elementFactory, componentFactory);
    }

    public SearchContext getSearchContext() {
        return pageContext.getDriver();
    }

    public String toString() {
        return name;
    }

    public void assertIsOpened(Duration timeout) {
        boolean opened = isOpened(timeout);
        if (opened) {
            pageContext.getBrowserWindow().setActivePage(this);
        }
        Verify.assertTrue(opened, "Page " + name + " opened");
    }

    public void assertIsOpened() {
        assertIsOpened(WebSettings.instance().getPageTimeout());
    }

    public void assertNotOpened() {
        assertNotOpened(WebSettings.instance().getPageTimeout());
    }

    private void assertNotOpened(Duration timeout) {
        Verify.assertFalse(isOpened(timeout), "Page " + name + " not opened");
    }

    public abstract boolean isOpened(Duration timeout);

    public boolean isOpened() {
        return isOpened(WebSettings.instance().getPageTimeout());
    }

    public BaseAutomationPage getPage() {
        return this;
    }

    public IPageContext getPageContext() {
        return pageContext;
    }

    public String locateString() {
        return "Page " + name + " (" + getPageContext().toString() + ")";
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
    protected <T extends Element> java.util.List<T> children(By by, Class<T> tClass, int maxCount) {
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
    protected <T extends Element> java.util.List<T> children(By by, Class<T> tClass, int maxCount,
                                                             ElementFromListMatcher matcher) {
        debug("Children by " + by + " as " + tClass + " of max count " + maxCount + " with matcher " +
                matcher);
        try {
            java.util.List<WebElement> elementList = getPageContext().getDriver().findElements(by);

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
                        this + " child element #" + (i + 1),
                        by, finalMatcher);
                result.add(element);
            }
            debug("Found totally " + result.size() + " children");
            return result;
        } catch (Throwable t) {
            throw new ElementException(createPageElement(), "Getting child elements", t, by, tClass, maxCount, matcher);
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
     * @see Element(String, IParent, By)
     */
    public Element createChild(String name, By locator) {
        return WebFactoryHelper.getElementFactory().createElement(Element.class, name, locator, this);
    }

    protected void info(String message) {
        LogManager.getCurrentTestLogger().info(toString(), message);
    }

    protected void info(String message, ITakeSnapshot takeSnapshot) {
        LogManager.getCurrentTestLogger().info(toString(), message, takeSnapshot);
    }

    protected void debug(String message) {
        LogManager.getCurrentTestLogger().debug(toString(), message);
    }

    protected void debug(String message, ITakeSnapshot takeSnapshot) {
        LogManager.getCurrentTestLogger().debug(toString(), message, takeSnapshot);
    }

    protected Element createPageElement() {
        return WebFactoryHelper.getElementFactory().createElement(Element.class, toString(), this, By.tagName("body"));
    }

    public void refresh() {
        debug("Refresh page");
        getPageContext().getBrowserWindow().refresh();
        isOpened();
    }

    public void navigateBack() {
        debug("Navigate back.");
        getDriver().navigate().back();
        isOpened();
    }

    protected void executeScript(String script) {
        getPageContext().getBrowserWindow().executeScript(script);
    }

    protected void switchToIFrame(BaseIFramePage page) {
        debug(String.format("Switch to IFrame '%s'.", page.getIframeLocator()));
        RemoteWebDriver driver = getDriver();
        WebElement element = driver.findElement(page.getIframeLocator());
        driver.switchTo().frame(element);
    }

    protected void switchToDefaultContent() {
        debug("Switch to Default content.");
        getDriver().switchTo().defaultContent();
    }

    private RemoteWebDriver getDriver() {
        return getPageContext().getDriver();
    }
}
