package com.intpfy.gui.components.moderator;

import com.intpfy.gui.components.common.VolumeBarComponent;
import com.intpfy.gui.dialogs.moderator.StartVoicePublishingDW;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfy.gui.complex_elements.selection.ComplexCheckbox;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class SessionMicControlComponent extends BaseComponent {

    @ElementInfo(name = "Turn mic on complex checkbox", findBy = @FindBy(css = "div.toggle-switch"))
    private ComplexCheckbox turnOnCheckbox;

    @ElementInfo(name = "Mic status", findBy = @FindBy(css = "div.mic-control-volume__info"))
    private Element statusElement;

    private final VolumeBarComponent volumeBar;

    public SessionMicControlComponent(IParent parent) {
        super("Mic control", parent, By.cssSelector("div.mic-control"));
        volumeBar = new VolumeBarComponent(this);
    }

    public StartVoicePublishingDW turnOn() {
        turnOnCheckbox.select();
        return new StartVoicePublishingDW(getPage());
    }

    public void turnOff() {
        turnOnCheckbox.unselect();
    }

    public boolean isOn(Duration timeout) {
        return turnOnCheckbox.waitIsSelected(timeout) && statusElement.waitForTextContains("mic on", timeout);
    }

    public boolean isOff(Duration timeout) {
        return turnOnCheckbox.waitIsNotSelected(timeout) && statusElement.waitForTextContains("mic off", timeout);
    }

    public boolean isVolumeLevelChanging(Duration timeout) {
        return volumeBar.isChanging(timeout);
    }

    public boolean isVolumeLevelNotChanging(Duration timeout) {
        return volumeBar.isNotChanging(timeout);
    }
}
