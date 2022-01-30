package com.intpfy.gui.dialogs;

import com.intpfy.util.WebContextUtil;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.factories.WebFactoryHelper;
import com.intpfyqa.logging.LogManager;
import org.openqa.selenium.By;

public interface Savable {

    // If Dialog window does not contain 'Save' button matching below selector,
    // 'getSaveButton()' method should be overridden.
    default Button getSaveButton() {
        return WebFactoryHelper.getElementFactory().createElement(
                Button.class, "Save", WebContextUtil.getCurrentPage(),
                By.xpath(".//button[contains(translate(text(), 'SAVE', 'save'),'save')]"));
    }

    default void save() {
        LogManager.getCurrentTestLogger().info(toString(), "Save.");
        Button closeButton = getSaveButton();
        closeButton.visible();
        closeButton.click();
    }
}