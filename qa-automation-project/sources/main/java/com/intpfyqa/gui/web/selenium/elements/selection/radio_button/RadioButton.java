package com.intpfyqa.gui.web.selenium.elements.selection.radio_button;

import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.elements.selection.BaseSelectionElement;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import org.openqa.selenium.By;

/**
 * RadioButton
 */
public class RadioButton extends BaseSelectionElement implements IRadioButton {

    public RadioButton(String logicalName, IParent parent, By locator) {
        super(logicalName, parent, locator);
    }

    public RadioButton(String logicalName, IParent page, By locator, ElementFromListMatcher matcher) {
        super(logicalName, page, locator, matcher);
    }

    @Override
    public void select() {
        requireVisible("Select");
        trace("Select radiobutton");
        if (!isSelected()) {
            click();
        }
        debug("Radiobutton is selected");
    }
}
