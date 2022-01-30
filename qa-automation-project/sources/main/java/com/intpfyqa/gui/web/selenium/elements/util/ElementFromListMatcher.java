package com.intpfyqa.gui.web.selenium.elements.util;

import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Searches for matched elements from collection
 */
public interface ElementFromListMatcher {

    /**
     * Finds matched element in collection
     *
     * @param elements Collection of elements to verify
     * @return Matched element or null if element not found
     */
    WebElement getMatched(List<WebElement> elements);

    List<WebElement> getAllMatched(List<WebElement> elements);

    /**
     * Provides with description of condition for this matcher
     *
     * @return String description
     */
    String describeCondition();
}
