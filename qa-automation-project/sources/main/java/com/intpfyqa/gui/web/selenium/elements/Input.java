package com.intpfyqa.gui.web.selenium.elements;

import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import com.intpfyqa.gui.web.selenium.exceptions.ElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.util.Strings;

/**
 * UI Edit box
 */
public class Input extends Element {

    public Input(String logicalName, IParent parent, By locator) {
        super(logicalName, parent, locator);
    }

    public Input(String logicalName, IParent parent, By locator, ElementFromListMatcher elementFromListMatcher) {
        super(logicalName, parent, locator, elementFromListMatcher);
    }

    public String getValue() {
        return getText();
    }

    public String getTextAndHideValueInLog() {
        return getText(true);
    }

    public String getText() {
        return getText(false);
    }

    /**
     * Get edit box text
     *
     * @return value of property "value"
     */
    private String getText(boolean hideValueInLog) {
        requireVisible("Get Text");
        try {
            trace("Getting text");
            String text = getNativeElement().getAttribute("value");
            if (text == null)
                text = "";
            if (hideValueInLog)
                debug("Got text %s", "********");
            else
                debug("Got text %s", text);
            return text;
        } catch (Throwable e) {
            throw new ElementException(this, "Get text", e);
        }
    }

    /**
     * Inputs text in text box
     *
     * @param text text to input
     */
    public void setText(String text) {
        requireVisible("Enter text", text);
        trace("Set text " + text);
        try {
            RemoteWebElement element = getNativeElement();
            element.clear();
            element.sendKeys(text);
            debug("Set text " + text);
        } catch (Throwable t) {
            throw new ElementException(this, "Enter text", t, text);
        }
    }

    public void setText(int number) {
        requireVisible("Enter text", number);
        trace("Set text " + number);
        try {
            RemoteWebElement element = getNativeElement();
            element.clear();
            element.sendKeys(number + "");
            debug("Set text " + number);
        } catch (Throwable t) {
            throw new ElementException(this, "Enter text", t, number);
        }
    }

    /**
     * Clear edit box
     */
    public void clear() {
        requireVisible("Clear");
        trace("Clear");
        try {
            WebElement element = getNativeElement();
            element.clear();
            debug("Cleared");
        } catch (Throwable e) {
            throw new ElementException(this, "Clear", e);
        }
    }

    public void clearByCtrlA() {
        requireVisible("Clear");
        trace("Clear by Ctrl+a");
        try {
            WebElement element = getNativeElement();
            element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            element.sendKeys(Keys.DELETE);
            debug("Cleared");
        } catch (Throwable e) {
            throw new ElementException(this, "Clear with Crtl+a", e);
        }
    }

    /**
     * Inputs text in text box (text will not be reported to log)
     *
     * @param text text to input
     */
    public void setSecret(String text) {
        requireVisible("Enter text", "******");
        String textToLog = "*******";
        trace("Set text " + textToLog);
        try {
            RemoteWebElement element = getNativeElement();
            element.clear();
            element.sendKeys(text);
            debug("Set text " + textToLog);
        } catch (Throwable t) {
            throw new ElementException(this, "Enter text", t, textToLog);
        }
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(getText());
    }
}