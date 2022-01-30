package com.intpfy.gui.components.speaker;

import com.intpfy.gui.components.common.AutoVolumeComponent;
import com.intpfy.gui.components.common.LanguageCellComponent;
import com.intpfy.gui.components.common.VolumeControlComponent;
import com.intpfy.gui.dialogs.common.LanguageSettingsDW;
import com.intpfy.model.Language;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

import java.time.Duration;

public class InterpretingComponent extends BaseComponent {

    private final AutoVolumeComponent autoVolume;
    private final LanguageCellComponent languageCell;
    private final VolumeControlComponent volumeControl;

    public InterpretingComponent(IParent parent) {
        super("Interpreting", parent, By.cssSelector(".stream-session._incoming"));
        autoVolume = new AutoVolumeComponent(this);
        languageCell = new LanguageCellComponent(this);
        volumeControl = new VolumeControlComponent(this);
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

    public LanguageSettingsDW selectLanguage() {
        languageCell.click();
        return new LanguageSettingsDW(getPage());
    }

    public boolean isLanguageSelected(Language language, Duration timeout) {
        return languageCell.isSelected(language, timeout);
    }

    public boolean isNoLanguageSelected(Duration timeout) {
        return languageCell.isNoLanguageSelected(timeout);
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
        return languageCell.isVolumeLevelChanging(timeout);
    }

    public boolean isVolumeLevelNotChanging(Duration timeout) {
        return languageCell.isVolumeLevelNotChanging(timeout);
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
