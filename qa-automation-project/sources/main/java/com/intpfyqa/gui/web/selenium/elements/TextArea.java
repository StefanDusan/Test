package com.intpfyqa.gui.web.selenium.elements;

import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import com.intpfyqa.gui.web.selenium.exceptions.ElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

/**
 * Text area element
 */
public class TextArea extends Element {

    public TextArea(String logicalName, IParent page, By locator) {
        super(logicalName, page, locator);
    }

    public TextArea(String logicalName, IParent page, By locator, ElementFromListMatcher matcher) {
        super(logicalName, page, locator, matcher);
    }

    public String getValue() {
        return getText();
    }

    public void appendText(String text) {
        privateSet(text, false);
    }

    private void privateSet(String text, boolean withClear) {
        trace("Setting text %s", text);
        requireVisible("Enter text", text);
        try {
            WebElement element = getNativeElement();
            if (withClear) {
                element.clear();
            }
        } catch (TimeoutException e) {
            throw new ElementException(this, "Enter text", e, text);
        }
        try {
            getNativeElement().sendKeys(text);
            debug("Set text %s", text);
        } catch (Exception e) {
            throw new ElementException(this, "Enter text", e, text);
        }
    }

    public String getText() {
        trace("Getting text");
        requireVisible("Get Text");
        try {
            //String text = getNativeElement().getText();
            String text = getNativeElement().getAttribute("value");
            if (null == text)
                text = getNativeElement().getText();
            debug("Got text '%s'", text);
            return text;
        } catch (WebDriverException e) {
            throw new ElementException(this, "Get text", e);
        }
    }

    public void setText(String text) {
        privateSet(text, true);
    }
}