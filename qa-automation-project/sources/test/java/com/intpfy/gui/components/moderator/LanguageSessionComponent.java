package com.intpfy.gui.components.moderator;

import com.intpfy.gui.components.chats.PartnerChatComponent;
import com.intpfy.gui.dialogs.common.ChatDW;
import com.intpfy.gui.dialogs.moderator.StartVoicePublishingDW;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

import java.time.Duration;

public class LanguageSessionComponent extends BaseComponent {

    protected final SessionMicAndVolumeSettingsComponent micAndVolumeSettings;
    protected final PartnerChatComponent partnerChat;

    public LanguageSessionComponent(String name, IParent parent, By componentLocator) {
        super(name, parent, componentLocator);
        micAndVolumeSettings = new SessionMicAndVolumeSettingsComponent(this);
        partnerChat = new PartnerChatComponent(this);
    }

    public void sendMessageToChat(String message) {
        partnerChat.sendMessage(message);
    }

    public boolean messageExistsInChat(String message, Duration timeout) {
        return partnerChat.messageExists(message, timeout);
    }

    public ChatDW openChat() {
        return partnerChat.open();
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
}