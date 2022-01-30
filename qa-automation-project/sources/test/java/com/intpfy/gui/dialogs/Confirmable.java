package com.intpfy.gui.dialogs;

import com.intpfy.util.WebContextUtil;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.factories.WebFactoryHelper;
import com.intpfyqa.logging.LogManager;
import org.openqa.selenium.By;

public interface Confirmable {

    // If Dialog window does not contain 'Yes' or another confirmation button matching below selector,
    // 'getConfirmButton()' method should be overridden.
    default Button getConfirmButton() {
        return WebFactoryHelper.getElementFactory().createElement(
                Button.class, "Confirm", WebContextUtil.getCurrentPage(),
                By.xpath(".//button[contains(translate(text(), 'OK', 'ok'),'ok') or contains(translate(text(), 'YES', 'yes'),'yes')]"));
    }

    default void confirm() {
        LogManager.getCurrentTestLogger().info(toString(), "Confirm.");
        Button closeButton = getConfirmButton();
        closeButton.visible();
        closeButton.click();
    }
}