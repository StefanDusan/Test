package com.intpfy.gui.components.audience;

import com.intpfy.gui.complex_elements.PageDropdown;
import com.intpfy.gui.components.common.AutoVolumeComponent;
import com.intpfy.model.Language;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.settings.WebSettings;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class ConnectionComponent extends BaseComponent {

    @ElementInfo(name = "Headset icon", findBy = @FindBy(css = "div.indicator svg"))
    private Element headsetIcon;

    @ElementInfo(name = "Language channel dropdown", findBy = @FindBy(css = "div._language-container div.ng-select-container"))
    private PageDropdown languageChannelDropdown;

    private final AutoVolumeComponent autoVolume;

    @ElementInfo(name = "Connect / disconnect", findBy = @FindBy(css = "button.primary"))
    private Button connectButton;

    public ConnectionComponent(IParent parent) {
        super("Connection", parent, By.id("audience"));
        autoVolume = new AutoVolumeComponent(this);
    }

    public void selectLanguageChannel(Language language) {
        languageChannelDropdown.selectContainsTextWithAssertion(language.name());
    }

    public boolean isLanguageChannelSelected(Language language, Duration timeout) {
        return languageChannelDropdown.isSelectedContainsText(language.name(), timeout);
    }

    public boolean isNoLanguageChannelSelected(Duration timeout) {
        return languageChannelDropdown.isSelected("none", timeout) || languageChannelDropdown.isSelected("select", timeout);
    }

    public void disconnect() {
        if (isConnected(Duration.ZERO)) {
            connectButton.click();
        }
    }

    public boolean isConnected() {
        return isConnected(WebSettings.AJAX_TIMEOUT);
    }

    private boolean isConnected(Duration timeout) {
        return connectButton.waitForTextEquals("disconnect", timeout) &&
                headsetIcon.waitCssClassNotContains("disabled", timeout);
    }

    public boolean isDisconnected() {
        return connectButton.waitForTextEquals("connect", WebSettings.AJAX_TIMEOUT) &&
                headsetIcon.waitCssClassContains("disabled", WebSettings.AJAX_TIMEOUT);
    }

    public boolean isAutoVolumeAvailable(Duration timeout) {
        return autoVolume.isAvailable(timeout);
    }

    public boolean isAutoVolumeUnavailable(Duration timeout) {
        return autoVolume.isUnavailable(timeout);
    }

    public void enableAutoVolume() {
        autoVolume.enable();
    }

    public void disableAutoVolume() {
        autoVolume.disable();
    }

    public boolean isAutoVolumeEnabled(Duration timeout) {
        return autoVolume.isEnabled(timeout);
    }

    public boolean isAutoVolumeDisabled(Duration timeout) {
        return autoVolume.isDisabled(timeout);
    }
}
