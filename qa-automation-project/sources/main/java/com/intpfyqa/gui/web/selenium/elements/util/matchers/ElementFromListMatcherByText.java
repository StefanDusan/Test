package com.intpfyqa.gui.web.selenium.elements.util.matchers;

import com.intpfyqa.logging.LogManager;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Searches elements by its text
 */
public class ElementFromListMatcherByText implements ElementFromListMatcher {

    private final String expectedText;

    public ElementFromListMatcherByText(String expected) {
        expectedText = expected;
    }

    public WebElement getMatched(List<WebElement> elements) {
        if (elements == null || elements.size() < 1) return null;

        LogManager.getCurrentTestLogger().trace("ElementFromListMatcherByText",
                "Got " + elements.size() + " elements to verify");

        for (WebElement element : elements) {
            String elementText = element.getText().trim();
            if ((elementText == null || elementText.equals("")) && !expectedText.equals("")) {
                elementText = element.getAttribute("innerHTML");
                if (elementText == null || elementText.equals("")) {
                    elementText = element.getAttribute("innerHtml");
                }
            }
            LogManager.getCurrentTestLogger().trace("ElementFromListMatcherByText",
                    "Checking element " + elementText);
            if (elementText != null && elementText.equalsIgnoreCase(expectedText)) {
                return element;
            }
        }

        return null;
    }

    public List<WebElement> getAllMatched(List<WebElement> elements) {
        return elements.stream().filter(e -> {
            String elementText = e.getText();
            if ((elementText == null || elementText.trim().equals("")) && !expectedText.equals("")) {
                elementText = e.getAttribute("innerHTML");
                if (elementText == null || elementText.trim().equals("")) {
                    elementText = e.getAttribute("innerHtml");
                }
            }
            return elementText != null && elementText.trim().equalsIgnoreCase(expectedText);
        }).collect(Collectors.toList());
    }

    public String describeCondition() {
        return "Element text is equal " + expectedText;
    }
}
