package com.intpfy.gui.components.emi.common;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Input;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;

public class SearchInputComponent extends BaseComponent {

    @ElementInfo(name = "Input", findBy = @FindBy(css = "input[type='search']"))
    private Input input;

    public SearchInputComponent(IParent parent, By componentLocator) {
        super("Search input", parent, componentLocator);
    }

    public void search(String criteria) {
        input.setText(criteria);
        input.sendKeys(Keys.ENTER);
    }
}
