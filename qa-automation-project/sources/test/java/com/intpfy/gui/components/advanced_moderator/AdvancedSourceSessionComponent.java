package com.intpfy.gui.components.advanced_moderator;

import com.intpfy.gui.components.moderator.SourceSessionComponent;
import com.intpfy.gui.components.moderator.UsersComponent;
import com.intpfy.gui.dialogs.moderator.SetActiveChannelsDW;
import com.intpfy.gui.dialogs.moderator.UsersSessionDW;
import com.intpfy.model.Language;
import com.intpfyqa.gui.web.selenium.IParent;

import java.time.Duration;

public class AdvancedSourceSessionComponent extends SourceSessionComponent {

    private final RecordingComponent recording;
    private final UsersComponent users;

    public AdvancedSourceSessionComponent(IParent parent) {
        super("Advanced source session", parent);
        recording = new RecordingComponent(this);
        users = new UsersComponent(this, Language.Source);
    }

    public void enableRecording() {
        recording.enable();
    }

    public void disableRecording() {
        recording.disable();
    }

    public boolean isRecordingEnabled(Duration timeout) {
        return recording.isEnabled(timeout);
    }

    public boolean isRecordingDisabled(Duration timeout) {
        return recording.isDisabled(timeout);
    }

    public boolean isUserPresent(String username, Duration timeout) {
        return users.isPresent(username, timeout);
    }

    public boolean isUserNotPresent(String username, Duration timeout) {
        return users.isNotPresent(username, timeout);
    }

    public boolean userCanStream(String username, Duration timeout) {
        return users.canStream(username, timeout);
    }

    public boolean userCanListen(String username, Duration timeout) {
        return users.canListen(username, timeout);
    }

    public boolean userHasIncomingLanguage(String username, Language language, Duration timeout) {
        return users.hasIncomingLanguage(username, language, timeout);
    }

    public boolean userHasOutgoingLanguage(String username, Language language, Duration timeout) {
        return users.hasOutgoingLanguage(username, language, timeout);
    }

    public boolean isUserListReloading(Duration timeout) {
        return users.isReloading(timeout);
    }

    public boolean isUserListNotReloading(Duration timeout) {
        return users.isNotReloading(timeout);
    }

    public SetActiveChannelsDW openSetActiveChannelsDW(String username) {
        return users.openSetActiveChannelsDW(username);
    }

    public UsersSessionDW openUsersSessionDW() {
        return users.openUsersSessionDW();
    }

    public void switchUsersToLobby() {
        users.switchToLobby();
    }

    public void switchUsersToUsers() {
        users.switchToUsers();
    }

    public boolean isUsersOfLobbyType(Duration timeout) {
        return users.isOfLobbyType(timeout);
    }

    public boolean isUsersOfUsersType(Duration timeout) {
        return users.isOfUsersType(timeout);
    }

    public boolean areUsersLobbyActionsAvailable(Duration timeout) {
        return users.areLobbyActionsAvailable(timeout);
    }

    public boolean isLobbySpeakersCountEqual(int count, Duration timeout) {
        return users.isLobbySpeakersCountEqual(count, timeout);
    }

    public boolean isLobbySpeakersCountNotVisible(Duration timeout) {
        return users.isLobbySpeakersCountNotVisible(timeout);
    }

    public boolean isSpeakerPresentInLobby(String speakerName, Duration timeout) {
        return users.isPresentInLobby(speakerName, timeout);
    }

    public boolean isSpeakerNotPresentInLobby(String speakerName, Duration timeout) {
        return users.isNotPresentInLobby(speakerName, timeout);
    }

    public boolean areSpeakerLobbyActionsAvailable(String speakerName, Duration timeout) {
        return users.areLobbyActionsAvailable(speakerName, timeout);
    }

    public void admitSpeakerInLobby(String speakerName) {
        users.admitInLobby(speakerName);
    }

    public void rejectSpeakerInLobby(String speakerName) {
        users.rejectInLobby(speakerName);
    }

    public void admitAllSpeakersInLobby() {
        users.admitAllInLobby();
    }

    public void rejectAllSpeakersInLobby() {
        users.rejectAllInLobby();
    }
}
