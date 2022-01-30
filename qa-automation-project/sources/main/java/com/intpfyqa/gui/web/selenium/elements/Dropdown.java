package com.intpfyqa.gui.web.selenium.elements;

import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import com.intpfyqa.gui.web.selenium.exceptions.ElementException;
import com.intpfyqa.settings.WebSettings;
import com.intpfyqa.utils.TestUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;

/**
 * Drop down list
 */
public class Dropdown extends Element {

    /**
     * Constructor
     *
     * @param logicalName element logical name in application
     * @param page        page
     * @param locator     element locator
     */
    public Dropdown(String logicalName, IParent page, By locator) {
        super(logicalName, page, locator);
    }

    public Dropdown(String logicalName, IParent page, By locator, ElementFromListMatcher matcher) {
        super(logicalName, page, locator, matcher);
    }

    public void selectItemByTextStartsWith(final String startsWith) {
        trace("Select item starting from string " + startsWith);
        String[] all = getAllValues();

        Optional<String> target = Arrays.stream(all).filter(i -> i.trim().toLowerCase().startsWith(startsWith.toLowerCase()))
                .findFirst();
        if (!target.isPresent()) {
            throw new ElementException(this, "Select item with text starting from string",
                    new Exception("Item starting from required string is not found"), startsWith);
        }
        selectItem(target.get().trim());
    }

    /**
     * Get default selenium select
     *
     * @return Standard selenium select
     */
    private Select createSelect(Duration timeout) {
        return new Select(getNativeElement(timeout));
    }

    private Select createSelect() {
        return new Select(getNativeElement());
    }

    /**
     * Select item in list by its text
     *
     * @param item Item text
     */
    public void selectItem(String item) {
        requireVisible("Select item from list", item);
        try {
            trace("Selecting item %s", item);
            createSelect().selectByVisibleText(item);
            debug("Selected item %s", item);
        } catch (WebDriverException e) {
            throw new ElementException(this, "Select item", e, item);
        }
    }

    public boolean isItemDisabled(String item) {
        requireVisible("Check that list item is not enabled", item);
        try {
            trace("Is item %s disabled", item);
            java.util.List<WebElement> options = createSelect().getOptions();
            Optional<WebElement> targetOption = options.stream().filter(webElement ->
                    webElement.getText().equals(item)).findFirst();
            if (targetOption.isPresent()) {
                boolean isEnabled = targetOption.get().isEnabled();
                debug("Option " + item + " enabled state is: " + isEnabled);
                return isEnabled;
            } else
                throw new NoSuchElementException("Option item '" + item + "' doesn't exist");
        } catch (WebDriverException e) {
            throw new ElementException(this, "Check if element present'" + item + "'", e, item);
        }
    }

    public String getValue() {
        trace("Getting selected item");
        requireVisible("Get value");

        try {
            String val = getNativeElement().getAttribute("value").trim();
            if (val.equals("")) {
                try {
                    val = getNativeElement().findElement(By.xpath(".//option[@value='']")).getText();
                } catch (WebDriverException ignore) {

                }
                return val;
            }
            try {
                val = getNativeElement().findElement(By.xpath(".//option[@value='" + val + "']")).getText();
            } catch (WebDriverException ignore) {
                for (WebElement option : getNativeElement().findElements(By.tagName("option"))) {
                    if (option.getAttribute("value").equals(val)) {
                        val = option.getText();
                        break;
                    }
                }
            }

            debug("Selected item: %s", val);

            return val;
        } catch (TimeoutException e) {
            throw new ElementException(this, "Get selected item", e);
        }
    }

    public String getText() {
        return getValue();
    }

    public boolean hasItem(String value) {
        String[] values = getAllValues();
        if (values != null) {
            for (String val : values) {
                if (val.trim().equalsIgnoreCase(value.trim())) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean waitOptionItemToBeSelected(String item, Duration timeout) {
        trace("Wait for item " + item + " is selected within timeout of " + timeout + " seconds");
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);
        try {

            String val;
            do {
                val = getValue();
                if (val.equals("") && val.equals(item)) return true;
                if (val.equalsIgnoreCase(item)) {
                    debug("Item " + item + " selected: " + val);
                    return true;
                }
                TestUtils.sleep(100);

            } while (LocalDateTime.now().isBefore(endTime));

            debug("Item " + item + " not selected. Actual: " + val);
            return false;
        } catch (TimeoutException e) {
            throw new ElementException(this, "Waiting for item to be selected", e, item, timeout);
        }
    }

    public String[] getAllValues() {
        return getAllValues(true);
    }

    /**
     * Get all available values
     *
     * @return All available values
     */
    private String[] getAllValues(boolean retryOnStaleReference) {
        trace("Getting all available items");


        try {
            waitOptionsCountNotZero(WebSettings.AJAX_TIMEOUT);
            requireVisible("Get all values");
            java.util.List<WebElement> val = getNativeElement().findElements(By.tagName("option"));
            String[] values = new String[val.size()];
            for (int i = 0; i < val.size(); i++) {
                values[i] = val.get(i).getText();
            }

            debug("All values: " + StringUtils.join(values, ";"));
            return values;
        } catch (WebDriverException e) {
            if (e instanceof StaleElementReferenceException && retryOnStaleReference) {
                TestUtils.sleep(1000);
                return getAllValues(false);
            }
            throw new ElementException(this, "Getting all items", e);
        }
    }

    public boolean waitOptionsCountNotZero(Duration timeout) {
        return waitOptionsCountNotZero(timeout, "");
    }

    public boolean waitOptionsCountNotZero(Duration timeout, final String ignoreOption) {
        trace("Waiting for options count not zero");
        try {
            LocalDateTime endTime = LocalDateTime.now().plus(timeout);
            int size = -1;
            do {
                try {
                    java.util.List<WebElement> list =
                            getNativeElement().findElements(By.tagName("option"));
                    size = list.size();
                    if (size > 0 && (size > 1 || !list.get(0).getText().equals(ignoreOption))) {
                        debug("Options count is: " + size);
                        return true;
                    }
                } catch (StaleElementReferenceException ignore) {
                }
            } while (LocalDateTime.now().isBefore(endTime));
            debug("Options count is: " + size);
            return false;
        } catch (Throwable t) {
            throw new ElementException(this, "Waiting for items count be greater than 0", t, ignoreOption);
        }
    }


    /**
     * Get all available values
     *
     * @return All available values
     */
    public String[] getAllValuesExceptEmpty() {
        return getAllValuesExceptEmpty("");
    }

    /**
     * Get all values except ignored one
     *
     * @param except Value to ignore
     * @return All values except specified
     */
    public String[] getAllValuesExceptEmpty(String except) {
        trace("Getting all available items");

        String[] all = getAllValues();
        java.util.List<String> val = new LinkedList<>();
        for (int i = 0; i < all.length; i++) {
            if (!all[i].equals(except)) val.add(all[i]);
        }

        debug("Got values %s", StringUtils.join(val.toArray(), ";"));
        return val.toArray(new String[val.size()]);
    }

    /**
     * Wait for option appear in list
     *
     * @param timeout Timeout for waiting in seconds
     * @param text    Option text to wait
     * @return <code>true</code> if such option appeared in list
     */
    public boolean waitForValueAvailable(Duration timeout, String text) {
        trace("Wait option " + text + " appearance");
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);

        try {
            do {
                try {
                    boolean exists = Arrays.stream(getAllValues()).anyMatch(v -> v.equalsIgnoreCase(text));
                    if (exists) {
                        debug("Option " + text + " found in dropdown");
                        return true;
                    }
                } catch (StaleElementReferenceException | ElementException e) {
                    if (e instanceof ElementException && ((ElementException) e).isScriptError())
                        throw e;
                }
            } while (LocalDateTime.now().isBefore(endTime));
            debug("Option " + text + " not found in dropdown");
            return false;
        } catch (Throwable t) {
            throw new ElementException(this, "Wait for option", t, text, timeout);
        }
    }
}