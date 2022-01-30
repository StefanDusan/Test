package com.intpfy.gui.components.speaker;

import com.intpfy.gui.components.common.VolumeBarComponent;
import com.intpfy.gui.components.common.VolumeControlComponent;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

import java.time.Duration;

public class SpeakersComponent extends BaseComponent {

    private final VolumeBarComponent volumeBar;
    private final VolumeControlComponent volumeControl;

    public SpeakersComponent(IParent parent) {
        super("Speakers", parent, By.cssSelector(".stream-session._outgoing"));
        volumeBar = new VolumeBarComponent(this);
        volumeControl = new VolumeControlComponent(this);
    }

    public void mute() {
        volumeControl.mute();
    }

    public void unmute() {
        volumeControl.unmute();
    }

    public boolean isMuted(Duration timeout) {
        return volumeControl.isMuted(timeout);
    }

    public boolean isUnmuted(Duration timeout) {
        return volumeControl.isUnmuted(timeout);
    }

    public boolean isVolumeLevelChanging(Duration timeout) {
        return volumeBar.isChanging(timeout);
    }

    public boolean isVolumeLevelNotChanging(Duration timeout) {
        return volumeBar.isNotChanging(timeout);
    }

    public void setMaxVolumeLevel() {
        volumeControl.setMaxVolumeLevel();
    }

    public void setMinVolumeLevel() {
        volumeControl.setMinVolumeLevel();
    }

    public boolean isVolumeLevelMax(Duration timeout) {
        return volumeControl.isVolumeLevelMax(timeout);
    }

    public boolean isVolumeLevelMin(Duration timeout) {
        return volumeControl.isVolumeLevelMin(timeout);
    }
}
