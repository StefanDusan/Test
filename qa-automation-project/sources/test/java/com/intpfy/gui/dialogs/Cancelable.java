package com.intpfy.gui.dialogs;

import com.intpfy.util.WebContextUtil;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.factories.WebFactoryHelper;
import com.intpfyqa.logging.LogManager;
import org.openqa.selenium.By;

public interface Cancelable {

    // If Dialog window does not contain 'Cancel' button matching below selector,
    // 'getCancelButton()' method should be overridden.
    default Button getCancelButton() {
        return WebFactoryHelper.getElementFactory().createElement(
                Button.class, "Cancel", WebContextUtil.getCurrentPage(),
                By.xpath(".//button[contains(translate(text(), 'CANCEL', 'cancel'),'cancel') " +
                        "or contains(translate(text(), 'NO', 'no'),'no')]"));
    }

    default void cancel() {
        LogManager.getCurrentTestLogger().info(toString(), "Cancel.");
        Button closeButton = getCancelButton();
        closeButton.visible();
        closeButton.click();
    }
}