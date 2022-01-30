package com.intpfyqa.gui.web.selenium.elements.selection.checkbox;

import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.elements.selection.BaseSelectionElement;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import com.intpfyqa.gui.web.selenium.exceptions.ElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;

/**
 * Checkbox
 */
public class Checkbox extends BaseSelectionElement implements ICheckbox {

    public Checkbox(String logicalName, IParent parent, By locator) {
        super(logicalName, parent, locator);
    }

    public Checkbox(String logicalName, IParent page, By locator, ElementFromListMatcher matcher) {
        super(logicalName, page, locator, matcher);
    }

    @Override
    public void select() {
        setState(true);
    }

    @Override
    public void unselect() {
        setState(false);
    }

    private void setState(boolean state) {

        trace("Set state: " + state);

        if (isSelected() != state) {
            click();
        }

        debug("Set state: " + state);
    }
}
