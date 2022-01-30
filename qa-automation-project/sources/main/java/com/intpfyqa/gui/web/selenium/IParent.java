package com.intpfyqa.gui.web.selenium;

import org.openqa.selenium.SearchContext;

/**
 * Parent of an element
 */
public interface IParent {

    /**
     * Provide this parent as Selenium search context for searching of child elements
     *
     * @return this parent as search context
     */
    SearchContext getSearchContext();

    /**
     * Page which this parent belongs to
     *
     * @return Page which this parent belongs to
     */
    BaseAutomationPage getPage();

    /**
     * How this parent was located
     * @return String description of how this parent was located
     */
    String locateString();
}
