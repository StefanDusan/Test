package com.intpfy.gui.components.common;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.settings.WebSettings;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class VolumeLevelsComponent extends BaseComponent {

    @ElementInfo(name = "First volume level item", findBy = @FindBy(xpath = ".//div[contains(@class, 'levels__item')][1]"))
    private Element firstVolumeLevelItem;

    @ElementInfo(name = "Second volume level item", findBy = @FindBy(xpath = ".//div[contains(@class, 'levels__item')][2]"))
    private Element secondVolumeLevelItem;

    @ElementInfo(name = "Last volume level item", findBy = @FindBy(xpath = ".//div[contains(@class, 'levels__item')][last()]"))
    private Element lastVolumeLevelItem;

    public VolumeLevelsComponent(IParent parent) {
        super("Volume levels", parent, By.xpath(".//div[contains(@id, 'volume-levels') or contains(@id, 'volume-bar')]"));
    }

    public void setMax() {
        if (!isMax(Duration.ZERO)) {
            lastVolumeLevelItem.click();
        }
    }

    public void setMin() {
        if (!isMin(Duration.ZERO)) {
            firstVolumeLevelItem.click();
        }
    }

    public boolean isMax(Duration timeout) {
        return isActive(lastVolumeLevelItem, timeout);
    }

    public boolean isMin(Duration timeout) {
        return isActive(firstVolumeLevelItem, timeout) && isInactive(secondVolumeLevelItem, timeout);
    }

    public boolean isEnabled(Duration timeout) {
        return isActive(firstVolumeLevelItem, timeout);
    }

    public boolean isDisabled(Duration timeout) {
        return isInactive(firstVolumeLevelItem, timeout);
    }

    private boolean isActive(Element volumeLevelItem, Duration timeout) {
        return volumeLevelItem.waitCssClassContains("active", timeout);
    }

    private boolean isInactive(Element volumeLevelItem, Duration timeout) {
        return volumeLevelItem.waitCssClassContains("_disabled", timeout) || volumeLevelItem.waitCssClassNotContains("active", timeout);
    }
}
