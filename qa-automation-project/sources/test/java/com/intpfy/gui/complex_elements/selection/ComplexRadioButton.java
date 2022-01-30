package com.intpfy.gui.complex_elements.selection;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.selection.radio_button.IRadioButton;
import com.intpfyqa.gui.web.selenium.elements.selection.radio_button.RadioButton;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

/**
 * Complex radio button consisting from radio button and label
 */
public class ComplexRadioButton extends BaseComponent implements IRadioButton {

    @ElementInfo(name = "Label", findBy = @FindBy(css = "label"))
    private Element label;

    @ElementInfo(name = "Radio button", findBy = @FindBy(css = "input[type='radio']"))
    private RadioButton radioButton;

    public ComplexRadioButton(String logicalName, IParent parent, By locator) {
        super(logicalName, parent, locator);
    }

    public ComplexRadioButton(String logicalName, IParent page, By locator, ElementFromListMatcher matcher) {
        super(logicalName, page, locator, matcher);
    }

    @Override
    public void select() {
        debug("Select.");
        if (!radioButton.isSelected()) {
            label.moveMouseIn();
            label.click();
        }
    }

    @Override
    public boolean isSelected() {
        return radioButton.isSelected();
    }

    @Override
    public boolean waitIsSelected(Duration timeout) {
        return radioButton.isSelected();
    }

    @Override
    public boolean waitIsNotSelected(Duration timeout) {
        return radioButton.waitIsNotSelected(timeout);
    }

    public String getLabel() {
        return label.getText();
    }
}
