package com.intpfy.gui.components.common;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.utils.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.time.LocalDateTime;

public class VolumeBarComponent extends BaseComponent {

    @ElementInfo(name = "Bar", findBy = @FindBy(xpath = ".//div[contains(@class, 'inner') or contains(@class, 'innner')]"))
    private Element barElement;

    public VolumeBarComponent(IParent parent) {
        this("Volume bar", parent, By.xpath(".//*[contains(@class, 'audio-meter') or contains(@class, 'volume__meter') or contains(@class, 'volume-meter')]"));
    }

    public VolumeBarComponent(String name, IParent parent, By locator) {
        super(name, parent, locator);
    }

    public boolean isChanging(Duration timeout) {
        String startValue = getStylePropertyValue();
        return barElement.waitPropertyNot("style", startValue, timeout);
    }

    public boolean isNotChanging(Duration timeout) {
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);
        String valueBeforeTimeout;
        String valueAfterTimeout;
        do {
            valueBeforeTimeout = getStylePropertyValue();
            TestUtils.sleep(750);
            valueAfterTimeout = getStylePropertyValue();
            if (valueBeforeTimeout.equals(valueAfterTimeout)) {
                return true;
            }
        }
        while (LocalDateTime.now().isBefore(endTime));

        return false;
    }

    private String getStylePropertyValue() {
        barElement.visible(Duration.ZERO);
        return barElement.getProperty("style");
    }
}
