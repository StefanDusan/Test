package com.intpfy.gui.dialogs;

import com.intpfy.util.WebContextUtil;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.factories.WebFactoryHelper;
import com.intpfyqa.logging.LogManager;
import org.openqa.selenium.By;

public interface Closeable {

    // If Dialog window does not contain 'Close' button matching below selector,
    // 'getCloseButton()' method should be overridden.
    default Button getCloseButton() {
        return WebFactoryHelper.getElementFactory().createElement(
                Button.class, "Close", WebContextUtil.getCurrentPage(),
                By.xpath(".//button[contains(@class, 'close') or contains(translate(text(), 'CLOSE', 'close'),'close')]"));
    }

    default void close() {
        LogManager.getCurrentTestLogger().info(toString(), "Close.");
        Button closeButton = getCloseButton();
        closeButton.visible();
        closeButton.click();
    }
}