package com.intpfy.gui.components.interpreter;

import com.intpfy.gui.components.common.VolumeBarComponent;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.settings.WebSettings;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class MuteIndicatorComponent extends BaseComponent {

    @ElementInfo(name = "Cough", findBy = @FindBy(css = "div.audio-option"))
    private Button coughButton;

    @ElementInfo(name = "Mute", findBy = @FindBy(css = ".mute-btn"))
    private Button muteButton;

    private final VolumeBarComponent volumeBar;

    public MuteIndicatorComponent(IParent parent) {
        super("Mute indicator", parent, By.cssSelector("div.mute-indicator"));
        volumeBar = new VolumeBarComponent(this);
    }

    public void mute() {
        if (isEnabled(WebSettings.AJAX_TIMEOUT)) {
            switchMute();
        }
    }

    public void unmute() {
        if (isEnabled(WebSettings.AJAX_TIMEOUT)) {
            switchMute();
        }
    }

    public void switchMute() {
        muteButton.click();
    }

    public boolean isMuted(Duration timeout) {
        return isEnabled(timeout) && muteButton.waitCssClassNotContains("_unmuted", timeout) && volumeBar.notVisible(timeout);
    }

    public boolean isUnmuted(Duration timeout) {
        return isEnabled(timeout) && muteButton.waitCssClassContains("_unmuted", timeout) && volumeBar.visible(timeout);
    }

    public boolean isVolumeLevelChanging(Duration timeout) {
        return volumeBar.visible(timeout) && volumeBar.isChanging(timeout);
    }

    public boolean isVolumeLevelNotChanging(Duration timeout) {
        return volumeBar.visible(timeout) && volumeBar.isNotChanging(timeout);
    }

    public boolean isCoughing(Duration timeout) {
        String mutedClass = "_muted";
        return isEnabled(timeout) &&
                coughButton.waitCssClassContains(mutedClass, timeout) &&
                muteButton.waitCssClassContains(mutedClass, timeout) &&
                isVolumeLevelNotChanging(timeout);
    }

    public boolean isNotCoughing(Duration timeout) {
        String mutedClass = "_muted";
        return isEnabled(timeout) &&
                coughButton.waitCssClassNotContains(mutedClass, timeout) &&
                muteButton.waitCssClassNotContains(mutedClass, timeout);
    }

    private boolean isEnabled(Duration timeout) {
        return muteButton.waitCssClassNotContains("_disabled", timeout);
    }

    public boolean isDisabled(Duration timeout) {
        return muteButton.waitCssClassContains("_disabled", timeout);
    }
}
