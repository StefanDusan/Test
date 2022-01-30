package com.intpfyqa.gui.web.selenium.elements.util.matchers;


import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Match by index
 */
public class ElementFromListMatcherByIndex implements ElementFromListMatcher {

    private final int index;

    /**
     * Matching by index
     *
     * @param index Index (starts from 1)
     */
    public ElementFromListMatcherByIndex(String index) {
        this.index = Integer.parseInt(index);
    }

    public ElementFromListMatcherByIndex(int index) {
        this.index = index;
    }

    public WebElement getMatched(List<WebElement> elements) {
        if (elements == null || elements.isEmpty() || elements.size() < index) return null;
        if (index < 0) {
            return elements.get(elements.size() - 1);
        }

        return elements.get(index - 1);
    }

    public List<WebElement> getAllMatched(List<WebElement> elements) {
        WebElement target = getMatched(elements);
        List<WebElement> res = new ArrayList<>();
        if (null != target) res.add(target);
        return res;
    }

    public String describeCondition() {
        return "Element number # " + index;
    }
}
