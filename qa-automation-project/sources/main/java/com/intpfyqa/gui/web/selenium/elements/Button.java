package com.intpfyqa.gui.web.selenium.elements;

import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import org.openqa.selenium.By;

/**
 * UI Button
 */
public class Button extends Element {

    public Button(String logicalName, IParent parent, By locator) {
        super(logicalName, parent, locator);
    }

    public Button(String logicalName, IParent parent, By locator, ElementFromListMatcher matcher) {
        super(logicalName, parent, locator, matcher);
    }
}