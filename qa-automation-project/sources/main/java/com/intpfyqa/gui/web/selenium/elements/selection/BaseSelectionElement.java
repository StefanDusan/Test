package com.intpfyqa.gui.web.selenium.elements.selection;

import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import com.intpfyqa.gui.web.selenium.exceptions.ElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;

import java.time.Duration;

public abstract class BaseSelectionElement extends Element {

    public BaseSelectionElement(String logicalName, IParent parent, By locator) {
        super(logicalName, parent, locator);
    }

    public BaseSelectionElement(String logicalName, IParent page, By locator, ElementFromListMatcher matcher) {
        super(logicalName, page, locator, matcher);
    }

    public boolean isSelected() {

        trace("Checking is selected");

        try {
            boolean isSelected = getNativeElement().isSelected();

            debug(isSelected ? "Selected" : "Not selected");

            return isSelected;

        } catch (WebDriverException e) {
            throw new ElementException(this, String.format("Check if '%s' selected", getLogicalName()), e);
        }
    }

    public boolean waitIsSelected(Duration timeout) {

        try {
            trace("Wait is selected");

            boolean res = getDefaultWait(timeout)
                    .ignoring(StaleElementReferenceException.class)
                    .until(driver -> isSelected());

            debug(res ? "Selected" : " NOT Selected");

            return res;

        } catch (WebDriverException ignore) {
            debug("NOT Selected");
            return false;
        }
    }

    public boolean waitIsNotSelected(Duration timeout) {

        try {
            trace("Wait is not selected");

            boolean res = getDefaultWait(timeout)
                    .ignoring(StaleElementReferenceException.class)
                    .until(driver -> !isSelected());

            debug(res ? "Not selected" : "Selected");

            return res;

        } catch (WebDriverException ignore) {
            debug("Selected");
            return false;
        }
    }
}
