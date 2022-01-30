package com.intpfy.gui.components.emi.event_creation;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfy.gui.complex_elements.selection.ComplexRadioButton;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class SelectModeratorComponent extends BaseComponent {

    @ElementInfo(name = "Select Moderator radio button", findBy = @FindBy(xpath = ".//div[./input[@id='search-moderator-radio']]"))
    private ComplexRadioButton selectRadioButton;

    @ElementInfo(name = "New Moderator radio button", findBy = @FindBy(xpath = ".//div[./input[@id='new-moderator-radio']]"))
    private ComplexRadioButton newRadioButton;

    @ElementInfo(name = "No Moderator radio button", findBy = @FindBy(xpath = ".//div[./input[@id='no-moderator-radio']]"))
    private ComplexRadioButton noRadioButton;

    public SelectModeratorComponent(IParent parent) {
        super("Select Moderator", parent, By.xpath(".//input[@id='search-moderator-radio']//ancestor::div[@class='md-radio-inline']"));
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
