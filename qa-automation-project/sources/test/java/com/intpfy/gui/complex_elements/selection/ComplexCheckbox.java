package com.intpfy.gui.complex_elements.selection;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.selection.checkbox.Checkbox;
import com.intpfyqa.gui.web.selenium.elements.selection.checkbox.ICheckbox;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

/**
 * Complex checkbox consisting from checkbox and label
 */
public class ComplexCheckbox extends BaseComponent implements ICheckbox {

    @ElementInfo(name = "Label", findBy = @FindBy(css = "label, span"))
    private Element label;

    @ElementInfo(name = "Inner", findBy = @FindBy(css = "input[type='checkbox']"))
    private Checkbox checkbox;

    public ComplexCheckbox(String logicalName, IParent parent, By locator) {
        super(logicalName, parent, locator);
    }

    public ComplexCheckbox(String logicalName, IParent page, By locator, ElementFromListMatcher matcher) {
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

    @Override
    public boolean isSelected() {
        return checkbox.isSelected();
    }

    @Override
    public boolean waitIsSelected(Duration timeout) {
        return checkbox.waitIsSelected(timeout);
    }

    @Override
    public boolean waitIsNotSelected(Duration timeout) {
        return checkbox.waitIsNotSelected(timeout);
    }

    public String getLabel() {
        return label.getText();
    }

    private void setState(boolean state) {
        debug("Set state: " + state);
        checkbox.scrollIntoView();
        if (checkbox.isSelected() != state) {
            label.moveMouseIn();
            label.clickWithActions();
        }
    }
}
