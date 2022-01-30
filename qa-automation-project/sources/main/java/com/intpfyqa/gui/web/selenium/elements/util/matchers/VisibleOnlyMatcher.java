package com.intpfyqa.gui.web.selenium.elements.util.matchers;

import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Only visible elements
 */
public class VisibleOnlyMatcher implements ElementFromListMatcher {


    /**
     * Matching only visible
     */
    public VisibleOnlyMatcher() {
    }

    public WebElement getMatched(List<WebElement> elements) {
        if (elements == null || elements.size() < 1) return null;

        WebElement result = null;

        for (WebElement element : elements) {
            if (element.isDisplayed()) {
                result = element;
                break;
            }
        }

        return result;
    }

    public List<WebElement> getAllMatched(List<WebElement> elements) {
        return elements.stream().filter(WebElement::isDisplayed).collect(Collectors.toList());
    }

    public String describeCondition() {
        return "Item is visible";
    }
}
