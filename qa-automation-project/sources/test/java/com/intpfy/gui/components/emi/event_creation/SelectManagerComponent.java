package com.intpfy.gui.components.emi.event_creation;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfy.gui.complex_elements.selection.ComplexRadioButton;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class SelectManagerComponent extends BaseComponent {

    @ElementInfo(name = "Select Manager radio button", findBy = @FindBy(xpath = ".//div[./input[@id='manager-search-radio']]"))
    private ComplexRadioButton selectRadioButton;

    @ElementInfo(name = "New Manager radio button", findBy = @FindBy(xpath = ".//div[./input[@id='new-manager-radio']]"))
    private ComplexRadioButton newRadioButton;

    @ElementInfo(name = "No Manager radio button", findBy = @FindBy(xpath = ".//div[./input[@id='no-manager-radio']]"))
    private ComplexRadioButton noRadioButton;

    public SelectManagerComponent(IParent parent) {
        super("Select Manager", parent, By.xpath(".//input[@id='manager-search-radio']//ancestor::div[@class='md-radio-inline']"));
    }

    public boolean isSelectOptionSelected() {
        return selectRadioButton.isSelected();
    }

    public boolean isNewOptionSelected() {
        return newRadioButton.isSelected();
    }

    public boolean isNoOptionSelected() {
        return noRadioButton.isSelected();
    }
}
