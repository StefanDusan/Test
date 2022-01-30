package com.intpfy.gui.components.common;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class VolumeControlComponent extends BaseComponent {

    @ElementInfo(name = "Mute", findBy = @FindBy(css = "div.volume-switcher__tooltip"))
    private Button muteButton;

    private final VolumeLevelsComponent volumeLevelsComponent;

    public VolumeControlComponent(IParent parent) {
        super("Volume control", parent, By.cssSelector("div.volume-control"));
        volumeLevelsComponent = new VolumeLevelsComponent(this);
    }

    public void mute() {
        switchMuteUnmute();
    }

    public void unmute() {
        switchMuteUnmute();
    }

    public boolean isMuted(Duration timeout) {
        return getComponentElement().waitCssClassContains("_off", timeout) && volumeLevelsComponent.isDisabled(timeout);
    }

    public boolean isUnmuted(Duration timeout) {
        return getComponentElement().waitCssClassContains("_on", timeout) && volumeLevelsComponent.isEnabled(timeout);
    }

    public void setMaxVolumeLevel() {
        volumeLevelsComponent.setMax();
    }

    public void setMinVolumeLevel() {
        volumeLevelsComponent.setMin();
    }

    public boolean isVolumeLevelMax(Duration timeout) {
        return volumeLevelsComponent.isMax(timeout);
    }

    public boolean isVolumeLevelMin(Duration timeout) {
        return volumeLevelsComponent.isMin(timeout);
    }

    private void switchMuteUnmute() {
        muteButton.click();
    }
}
