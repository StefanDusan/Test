package com.intpfyqa.gui.web.selenium.elements.wait;


import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.elements.ParentNotFoundException;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.logging.Logger;

/**
 * Element wait conditions
 */
public class ExpectedElementConditions {

    private final static Logger log = Logger.getLogger(ExpectedElementConditions.class.getName());

    private ExpectedElementConditions() {
        // Utility class
    }

    /**
     * An expectation for checking that an element is present on the DOM of a
     * page. This does not necessarily mean that the element is visible.
     *
     * @param locator used to find the element
     * @return the WebElement once it is located
     */
    public static ElementExpectedCondition<WebElement> presenceOfElementLocated(
            final By locator, final ElementFromListMatcher matcher) {
        return new ElementExpectedCondition<WebElement>() {
            public WebElement apply(IParent parentElement) {
                return findElement(parentElement, locator, matcher);
            }

            @Override
            public String toString() {
                return "presence of element located by: " + locator + (null != matcher ? (", " +
                        "and matcher: " + matcher.describeCondition()) : "");
            }
        };
    }

    /**
     * An expectation for checking that an element is present on the DOM of a page
     * and visible. Visibility means that the element is not only displayed but
     * also has a height and width that is greater than 0.
     *
     * @param locator used to find the element
     * @return the WebElement once it is located and visible
     */
    public static ElementExpectedCondition<WebElement> visibilityOfElementLocated(
            final By locator, final ElementFromListMatcher matcher) {
        return new ElementExpectedCondition<WebElement>() {

            public WebElement apply(IParent parentElement) {
                try {
                    return elementIfVisible(findElement(parentElement, locator, matcher));
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return "visibility of element located by " + locator + (null != matcher ? (", " +
                        "and matcher: " + matcher.describeCondition()) : "");
            }
        };
    }


    /**
     * Chekcs if element is visible
     *
     * @param element element to check
     * @return the given element if it is visible and has non-zero size, otherwise
     * null.
     */
    private static WebElement elementIfVisible(WebElement element) {
        return element != null
                ? element.isDisplayed() ? element : null
                : null;
    }


    /**
     * An expectation for checking that an element is either invisible or not
     * present on the DOM.
     *
     * @param locator used to find the element
     * @return element by locator if it is invisible or not exists at all
     */
    public static ElementExpectedCondition<Boolean> invisibilityOfElementLocated(
            final By locator, final ElementFromListMatcher matcher) {
        return new ElementExpectedCondition<Boolean>() {

            public Boolean apply(IParent parentElement) {
                try {
                    return !(findElement(parentElement, locator, matcher).isDisplayed());
                } catch (NoSuchElementException | StaleElementReferenceException | ParentNotFoundException ignore) {
                    return true;
                }
            }

            @Override
            public String toString() {
                return "element to no longer be visible: " + locator + (null != matcher ? (", " +
                        "and matcher: " + matcher.describeCondition()) : "");
            }
        };
    }

    /**
     * An expectation for checking an element is visible and enabled such that you
     * can click it.
     *
     * @param locator element locator
     * @return element by locator if it is clickable (visible and enabled)
     */
    public static ElementExpectedCondition<WebElement> elementToBeClickable(
            final By locator, final ElementFromListMatcher matcher) {
        return new ElementExpectedCondition<WebElement>() {

            public ElementExpectedCondition<WebElement> visibilityOfElementLocated =
                    ExpectedElementConditions.visibilityOfElementLocated(locator, matcher);


            public WebElement apply(IParent parentElement) {
                WebElement element = visibilityOfElementLocated.apply(parentElement);

                try {
                    if (element != null && element.isEnabled()) {
                        return element;
                    } else {
                        return null;
                    }
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return "element to be clickable: " + locator + (null != matcher ? (", " +
                        "and matcher: " + matcher.describeCondition()) : "");
            }
        };
    }


    /**
     * An expectation with the logical opposite condition of the given condition.
     * In case of null, it will return false.
     *
     * @param condition condition to apply
     * @return true if condition is false or not null
     */
    public static ElementExpectedCondition<Boolean> not(final ElementExpectedCondition<?> condition) {
        return new ElementExpectedCondition<Boolean>() {
            public Boolean apply(IParent parentElement) {
                Object result = condition.apply(parentElement);
                return !(result == null || result == Boolean.TRUE);
            }

            @Override
            public String toString() {
                return "condition to not be valid: " + condition;
            }
        };
    }

    /**
     * An expectation with the logical opposite condition of the given condition.
     * In case of null, it will return false.
     *
     * @param condition condition to apply
     * @return true if condition is false or not null
     */
    public static ExpectedCondition<Boolean> not(final ExpectedCondition<?> condition) {
        return ExpectedConditions.not(condition);
    }

    /**
     * An expectation that currentUrl contains given part.
     *
     * @param part to compare
     * @return true if currentUrl contains part, false if currentUrl is null or currentUrl doesn't contain part
     */
    public static ElementExpectedCondition<Boolean> urlContains(String part) {
        return new ElementExpectedCondition<Boolean>() {

            private String currentUrl = "";

            public Boolean apply(IParent parent) {
                currentUrl = parent.getPage().getPageContext().getBrowserWindow().getCurrentUrl();
                return currentUrl != null && currentUrl.toLowerCase().contains(part.toLowerCase());
            }

            public String toString() {
                return "part to contain " + part + " Current url: " + currentUrl;
            }
        };
    }

    /**
     * Looks up an element. Logs and re-throws WebDriverException if thrown. <p/>
     * Method exists to gather data for http://code.google.com/p/selenium/issues/detail?id=1800
     *
     * @param by            locator
     * @param parentElement parentElement where required should be searched
     * @return element if found or throws Exception
     */
    private static WebElement findElement(IParent parentElement, By by, ElementFromListMatcher matcher) {
        if (null != matcher) {
            WebElement element = matcher.getMatched(findElements(parentElement, by));
            if (null == element) throw new NoSuchElementException(by.toString());
            return element;
        } else {
            SearchContext searchContext;
            try {
                searchContext = parentElement.getSearchContext();
            } catch (WebDriverException e) {
                throw new ParentNotFoundException(parentElement, e);
            }
            return searchContext.findElement(by);
        }
    }

    /**
     * Looks up elements. Logs and re-throws WebDriverException if thrown.
     *
     * @param by            locator
     * @param parentElement parentElement where required should be searched
     * @return elements if found or throws Exception
     */
    private static List<WebElement> findElements(IParent parentElement, By by) {
        SearchContext searchContext;
        try {
            searchContext = parentElement.getSearchContext();
        } catch (WebDriverException e) {
            throw new ParentNotFoundException(parentElement, e);
        }
        return searchContext.findElements(by);
    }

}
