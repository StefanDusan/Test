package com.intpfy.gui.complex_elements;

import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.Input;
import com.intpfyqa.test.Verify;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

public class SearchDropdown extends Dropdown {

    @ElementInfo(name = "Search", findBy = @FindBy(css = "input[type='search']"))
    private Input searchInput;

    public SearchDropdown(String name, IParent parent, By componentLocator) {
        super(name, parent, componentLocator);
    }

    @Override
    public void selectWithAssertion(String option) {
        openOptionsList(option);
        selectOption(option);
        Verify.assertTrue(isSelected(option, AJAX_TIMEOUT), String.format("Option '%s' is selected.", option));
    }

    @Override
    public void selectContainsTextWithAssertion(String option) {
        openOptionsList(option);
        selectOptionContainsText(option);
        Verify.assertTrue(isSelectedContainsText(option, AJAX_TIMEOUT), String.format("Option containing text '%s' is selected.", option));
    }

    @Override
    public void closeOptionsList() {
        searchInput.click();
        searchInput.sendKeys(Keys.ESCAPE);
    }

    public boolean isOptionsListClosed(Duration timeout) {
        return searchInput.waitProperty("aria-expanded", "false", timeout);
    }

    private void openOptionsList(String option) {
        Element componentElement = getComponentElement();
        componentElement.clickable(AJAX_TIMEOUT);
        componentElement.click();
        searchInput.setText(option);
    }
}
