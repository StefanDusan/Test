package com.intpfyqa.gui.web.selenium.elements.util.matchers;


import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by RakitskyAK on 06.11.2018.
 */
public class SequentialMatcher implements ElementFromListMatcher {

    private LinkedList<ElementFromListMatcher> matchers = new LinkedList<>();

    public SequentialMatcher(ElementFromListMatcher firstMatcher) {
        matchers.add(firstMatcher);
    }

    public SequentialMatcher then(ElementFromListMatcher matcher) {
        matchers.add(matcher);
        return this;
    }

    public WebElement getMatched(List<WebElement> elements) {
        List<WebElement> newCollection = new LinkedList<>(elements);
        for (int i = 0; i < matchers.size() - 1; i++) {
            newCollection = matchers.get(i).getAllMatched(newCollection);
        }
        return matchers.get(matchers.size() - 1).getMatched(newCollection);
    }

    public List<WebElement> getAllMatched(List<WebElement> elements) {
        List<WebElement> newCollection = new LinkedList<>(elements);
        for (ElementFromListMatcher matcher : matchers) {
            newCollection = matcher.getAllMatched(newCollection);
        }
        return newCollection;
    }

    /**
     * Provides with description of condition for this matcher
     *
     * @return String description
     */
    public String describeCondition() {
        return "meets all conditions\n" + StringUtils.join(matchers.stream().map(ElementFromListMatcher::describeCondition)
                .collect(Collectors.toList()), "\n");
    }
}
