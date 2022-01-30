package com.intpfy.gui.components.speaker.speakers;

import com.intpfy.gui.components.speaker.SpeakerUserComponent;
import com.intpfy.gui.dialogs.logout.LogOutUserDW;
import com.intpfy.gui.dialogs.speaker.DisableStreamingDW;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

import java.time.Duration;

public class SpeakersComponent extends BaseComponent {

    private final SpeakersHeaderComponent speakersHeader;
    private final LobbyActionsComponent lobbyActions;
    private final LobbyContainerComponent lobbyContainer;

    public SpeakersComponent(IParent parent) {
        super("Speakers", parent, By.cssSelector("div.user-list"));
        speakersHeader = new SpeakersHeaderComponent(this);
        lobbyActions = new LobbyActionsComponent(this);
        lobbyContainer = new LobbyContainerComponent(this);
    }

    public boolean hasHostRoleInParticipants(String speakerName, Duration timeout) {
        return createSpeakerUserComponent(speakerName).hasHostRole(timeout);
    }

    public boolean isPresentInParticipants(String speakerName, Duration timeout) {
        return createSpeakerUserComponent(speakerName).visible(timeout);
    }

    public boolean isNotPresentInParticipants(String speakerName, Duration timeout) {
        return createSpeakerUserComponent(speakerName).notVisible(timeout);
    }

    public boolean isHandRaisedInParticipants(String speakerName, Duration timeout) {
        return createSpeakerUserComponent(speakerName).isHandRaised(timeout);
    }

    public boolean isHandDownInParticipants(String speakerName, Duration timeout) {
        return createSpeakerUserComponent(speakerName).isHandDown(timeout);
    }

    public void allowToStreamInParticipants(String speakerName) {
        createSpeakerUserComponent(speakerName).allowToStream();
    }

    public DisableStreamingDW disallowToStreamInParticipants(String speakerName) {
        return createSpeakerUserComponent(speakerName).disallowToStream();
    }

    public boolean isAskedToStreamInParticipants(String speakerName, Duration timeout) {
        return createSpeakerUserComponent(speakerName).isAskedToStream(timeout);
    }

    public boolean isStreamingInParticipants(String speakerName, Duration timeout) {
        return createSpeakerUserComponent(speakerName).isStreaming(timeout);
    }

    public boolean isNotStreamingInParticipants(String speakerName, Duration timeout) {
        return createSpeakerUserComponent(speakerName).isNotStreaming(timeout);
    }

    public LogOutUserDW logOutFromParticipants(String speakerName) {
        SpeakerUserComponent speakerUserComponent = createSpeakerUserComponent(speakerName);
        speakerUserComponent.visible();
        return speakerUserComponent.logOut();
    }

    public void switchToLobby() {
        speakersHeader.switchToLobby();
    }

    public boolean isOfLobbyType(Duration timeout) {
        return speakersHeader.isOfLobbyType(timeout);
    }

    public boolean areLobbyActionsAvailable(Duration timeout) {
        return lobbyActions.areAvailable(timeout);
    }

    public void switchToParticipants() {
        speakersHeader.switchToParticipants();
    }

    public boolean isOfParticipantsType(Duration timeout) {
        return speakersHeader.isOfParticipantsType(timeout);
    }

    public boolean areLobbyActionsAvailable(String speakerName, Duration timeout) {
        return lobbyContainer.areActionsAvailable(speakerName, timeout);
    }

    public boolean isLobbySpeakersCountEqual(int count, Duration timeout) {
        return speakersHeader.isLobbySpeakersCountEqual(count, timeout);
    }

    public boolean isPresentInLobby(String speakerName, Duration timeout) {
        return lobbyContainer.isPresent(speakerName, timeout);
    }

    public boolean isNotPresentInLobby(String speakerName, Duration timeout) {
        return lobbyContainer.isNotPresent(speakerName, timeout);
    }

    public void admitInLobby(String speakerName) {
        lobbyContainer.admit(speakerName);
    }

    public void rejectInLobby(String speakerName) {
        lobbyContainer.reject(speakerName);
    }

    public void admitAllInLobby() {
        lobbyActions.admitAll();
    }

    public void rejectAllInLobby() {
        lobbyActions.rejectAll();
    }

    public void searchInLobby(String speakerName) {
        speakersHeader.searchInLobby(speakerName);
    }

    private SpeakerUserComponent createSpeakerUserComponent(String name) {
        return new SpeakerUserComponent(this, name);
    }
}
