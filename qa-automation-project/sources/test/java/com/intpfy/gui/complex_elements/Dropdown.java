package com.intpfy.gui.complex_elements;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import com.intpfyqa.gui.web.selenium.exceptions.ElementException;
import com.intpfyqa.gui.web.selenium.factories.WebFactoryHelper;
import com.intpfyqa.test.Verify;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.intpfy.util.XpathUtil.createContainsTextIgnoreCaseLocator;
import static com.intpfy.util.XpathUtil.createEqualsTextIgnoreCaseLocator;
import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

public class Dropdown extends BaseComponent {

    @ElementInfo(name = "Result of adding", findBy = @FindBy(css = ".ui-select-match, .ng-value, .ng-value-label"))
    protected Element resultElement;

    public Dropdown(String name, IParent parent, By componentLocator) {
        super(name, parent, componentLocator);
    }

    public Dropdown(String name, IParent parent, By componentLocator, ElementFromListMatcher matcher) {
        super(name, parent, componentLocator, matcher);
    }

    public String getSelected() {
        return resultElement.getText();
    }

    public int getOptionsCount() {
        openOptionsList();
        By genericOptionLocator = By.cssSelector("div.ui-select-choices-row");
        int count = getComponentElement().children(genericOptionLocator).size();
        closeOptionsList();
        return count;
    }

    public void select(String option) {
        openOptionsList();
        selectOption(option);
    }

    public void selectWithAssertion(String option) {
        select(option);
        Verify.assertTrue(isSelected(option, AJAX_TIMEOUT), String.format("Option '%s' selected.", option));
    }

    public void selectContainsText(String option) {
        openOptionsList();
        selectOptionContainsText(option);
    }

    public void selectContainsTextWithAssertion(String option) {
        selectContainsText(option);
        Verify.assertTrue(isSelectedContainsText(option, AJAX_TIMEOUT), String.format("Option containing text '%s' selected.", option));
    }

    public boolean isSelected(Duration timeout) {
        return resultElement.visible(timeout);
    }

    public boolean isSelected(String option, Duration timeout) {
        return resultElement.waitForTextEquals(option, timeout);
    }

    public boolean isSelectedContainsText(String option, Duration timeout) {
        return resultElement.waitForTextContains(option, timeout);
    }

    protected void selectOption(String option) {
        Element optionElement = createOptionElement(option);
        clickOptionElement(optionElement);
    }

    protected void selectOptionContainsText(String option) {
        Element optionElement = createOptionElementContainsText(option);
        clickOptionElement(optionElement);
    }

    protected Element createOptionElement(String option) {
        String name = option + " option";
        By locator = By.xpath(".//div[contains(@class, 'select-choices-row') and .//span[" + createEqualsTextIgnoreCaseLocator(option) + "]]");
        return WebFactoryHelper.getElementFactory().createElement(Element.class, name, getParent(), locator);
    }

    protected Element createOptionElementContainsText(String option) {
        String name = option + " option";
        By locator = By.xpath(".//div[contains(@class, 'select-choices-row') and .//span[" + createContainsTextIgnoreCaseLocator(option) + "]]");
        return WebFactoryHelper.getElementFactory().createElement(Element.class, name, getParent(), locator);
    }

    void openOptionsList() {
        Element componentElement = getComponentElement();
        componentElement.visible();
        componentElement.clickable(AJAX_TIMEOUT);
        clickWithRetry(componentElement); // First click attempt fails sometimes on remote test execution.
    }

    void closeOptionsList() {
        Element componentElement = getComponentElement();
        componentElement.visible();
        componentElement.clickable(AJAX_TIMEOUT);
        clickWithRetry(componentElement); // First click attempt fails sometimes on remote test execution.
    }

    private void clickOptionElement(Element element) {
        element.visible();
        element.clickable(AJAX_TIMEOUT);
        element.click();
    }

    private void clickWithRetry(Element element) {

        LocalDateTime endDateTime = LocalDateTime.now().plus(AJAX_TIMEOUT);

        while (LocalDateTime.now().isBefore(endDateTime)) {
            try {
                element.click();
                break;
            } catch (ElementException ignored) {
            }
        }
    }
}
