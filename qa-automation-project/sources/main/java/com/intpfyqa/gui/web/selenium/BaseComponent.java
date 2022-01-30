package com.intpfyqa.gui.web.selenium;

import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import com.intpfyqa.gui.web.selenium.factories.WebFactoryHelper;
import com.intpfyqa.logging.ITakeSnapshot;
import com.intpfyqa.logging.LogManager;
import com.intpfyqa.settings.WebSettings;
import com.intpfyqa.test.Verify;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.SearchContext;

import java.time.Duration;

public abstract class BaseComponent implements IParent {

    private final Element componentElement;

    protected BaseComponent(String name, IParent parent, By componentLocator) {
        componentElement = WebFactoryHelper.getElementFactory().createElement(Element.class, name, parent, componentLocator);
        WebFactoryHelper.getElementDecorator().decorateComponent(this, WebFactoryHelper.getElementFactory(), WebFactoryHelper.getComponentFactory());
    }

    protected BaseComponent(String name, IParent parent, By componentLocator, ElementFromListMatcher matcher) {
        componentElement = WebFactoryHelper.getElementFactory().createElement(Element.class, name, parent, componentLocator, matcher);
        WebFactoryHelper.getElementDecorator().decorateComponent(this, WebFactoryHelper.getElementFactory(), WebFactoryHelper.getComponentFactory());
    }

    protected BaseComponent(Element element) {
        componentElement = element;
        WebFactoryHelper.getElementDecorator().decorateComponent(this, WebFactoryHelper.getElementFactory(), WebFactoryHelper.getComponentFactory());
    }

    public SearchContext getSearchContext() {
        return componentElement.getSearchContext();
    }

    public BaseAutomationPage getPage() {
        return componentElement.getPage();
    }

    public boolean visible(Duration timeout) {
        return componentElement.visible(timeout);
    }

    public boolean visible() {
        return componentElement.visible();
    }

    public boolean waitForVisibilityWithRefresh(Duration timeout) {
        return componentElement.waitForVisibilityOfElementWithRefresh(timeout);
    }

    public boolean notVisible(Duration timeout) {
        return componentElement.notVisible(timeout);
    }

    public boolean notVisible() {
        return componentElement.notVisible();
    }

    public Element getComponentElement() {
        return componentElement;
    }

    public String locateString() {
        return getComponentElement().locateString();
    }

    protected IParent getParent() {
        return getComponentElement().getParent();
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

    public void assertIsOpened(Duration timeout) {
        Verify.assertTrue(visible(timeout), "Component " + componentElement.getLogicalName() + " opened");
    }

    public void assertNotVisible(Duration timeout) {
        Verify.assertTrue(notVisible(timeout), "Component " + componentElement.getLogicalName() + " not visible");
    }

    public void assertNotVisible() {
        assertNotVisible(WebSettings.instance().getPageTimeout());
    }

    public void assertIsOpened() {
        assertIsOpened(WebSettings.instance().getPageTimeout());
    }

    @Override
    public String toString() {
        return getComponentElement().getLogicalName();
    }

    public Rectangle getRect() {
        return getComponentElement().getRect();
    }

    protected void failCurrentTest(String logMessage) {
        LogManager.getCurrentTestLogger().error(getComponentElement().getLogicalName(), logMessage);
    }

    public void clickAndMoveMouseOut() {
        componentElement.clickAndMoveMouseOut();
    }
}
