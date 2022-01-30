package com.intpfy.gui.complex_elements;

import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import com.intpfyqa.gui.web.selenium.factories.WebFactoryHelper;
import org.openqa.selenium.By;

import static com.intpfy.util.XpathUtil.createContainsTextIgnoreCaseLocator;
import static com.intpfy.util.XpathUtil.createEqualsTextIgnoreCaseLocator;

/**
 * Dropdown component with option element(s) situated outside of component.
 */
public class PageDropdown extends Dropdown {

    private static final By TOOLTIP_LOCATOR = By.cssSelector("div.tooltip-inner");

    public PageDropdown(String name, IParent parent, By componentLocator) {
        super(name, parent, componentLocator);
    }

    // Used for reflective instantiation.
    public PageDropdown(String name, IParent parent, By componentLocator, ElementFromListMatcher matcher) {
        super(name, parent, componentLocator, matcher);
    }

    public String getTooltip(String option) {

        openOptionsList();

        Element optionElement = createOptionElementContainsText(option);
        optionElement.visible();

        optionElement.moveMouseIn();

        String name = option + " tooltip";
        Element tooltip = WebFactoryHelper.getElementFactory().createElement(Element.class, name, getPage(), TOOLTIP_LOCATOR);
        tooltip.visible();

        String tooltipText = tooltip.getText().trim();

        closeOptionsList();

        return tooltipText;
    }

    @Override
    protected Element createOptionElement(String option) {
        String name = option + " option";
        By selector = By.xpath(".//div[contains(@class, 'ng-option') and " + createEqualsTextIgnoreCaseLocator(option) + "]");
        return WebFactoryHelper.getElementFactory().createElement(Element.class, name, getPage(), selector);
    }

    @Override
    protected Element createOptionElementContainsText(String option) {
        String name = option + " option";
        By selector = By.xpath(".//div[contains(@class, 'ng-option') and " + createContainsTextIgnoreCaseLocator(option) + "]");
        return WebFactoryHelper.getElementFactory().createElement(Element.class, name, getPage(), selector);
    }
}
