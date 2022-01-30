package com.intpfy.gui.components.moderator;

import com.intpfy.gui.components.chats.EventChatComponent;
import com.intpfy.gui.dialogs.moderator.SessionsDW;
import com.intpfy.gui.dialogs.moderator.StartVoicePublishingDW;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class SourceSessionComponent extends BaseComponent {

    @ElementInfo(name = "Handover", findBy = @FindBy(className = "floor-control-header__sessions"))
    private Button handoverButton;

    @ElementInfo(name = "Hide section", findBy = @FindBy(className = "floor-control-header__source-toggle"))
    private Button hideSectionButton;

    private final SourceVideoControlComponent videoControl;
    private final SessionMicAndVolumeSettingsComponent micAndVolumeSettings;
    private final EventChatComponent eventChat;

    public SourceSessionComponent(IParent parent) {
        super("Source session", parent, By.cssSelector("div.floor-control"));
        videoControl = new SourceVideoControlComponent(this);
        micAndVolumeSettings = new SessionMicAndVolumeSettingsComponent(this);
        eventChat = new EventChatComponent(this);
    }

    public SourceSessionComponent(String name, IParent parent) {
        super(name, parent, By.cssSelector("div.floor-control"));
        videoControl = new SourceVideoControlComponent(this);
        micAndVolumeSettings = new SessionMicAndVolumeSettingsComponent(this);
        eventChat = new EventChatComponent(this);
    }

    public SessionsDW openSessionsDW() {
        handoverButton.clickByJS();
        return new SessionsDW(this);
    }

    public void hide() {
        hideSectionButton.clickByJS();
    }

    public void turnVolumeOn() {
        micAndVolumeSettings.turnVolumeOn();
    }

    public void turnVolumeOff() {
        micAndVolumeSettings.turnVolumeOff();
    }

    public boolean isVolumeOn() {
        return micAndVolumeSettings.isVolumeOn();
    }

    public boolean isVolumeOff() {
        return micAndVolumeSettings.isVolumeOff();
    }

    public boolean isIncomingVolumeLevelChanging(Duration timeout) {
        return micAndVolumeSettings.isIncomingVolumeLevelChanging(timeout);
    }

    public boolean isOutgoingVolumeLevelChanging(Duration timeout) {
        return micAndVolumeSettings.isOutgoingVolumeLevelChanging(timeout);
    }

    public boolean isIncomingVolumeLevelNotChanging(Duration timeout) {
        return micAndVolumeSettings.isIncomingVolumeLevelNotChanging(timeout);
    }

    public boolean isOutgoingVolumeLevelNotChanging(Duration timeout) {
        return micAndVolumeSettings.isOutgoingVolumeLevelNotChanging(timeout);
    }

    public StartVoicePublishingDW turnMicOn() {
        return micAndVolumeSettings.turnMicOn();
    }

    public void turnMicOff() {
        micAndVolumeSettings.turnMicOff();
    }

    public boolean isMicOn(Duration timeout) {
        return micAndVolumeSettings.isMicOn(timeout);
    }

    public boolean isMicOff(Duration timeout) {
        return micAndVolumeSettings.isMicOff(timeout);
    }

    public void turnVideoOn() {
        videoControl.turnOn();
    }

    public void turnVideoOff() {
        videoControl.turnOff();
    }

    public boolean isVideoOn(Duration timeout) {
        return videoControl.isOn(timeout);
    }

    public boolean isVideoOff(Duration timeout) {
        return videoControl.isOff(timeout);
    }

    public boolean isVideoContainerVisible(String streamerName, Duration timeout) {
        return videoControl.isVideoContainerVisible(streamerName, timeout);
    }

    public boolean isScreenContainerVisible(String streamerName, Duration timeout) {
        return videoControl.isScreenContainerVisible(streamerName, timeout);
    }

    public boolean isVideoContainerNotVisible(String streamerName, Duration timeout) {
        return videoControl.isVideoContainerNotVisible(streamerName, timeout);
    }

    public boolean isScreenContainerNotVisible(String streamerName, Duration timeout) {
        return videoControl.isScreenContainerNotVisible(streamerName, timeout);
    }

    public EventChatComponent getEventChat() {
        return eventChat;
    }
}
