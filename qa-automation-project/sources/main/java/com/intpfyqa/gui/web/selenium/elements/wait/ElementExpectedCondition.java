package com.intpfyqa.gui.web.selenium.elements.wait;

import com.google.common.base.Function;
import com.intpfyqa.gui.web.selenium.IParent;

/**
 * Models a condition that might reasonably be expected to eventually evaluate to something that is
 * neither null nor false. Examples would include determining if a web page has loaded or that an
 * element is visible.
 * <p>
 * Object should be instance of WebDriver or WebElement and will be treated as parent
 *
 * @param <T> The return type
 * @see org.openqa.selenium.support.ui.WebDriverWait
 * @see ExpectedElementConditions
 */
public interface ElementExpectedCondition<T> extends Function<IParent, T> {
}

