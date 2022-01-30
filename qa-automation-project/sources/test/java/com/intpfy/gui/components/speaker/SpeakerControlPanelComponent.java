package com.intpfy.gui.components.speaker;

import com.intpfy.gui.dialogs.common.LanguageSettingsDW;
import com.intpfy.model.Language;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

import java.time.Duration;

public class SpeakerControlPanelComponent extends BaseComponent {

    private final InterpretingComponent interpreting;
    private final SpeakersComponent speakers;
    private final StreamComponent stream;

    public SpeakerControlPanelComponent(IParent parent) {
        super("Speaker control panel", parent, By.cssSelector("div.control-panel"));
        interpreting = new InterpretingComponent(this);
        speakers = new SpeakersComponent(this);
        stream = new StreamComponent(this);
    }

    public LanguageSettingsDW selectInterpretingLanguage() {
        return interpreting.selectLanguage();
    }

    public boolean isNoInterpretingLanguageSelected(Duration timeout) {
        return interpreting.isNoLanguageSelected(timeout);
    }

    public void raiseHand() {
        stream.raiseHand();
    }

    public void lowerHand() {
        stream.lowerHand();
    }

    public boolean isHandRaised(Duration timeout) {
        return stream.isHandRaised(timeout);
    }

    public boolean isHandDown(Duration timeout) {
        return stream.isHandDown(timeout);
    }

    public void connect() {
        stream.connect();
    }

    public void disconnect() {
        stream.disconnect();
    }

    public boolean isConnected(Duration timeout) {
        return stream.isConnected(timeout);
    }

    public boolean isDisconnected(Duration timeout) {
        return stream.isDisconnected(timeout);
    }

    // Method should be used only for Speakers which accepted streaming invite from Host
    public void stopStreaming() {
        stream.stopStreaming();
    }

    public boolean areStreamControlsDisabled(Duration timeout) {
        return stream.areControlsDisabled(timeout);
    }

    public void turnMicOn() {
        stream.turnMicOn();
    }

    public void turnMicOff() {
        stream.turnMicOff();
    }

    public boolean isMicOn(Duration timeout) {
        return stream.isMicOn(timeout);
    }

    public boolean isMicOff(Duration timeout) {
        return stream.isMicOff(timeout);
    }

    public boolean isMuted(Duration timeout) {
        return stream.isMuted(timeout);
    }

    public void turnCameraOn() {
        stream.turnCameraOn();
    }

    public void turnCameraOff() {
        stream.turnCameraOff();
    }

    public boolean isCameraOn(Duration timeout) {
        return stream.isCameraOn(timeout);
    }

    public boolean isCameraOff(Duration timeout) {
        return stream.isCameraOff(timeout);
    }

    public void enableScreenSharing() {
        stream.enableScreenSharing();
    }

    public void disableScreenSharing() {
        stream.disableScreenSharing();
    }

    public boolean isScreenSharingEnabled(Duration timeout) {
        return stream.isScreenSharingEnabled(timeout);
    }

    public boolean isScreenSharingDisabled(Duration timeout) {
        return stream.isScreenSharingDisabled(timeout);
    }

    public boolean isAutoVolumeAvailable(Duration timeout) {
        return interpreting.isAutoVolumeAvailable(timeout);
    }

    public boolean isAutoVolumeUnavailable(Duration timeout) {
        return interpreting.isAutoVolumeUnavailable(timeout);
    }

    public void enableAutoVolume() {
        interpreting.enableAutoVolume();
    }

    public void disableAutoVolume() {
        interpreting.disableAutoVolume();
    }

    public boolean isAutoVolumeEnabled(Duration timeout) {
        return interpreting.isAutoVolumeEnabled(timeout);
    }

    public boolean isAutoVolumeDisabled(Duration timeout) {
        return interpreting.isAutoVolumeDisabled(timeout);
    }

    public boolean isInterpretingLanguageSelected(Language language, Duration timeout) {
        return interpreting.isLanguageSelected(language, timeout);
    }

    public boolean isVolumeLevelChanging(Duration timeout) {
        return stream.isVolumeLevelChanging(timeout);
    }

    public boolean isVolumeLevelNotChanging(Duration timeout) {
        return stream.isVolumeLevelNotChanging(timeout);
    }

    public void muteSourceChannel() {
        speakers.mute();
    }

    public void muteLanguageChannel() {
        interpreting.mute();
    }

    public void unmuteSourceChannel() {
        speakers.unmute();
    }

    public void unmuteLanguageChannel() {
        interpreting.unmute();
    }

    public boolean isSourceChannelMuted(Duration timeout) {
        return speakers.isMuted(timeout);
    }

    public boolean isLanguageChannelMuted(Duration timeout) {
        return interpreting.isMuted(timeout);
    }

    public boolean isSourceChannelUnmuted(Duration timeout) {
        return speakers.isUnmuted(timeout);
    }

    public boolean isLanguageChannelUnmuted(Duration timeout) {
        return interpreting.isUnmuted(timeout);
    }

    public boolean isSourceChannelVolumeLevelChanging(Duration timeout) {
        return speakers.isVolumeLevelChanging(timeout);
    }

    public boolean isLanguageChannelVolumeLevelChanging(Duration timeout) {
        return interpreting.isVolumeLevelChanging(timeout);
    }

    public boolean isSourceChannelVolumeLevelNotChanging(Duration timeout) {
        return speakers.isVolumeLevelNotChanging(timeout);
    }

    public boolean isLanguageChannelVolumeLevelNotChanging(Duration timeout) {
        return interpreting.isVolumeLevelNotChanging(timeout);
    }

    public void setMaxVolumeLevelForSourceChannel() {
        speakers.setMaxVolumeLevel();
    }

    public void setMaxVolumeLevelForLanguageChannel() {
        interpreting.setMaxVolumeLevel();
    }

    public void setMinVolumeLevelForSourceChannel() {
        speakers.setMinVolumeLevel();
    }

    public void setMinVolumeLevelForLanguageChannel() {
        interpreting.setMinVolumeLevel();
    }

    public boolean isVolumeLevelMaxForSourceChannel(Duration timeout) {
        return speakers.isVolumeLevelMax(timeout);
    }

    public boolean isVolumeLevelMaxForLanguageChannel(Duration timeout) {
        return interpreting.isVolumeLevelMax(timeout);
    }

    public boolean isVolumeLevelMinForSourceChannel(Duration timeout) {
        return speakers.isVolumeLevelMin(timeout);
    }

    public boolean isVolumeLevelMinForLanguageChannel(Duration timeout) {
        return interpreting.isVolumeLevelMin(timeout);
    }
}
