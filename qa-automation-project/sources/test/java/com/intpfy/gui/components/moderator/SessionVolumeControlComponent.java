package com.intpfy.gui.components.moderator;

import com.intpfy.gui.components.common.VolumeBarComponent;
import com.intpfy.gui.components.common.VolumeLevelsComponent;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.settings.WebSettings;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class SessionVolumeControlComponent extends BaseComponent {

    @ElementInfo(name = "Volume on / off button", findBy = @FindBy(css = "button.vol-control__button"))
    private Button volumeButton;

    private final VolumeLevelsComponent volumeLevels;
    private final VolumeBarComponent volumeBar;

    public SessionVolumeControlComponent(IParent parent) {
        super("Mic control", parent, By.cssSelector("div.floor-control__vol, div.session-control__vol"));
        volumeLevels = new VolumeLevelsComponent(this);
        volumeBar = new VolumeBarComponent(this);
    }

    public void turnOn() {
        if (isOff(Duration.ZERO)) {
            volumeButton.click();
        }
    }

    public void turnOff() {
        if (isOn(Duration.ZERO)) {
            volumeButton.click();
        }
    }

    public boolean isOn() {
        return isOn(WebSettings.AJAX_TIMEOUT);
    }

    private boolean isOn(Duration timeout) {
        return volumeButton.waitCssClassContains("_on", timeout) && volumeLevels.isEnabled(timeout);
    }

    public boolean isOff() {
        return isOff(WebSettings.AJAX_TIMEOUT);
    }

    private boolean isOff(Duration timeout) {
        return volumeButton.waitCssClassNotContains("_on", timeout) && volumeLevels.isDisabled(timeout);
    }

    public boolean isLevelChanging(Duration timeout) {
        return volumeBar.isChanging(timeout);
    }

    public boolean isLevelNotChanging(Duration timeout) {
        return volumeBar.isNotChanging(timeout);
    }
}
