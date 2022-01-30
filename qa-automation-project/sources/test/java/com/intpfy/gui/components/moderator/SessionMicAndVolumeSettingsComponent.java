package com.intpfy.gui.components.moderator;

import com.intpfy.gui.dialogs.moderator.StartVoicePublishingDW;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

import java.time.Duration;

public class SessionMicAndVolumeSettingsComponent extends BaseComponent {

    private final SessionMicControlComponent micControl;
    private final SessionVolumeControlComponent volumeControl;

    public SessionMicAndVolumeSettingsComponent(IParent parent) {
        super("Mic and volume settings", parent, By.xpath(".//div[contains(@class, 'control__setting')]"));
        micControl = new SessionMicControlComponent(this);
        volumeControl = new SessionVolumeControlComponent(this);
    }

    public void turnVolumeOn() {
        volumeControl.turnOn();
    }

    public void turnVolumeOff() {
        volumeControl.turnOff();
    }

    public boolean isVolumeOn() {
        return volumeControl.isOn();
    }

    public boolean isVolumeOff() {
        return volumeControl.isOff();
    }

    public boolean isIncomingVolumeLevelChanging(Duration timeout) {
        return volumeControl.isLevelChanging(timeout);
    }

    public boolean isIncomingVolumeLevelNotChanging(Duration timeout) {
        return volumeControl.isLevelNotChanging(timeout);
    }

    public StartVoicePublishingDW turnMicOn() {
        return micControl.turnOn();
    }

    public void turnMicOff() {
        micControl.turnOff();
    }

    public boolean isMicOn(Duration timeout) {
        return micControl.isOn(timeout);
    }

    public boolean isMicOff(Duration timeout) {
        return micControl.isOff(timeout);
    }

    public boolean isOutgoingVolumeLevelChanging(Duration timeout) {
        return micControl.isVolumeLevelChanging(timeout);
    }

    public boolean isOutgoingVolumeLevelNotChanging(Duration timeout) {
        return micControl.isVolumeLevelNotChanging(timeout);
    }
}