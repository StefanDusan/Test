package com.intpfyqa.gui.web.selenium.elements;

import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import org.openqa.selenium.By;

/**
 * UI Link
 */
public class Link extends Element {

    public Link(String logicalName, IParent page, By locator) {
        super(logicalName, page, locator);
    }

    public Link(String logicalName, IParent page, By locator, ElementFromListMatcher matcher) {
        super(logicalName, page, locator, matcher);
    }


    public String getHref() {
        return getProperty("href");
    }
}