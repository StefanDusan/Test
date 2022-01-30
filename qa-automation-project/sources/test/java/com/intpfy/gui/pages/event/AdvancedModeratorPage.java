package com.intpfy.gui.pages.event;

import com.intpfy.gui.components.advanced_moderator.AdvancedLanguageSessionComponent;
import com.intpfy.gui.components.advanced_moderator.AdvancedSourceSessionComponent;
import com.intpfy.gui.dialogs.logout.LogOutFromEventDW;
import com.intpfy.gui.dialogs.moderator.*;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.model.Language;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.settings.WebSettings;

import java.time.Duration;

public class AdvancedModeratorPage extends ModeratorPage {

    private final AdvancedSourceSessionComponent sourceSessionComponent;

    public AdvancedModeratorPage(IPageContext pageContext) {
        super("Advanced Moderator page", pageContext);
        sourceSessionComponent = new AdvancedSourceSessionComponent(this);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return super.isOpened(timeout) && sourceSessionComponent.visible(timeout);
    }

    @Override
    public LoginPage logOut() {

        info("Log out.");

        logOutButton.click();
        LogOutFromEventDW logOutFromEventDW = new LogOutFromEventDW(this);

        if (logOutFromEventDW.visible(WebSettings.AJAX_TIMEOUT)) {

            logOutFromEventDW.assertIsOpened(Duration.ZERO);
            logOutFromEventDW.confirm();
            logOutFromEventDW.assertNotVisible();

        } else {

            EventRecordingIsOnDW eventRecordingIsOnDW = new EventRecordingIsOnDW(this);
            eventRecordingIsOnDW.assertIsOpened();

            eventRecordingIsOnDW.confirm();
            eventRecordingIsOnDW.assertNotVisible();
        }

        return new LoginPage(getPageContext());
    }

    public void enableRecordingForSourceSession() {
        info("Enable recording for Source session.");
        sourceSessionComponent.enableRecording();
    }

    public RecordingNotificationDW disableRecordingForSourceSession() {
        info("Disable recording for Source session.");
        sourceSessionComponent.disableRecording();
        return new RecordingNotificationDW(this);
    }

    public boolean isRecordingForSourceSessionEnabled(Duration timeout) {
        return sourceSessionComponent.isRecordingEnabled(timeout);
    }

    public boolean isRecordingForSourceSessionDisabled(Duration timeout) {
        return sourceSessionComponent.isRecordingDisabled(timeout);
    }

    public void enableRecordingForLanguageSession(Language language) {
        info(String.format("Enable recording for '%s' language session.", language));
        createAdvancedLanguageSessionComponent(language).enableRecording();
    }

    public RecordingNotificationDW disableRecordingForLanguageSession(Language language) {
        info(String.format("Disable recording for '%s' language session.", language));
        createAdvancedLanguageSessionComponent(language).disableRecording();
        return new RecordingNotificationDW(this);
    }

    public boolean isRecordingForLanguageSessionEnabled(Language language, Duration timeout) {
        return createAdvancedLanguageSessionComponent(language).isRecordingEnabled(timeout);
    }

    public boolean isRecordingForLanguageSessionDisabled(Language language, Duration timeout) {
        return createAdvancedLanguageSessionComponent(language).isRecordingDisabled(timeout);
    }

    public void showPartnerChat(Language chatLanguage) {
        info(String.format("Show Partner chat for language '%s'.", chatLanguage));
        createAdvancedLanguageSessionComponent(chatLanguage).showChat();
    }

    public void hidePartnerChat(Language chatLanguage) {
        info(String.format("Hide Partner chat for language '%s'.", chatLanguage));
        createAdvancedLanguageSessionComponent(chatLanguage).hideChat();
    }

    public boolean isPartnerChatShown(Language chatLanguage, Duration timeout) {
        return createAdvancedLanguageSessionComponent(chatLanguage).isChatShown(timeout);
    }

    public boolean isPartnerChatHidden(Language chatLanguage, Duration timeout) {
        return createAdvancedLanguageSessionComponent(chatLanguage).isChatHidden(timeout);
    }

    public boolean isUserPresentInSourceSession(String username, Duration timeout) {
        return sourceSessionComponent.isUserPresent(username, timeout);
    }

    public boolean isUserNotPresentInSourceSession(String username, Duration timeout) {
        return sourceSessionComponent.isUserNotPresent(username, timeout);
    }

    public boolean isUserPresentInLanguageSession(String username, Language language, Duration timeout) {
        return createAdvancedLanguageSessionComponent(language).isUserPresent(username, timeout);
    }

    public boolean userCanStreamInSourceSession(String username, Duration timeout) {
        return sourceSessionComponent.userCanStream(username, timeout);
    }

    public boolean userCanStreamInLanguageSession(String username, Language language, Duration timeout) {
        return createAdvancedLanguageSessionComponent(language).userCanStream(username, timeout);
    }

    public boolean userCanListenInSourceSession(String username, Duration timeout) {
        return sourceSessionComponent.userCanListen(username, timeout);
    }

    public boolean userCanListenInLanguageSession(String username, Language language, Duration timeout) {
        return createAdvancedLanguageSessionComponent(language).userCanListen(username, timeout);
    }

    public boolean userHasIncomingLanguageInSourceSession(String username, Language userLanguage, Duration timeout) {
        return sourceSessionComponent.userHasIncomingLanguage(username, userLanguage, timeout);
    }

    public boolean userHasIncomingLanguageInLanguageSession(String username, Language sessionLanguage, Language userLanguage, Duration timeout) {
        return createAdvancedLanguageSessionComponent(sessionLanguage).userHasIncomingLanguage(username, userLanguage, timeout);
    }

    public boolean userHasOutgoingLanguageInSourceSession(String username, Language userLanguage, Duration timeout) {
        return sourceSessionComponent.userHasOutgoingLanguage(username, userLanguage, timeout);
    }

    public boolean userHasOutgoingLanguageInLanguageSession(String username, Language sessionLanguage, Language userLanguage, Duration timeout) {
        return createAdvancedLanguageSessionComponent(sessionLanguage).userHasOutgoingLanguage(username, userLanguage, timeout);
    }

    public boolean isUserListReloadingInSourceSession(Duration timeout) {
        return sourceSessionComponent.isUserListReloading(timeout);
    }

    public boolean isUserListNotReloadingInSourceSession(Duration timeout) {
        return sourceSessionComponent.isUserListNotReloading(timeout);
    }

    public boolean isUserMutedInLanguageSession(String username, Language language, Duration timeout) {
        return createAdvancedLanguageSessionComponent(language).isUserMuted(username, timeout);
    }

    public boolean isUserUnmutedInLanguageSession(String username, Language language, Duration timeout) {
        return createAdvancedLanguageSessionComponent(language).isUserUnmuted(username, timeout);
    }

    public boolean isStreamingMultipleInLanguageSession(Language language, Duration timeout) {
        return createAdvancedLanguageSessionComponent(language).isStreamingMultiple(timeout);
    }

    public boolean isStreamingNotMultipleInLanguageSession(Language language, Duration timeout) {
        return createAdvancedLanguageSessionComponent(language).isStreamingNotMultiple(timeout);
    }

    public int getUserOrdinalPositionInLanguageSession(String username, Language language) {
        return createAdvancedLanguageSessionComponent(language).getUserOrdinalPosition(username);
    }

    public RecordingSettingsDW openRecordingSettings() {
        info("Open Recording settings.");
        return header.openRecordingSettings();
    }

    public UsersSessionDW openUsersSessionDWForSourceSession() {
        info("Open 'Users session' dialog window for Source session.");
        return sourceSessionComponent.openUsersSessionDW();
    }

    public UsersSessionDW openUsersSessionDWForLanguageSession(Language language) {
        info(String.format("Open 'Users session' dialog window for '%s' language session.", language));
        return createAdvancedLanguageSessionComponent(language).openUsersSessionDW();
    }

    public SetActiveChannelsDW openSetActiveChannelsDWFromSourceSession(String username) {
        info(String.format("Open 'Set active channels' dialog window from Source session for user '%s'.", username));
        return sourceSessionComponent.openSetActiveChannelsDW(username);
    }

    public SetActiveChannelsDW openSetActiveChannelsDWForLanguageSession(String username, Language language) {
        info(String.format("Open 'Set active channels' dialog window for '%s' language session.", language));
        return createAdvancedLanguageSessionComponent(language).openSetActiveChannelsDW(username);
    }

    public void switchSourceSessionUsersToLobby() {
        info("Switch Source session Users to Lobby.");
        sourceSessionComponent.switchUsersToLobby();
    }

    public void switchSourceSessionUsersToUsers() {
        info("Switch Source session Users to Users.");
        sourceSessionComponent.switchUsersToUsers();
    }

    public void admitSpeakerInLobbyInSourceSession(String speakerName) {
        info(String.format("Admit Speaker '%s' in Lobby in Source session.", speakerName));
        sourceSessionComponent.admitSpeakerInLobby(speakerName);
    }

    public void rejectSpeakerInLobbyInSourceSession(String speakerName) {
        info(String.format("Reject Speaker '%s' in Lobby in Source session.", speakerName));
        sourceSessionComponent.rejectSpeakerInLobby(speakerName);
    }

    public void admitAllSpeakersInLobbyInSourceSession() {
        info("Admit all Speakers in Lobby in Source session.");
        sourceSessionComponent.admitAllSpeakersInLobby();
    }

    public void rejectAllSpeakersInLobbyInSourceSession() {
        info("Reject all Speakers in Lobby in Source session.");
        sourceSessionComponent.rejectAllSpeakersInLobby();
    }

    public boolean isUsersOfLobbyTypeInSourceSession(Duration timeout) {
        return sourceSessionComponent.isUsersOfLobbyType(timeout);
    }

    public boolean isUsersOfUsersTypeInSourceSession(Duration timeout) {
        return sourceSessionComponent.isUsersOfUsersType(timeout);
    }

    public boolean areLobbyActionsAvailableInSourceSession(Duration timeout) {
        return sourceSessionComponent.areUsersLobbyActionsAvailable(timeout);
    }

    public boolean isSpeakerPresentInLobbyInSourceSession(String speakerName, Duration timeout) {
        return sourceSessionComponent.isSpeakerPresentInLobby(speakerName, timeout);
    }

    public boolean isSpeakerNotPresentInLobbyInSourceSession(String speakerName, Duration timeout) {
        return sourceSessionComponent.isSpeakerNotPresentInLobby(speakerName, timeout);
    }

    public boolean isLobbySpeakersCountEqualInSourceSession(int count, Duration timeout) {
        return sourceSessionComponent.isLobbySpeakersCountEqual(count, timeout);
    }

    public boolean isLobbySpeakersCountNotVisibleInSourceSession(Duration timeout) {
        return sourceSessionComponent.isLobbySpeakersCountNotVisible(timeout);
    }

    public boolean areSpeakerLobbyActionsAvailableInSourceSession(String speakerName, Duration timeout) {
        return sourceSessionComponent.areSpeakerLobbyActionsAvailable(speakerName, timeout);
    }

    private AdvancedLanguageSessionComponent createAdvancedLanguageSessionComponent(Language language) {
        return new AdvancedLanguageSessionComponent(language, this);
    }
}
