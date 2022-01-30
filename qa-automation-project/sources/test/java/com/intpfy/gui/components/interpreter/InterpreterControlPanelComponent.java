package com.intpfy.gui.components.interpreter;

import com.intpfy.model.Language;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

import java.time.Duration;

public class InterpreterControlPanelComponent extends BaseComponent {

    private final StreamComponent incomingStream;
    private final StreamComponent outgoingStream;
    private final MuteIndicatorComponent muteIndicator;
    private final HandoverComponent handover;

    public InterpreterControlPanelComponent(IParent parent) {
        super("Interpreter control panel", parent, By.cssSelector("div.control-panel"));
        incomingStream = new StreamComponent("Incoming stream", this, By.cssSelector(".stream-session._incoming"));
        outgoingStream = new StreamComponent("Outgoing stream", this, By.cssSelector(".stream-session._outgoing"));
        muteIndicator = new MuteIndicatorComponent(this);
        handover = new HandoverComponent(this);
    }

    public void changeIncomingLanguageToRelay() {
        incomingStream.changeLanguageToSecond();
    }

    public void changeOutgoingLanguageToRelay() {
        outgoingStream.changeLanguageToSecond();
    }

    public void changeIncomingLanguageToSource() {
        incomingStream.changeLanguageToFirst();
    }

    public void changeOutgoingLanguageToMain() {
        outgoingStream.changeLanguageToFirst();
    }

    public void switchMute() {
        muteIndicator.switchMute();
    }

    public void mute() {
        muteIndicator.mute();
    }

    public void unmute() {
        muteIndicator.unmute();
    }

    public boolean isMuted(Duration timeout) {
        return muteIndicator.isMuted(timeout);
    }

    public boolean isUnmuted(Duration timeout) {
        return muteIndicator.isUnmuted(timeout);
    }

    public boolean isMuteIndicatorDisabled(Duration timeout) {
        return muteIndicator.isDisabled(timeout);
    }

    public boolean isVolumeLevelChanging(Duration timeout) {
        return muteIndicator.isVolumeLevelChanging(timeout);
    }

    public boolean isHandoverAvailable() {
        return handover.isAvailable();
    }

    public boolean isHandoverUnavailable() {
        return handover.isUnavailable();
    }

    public void requestHandoverNow() {
        handover.requestNow();
    }

    public boolean isHandoverWaitingForPartnerResponse() {
        return handover.isWaitingForPartnerResponse();
    }

    public void muteIncomingChannel() {
        incomingStream.mute();
    }

    public void muteOutgoingChannel() {
        outgoingStream.mute();
    }

    public void unmuteIncomingChannel() {
        incomingStream.unmute();
    }

    public void unmuteOutgoingChannel() {
        outgoingStream.unmute();
    }

    public boolean isIncomingChannelMuted(Duration timeout) {
        return incomingStream.isMuted(timeout);
    }

    public boolean isOutgoingChannelMuted(Duration timeout) {
        return outgoingStream.isMuted(timeout);
    }

    public boolean isIncomingChannelUnmuted(Duration timeout) {
        return incomingStream.isUnmuted(timeout);
    }

    public boolean isOutgoingChannelUnmuted(Duration timeout) {
        return outgoingStream.isUnmuted(timeout);
    }

    public boolean isIncomingChannelVolumeLevelChanging(Duration timeout) {
        return incomingStream.isVolumeLevelChanging(timeout);
    }

    public boolean isOutgoingChannelVolumeLevelChanging(Duration timeout) {
        return outgoingStream.isVolumeLevelChanging(timeout);
    }

    public boolean isIncomingChannelVolumeLevelNotChanging(Duration timeout) {
        return incomingStream.isVolumeLevelNotChanging(timeout);
    }

    public boolean isOutgoingChannelVolumeLevelNotChanging(Duration timeout) {
        return outgoingStream.isVolumeLevelNotChanging(timeout);
    }

    public void setMaxVolumeLevelForIncomingChannel() {
        incomingStream.setMaxVolumeLevel();
    }

    public void setMaxVolumeLevelForOutgoingChannel() {
        outgoingStream.setMaxVolumeLevel();
    }

    public void setMinVolumeLevelForIncomingChannel() {
        incomingStream.setMinVolumeLevel();
    }

    public void setMinVolumeLevelForOutgoingChannel() {
        outgoingStream.setMinVolumeLevel();
    }

    public boolean isVolumeLevelMaxForIncomingChannel(Duration timeout) {
        return incomingStream.isVolumeLevelMax(timeout);
    }

    public boolean isVolumeLevelMaxForOutgoingChannel(Duration timeout) {
        return outgoingStream.isVolumeLevelMax(timeout);
    }

    public boolean isVolumeLevelMinForIncomingChannel(Duration timeout) {
        return incomingStream.isVolumeLevelMin(timeout);
    }

    public boolean isVolumeLevelMinForOutgoingChannel(Duration timeout) {
        return outgoingStream.isVolumeLevelMin(timeout);
    }

    public boolean isCoughing(Duration timeout) {
        return muteIndicator.isCoughing(timeout);
    }

    public boolean isNotCoughing(Duration timeout) {
        return muteIndicator.isNotCoughing(timeout);
    }

    public boolean isShortcutsIndicatorDisplayedForIncomingChannel(Duration timeout) {
        return incomingStream.isShortcutsIndicatorDisplayed(timeout);
    }

    public boolean isShortcutsIndicatorDisplayedForOutgoingChannel(Duration timeout) {
        return outgoingStream.isShortcutsIndicatorDisplayed(timeout);
    }

    public boolean isShortcutsIndicatorNotDisplayedForIncomingChannel(Duration timeout) {
        return incomingStream.isShortcutsIndicatorNotDisplayed(timeout);
    }

    public boolean isShortcutsIndicatorNotDisplayedForOutgoingChannel(Duration timeout) {
        return outgoingStream.isShortcutsIndicatorNotDisplayed(timeout);
    }

    public boolean isIncomingLanguageActive(Language language, Duration timeout) {
        return incomingStream.isLanguageActive(language, timeout);
    }

    public boolean isOutgoingLanguageActive(Language language, Duration timeout) {
        return outgoingStream.isLanguageActive(language, timeout);
    }
}
