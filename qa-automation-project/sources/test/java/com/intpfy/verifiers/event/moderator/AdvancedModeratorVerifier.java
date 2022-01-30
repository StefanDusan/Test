package com.intpfy.verifiers.event.moderator;

import com.intpfy.gui.dialogs.common.ChatDW;
import com.intpfy.gui.dialogs.common.NotificationDW;
import com.intpfy.gui.dialogs.moderator.RecordingSettingsDW;
import com.intpfy.gui.dialogs.moderator.SetActiveChannelsDW;
import com.intpfy.gui.dialogs.moderator.UsersSessionDW;
import com.intpfy.gui.pages.event.AdvancedModeratorPage;
import com.intpfy.model.Language;
import com.intpfyqa.test.Verify;

import java.util.Locale;

import static com.intpfy.verifiers.event.moderator.AdvancedModeratorVerificationMessages.*;
import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;
import static com.intpfyqa.test.Verify.*;

public class AdvancedModeratorVerifier extends ModeratorVerifier {

    private final AdvancedModeratorPage page;

    public AdvancedModeratorVerifier(AdvancedModeratorPage page) {
        super(page);
        this.page = page;
    }

    public void assertRecordingForSourceSessionEnabled() {
        assertTrue(page.isRecordingForSourceSessionEnabled(AJAX_TIMEOUT), RECORDING_FOR_SOURCE_SESSION_ENABLED);
    }

    public void assertRecordingForSourceSessionDisabled() {
        assertTrue(page.isRecordingForSourceSessionDisabled(AJAX_TIMEOUT), RECORDING_FOR_SOURCE_SESSION_DISABLED);
    }

    public void assertRecordingForSourceSessionEnabled(RecordingSettingsDW recordingSettingsDW) {
        assertTrue(recordingSettingsDW.isRecordingForSourceSessionEnabled(AJAX_TIMEOUT), RECORDING_FOR_SOURCE_SESSION_ENABLED);
    }

    public void assertRecordingForSourceSessionDisabled(RecordingSettingsDW recordingSettingsDW) {
        assertTrue(recordingSettingsDW.isRecordingForSourceSessionDisabled(AJAX_TIMEOUT), RECORDING_FOR_SOURCE_SESSION_DISABLED);
    }

    public void assertRecordingForLanguageSessionEnabled(Language language) {
        String message = String.format(RECORDING_FOR_LANGUAGE_SESSION_ENABLED, language);
        assertTrue(page.isRecordingForLanguageSessionEnabled(language, AJAX_TIMEOUT), message);
    }

    public void assertRecordingForLanguageSessionDisabled(Language language) {
        String message = String.format(RECORDING_FOR_LANGUAGE_SESSION_DISABLED, language);
        assertTrue(page.isRecordingForLanguageSessionDisabled(language, AJAX_TIMEOUT), message);
    }

    public void assertRecordingForLanguageSessionEnabled(RecordingSettingsDW recordingSettingsDW, Language language) {
        String message = String.format(RECORDING_FOR_LANGUAGE_SESSION_ENABLED, language);
        assertTrue(recordingSettingsDW.isRecordingForLanguageSessionEnabled(language, AJAX_TIMEOUT), message);
    }

    public void assertRecordingForLanguageSessionDisabled(RecordingSettingsDW recordingSettingsDW, Language language) {
        String message = String.format(RECORDING_FOR_LANGUAGE_SESSION_DISABLED, language);
        assertTrue(recordingSettingsDW.isRecordingForLanguageSessionDisabled(language, AJAX_TIMEOUT), message);
    }

    public void verifyUserPresentInSourceSession(String username) {
        String message = String.format(USER_PRESENT_IN_SOURCE_SESSION, username);
        verifyTrue(page.isUserPresentInSourceSession(username, AJAX_TIMEOUT), message);
    }

    public void assertUserNotPresentInSourceSession(String username) {
        String message = String.format(USER_NOT_PRESENT_IN_SOURCE_SESSION, username);
        assertTrue(page.isUserNotPresentInSourceSession(username, AJAX_TIMEOUT), message);
    }

    public void verifyUserNotPresentInSourceSession(String username) {
        String message = String.format(USER_NOT_PRESENT_IN_SOURCE_SESSION, username);
        verifyTrue(page.isUserNotPresentInSourceSession(username, AJAX_TIMEOUT), message);
    }

    public void verifyUserPresentInLanguageSession(String username, Language language) {
        String message = String.format(USER_PRESENT_IN_LANGUAGE_SESSION, username, language);
        verifyTrue(page.isUserPresentInLanguageSession(username, language, AJAX_TIMEOUT), message);
    }

    public void verifyUserCanListenInSourceSession(String username) {
        String message = String.format(USER_CAN_LISTEN_IN_SOURCE_SESSION, username);
        verifyTrue(page.userCanListenInSourceSession(username, AJAX_TIMEOUT), message);
    }

    public void verifyUserCanListenInLanguageSession(String username, Language language) {
        String message = String.format(USER_CAN_LISTEN_IN_LANGUAGE_SESSION, username, language);
        verifyTrue(page.userCanListenInLanguageSession(username, language, AJAX_TIMEOUT), message);
    }

    public void verifyUserCanStreamInSourceSession(String username) {
        String message = String.format(USER_CAN_STREAM_IN_SOURCE_SESSION, username);
        verifyTrue(page.userCanStreamInSourceSession(username, AJAX_TIMEOUT), message);
    }

    public void assertUserCanStreamInLanguageSession(String username, Language language) {
        String message = String.format(USER_CAN_STREAM_IN_LANGUAGE_SESSION, username, language);
        assertTrue(page.userCanStreamInLanguageSession(username, language, AJAX_TIMEOUT), message);
    }

    public void verifySourceSessionUserListReloading() {
        verifyTrue(page.isUserListReloadingInSourceSession(AJAX_TIMEOUT), USERS_LIST_RELOADING_IN_SOURCE_SESSION);
    }

    public void verifySourceSessionUserListNotReloading() {
        verifyTrue(page.isUserListNotReloadingInSourceSession(AJAX_TIMEOUT), USERS_LIST_NOT_RELOADING_IN_SOURCE_SESSION);
    }

    public void assertUserMutedInLanguageSession(String username, Language language) {
        String message = String.format(USER_MUTED_IN_LANGUAGE_SESSION, username, language);
        assertTrue(page.isUserMutedInLanguageSession(username, language, AJAX_TIMEOUT), message);
    }

    public void assertUserUnmutedInLanguageSession(String username, Language language) {
        String message = String.format(USER_UNMUTED_IN_LANGUAGE_SESSION, username, language);
        assertTrue(page.isUserUnmutedInLanguageSession(username, language, AJAX_TIMEOUT), message);
    }

    public void assertStreamingIsMultipleInLanguageSession(Language language) {
        String message = String.format(STREAMING_MULTIPLE_IN_LANGUAGE_SESSION, language);
        assertTrue(page.isStreamingMultipleInLanguageSession(language, AJAX_TIMEOUT), message);
    }

    public void assertStreamingIsNotMultipleInLanguageSession(Language language) {
        String message = String.format(STREAMING_NOT_MULTIPLE_IN_LANGUAGE_SESSION, language);
        assertTrue(page.isStreamingNotMultipleInLanguageSession(language, AJAX_TIMEOUT), message);
    }

    public void assertUserOrdinalPositionInLanguageSessionUsersList(String username, Language language, int position) {
        String message = String.format(USER_POSITIONED_IN_LANGUAGE_SESSION, username, position, language);
        assertEquals(page.getUserOrdinalPositionInLanguageSession(username, language), position, message);
    }

    public void assertUserPresentInUsersSessionDW(UsersSessionDW usersSessionDW, String username) {
        String message = String.format(USER_PRESENT_IN_USERS_SESSION_DIALOG_WINDOW, username);
        assertTrue(usersSessionDW.isUserPresent(username, AJAX_TIMEOUT), message);
    }

    public void assertUserNotPresentInUsersSessionDW(UsersSessionDW usersSessionDW, String username) {
        String message = String.format(USER_NOT_PRESENT_IN_USERS_SESSION_DIALOG_WINDOW, username);
        assertTrue(usersSessionDW.isUserNotPresent(username, AJAX_TIMEOUT), message);
    }

    public void verifyUsername(SetActiveChannelsDW setActiveChannelsDW, String username) {
        String message = String.format(USERNAME_DISPLAYED_IN_SET_ACTIVE_CHANNELS_DIALOG_WINDOW, username);
        verifyEquals(setActiveChannelsDW.getUsername(), username, message);
    }

    public void verifyNotificationText(NotificationDW notificationDW, String text) {
        String message = String.format(TEXT_DISPLAYED_IN_NOTIFICATION_DIALOG_WINDOW, text);
        verifyEquals(notificationDW.getText(), text, message);
    }

    public void verifyUserIncomingLanguageInSourceSession(String username, Language language) {
        String message = String.format(USER_HAS_INCOMING_LANGUAGE_IN_SOURCE_SESSION, username, language);
        verifyTrue(page.userHasIncomingLanguageInSourceSession(username, language, AJAX_TIMEOUT), message);
    }

    public void verifyUserOutgoingLanguageInSourceSession(String username, Language language) {
        String message = String.format(USER_HAS_OUTGOING_LANGUAGE_IN_SOURCE_SESSION, username, language);
        verifyTrue(page.userHasOutgoingLanguageInSourceSession(username, language, AJAX_TIMEOUT), message);
    }

    public void assertPartnerChatShown(Language language) {
        String message = String.format(PARTNER_CHAT_SHOWN_FOR_LANGUAGE, language);
        assertTrue(page.isPartnerChatShown(language, AJAX_TIMEOUT), message);
    }

    public void assertPartnerChatHidden(Language language) {
        String message = String.format(PARTNER_CHAT_HIDDEN_FOR_LANGUAGE, language);
        assertTrue(page.isPartnerChatHidden(language, AJAX_TIMEOUT), message);
    }

    public void assertPartnerChatSessionTitle(ChatDW partnerChatDW, String expectedTitle) {

        String actualTitle = partnerChatDW.getCurrentSessionTitle().toLowerCase(Locale.ROOT);
        expectedTitle = expectedTitle.toLowerCase(Locale.ROOT);

        String message = String.format(CHAT_SESSION_TITLE, actualTitle);

        Verify.assertEquals(actualTitle, expectedTitle, message);
    }

    public void assertUsersIsOfLobbyTypeInSourceSession() {
        assertTrue(page.isUsersOfLobbyTypeInSourceSession(AJAX_TIMEOUT), USERS_IS_OF_LOBBY_TYPE_IN_SOURCE_SESSION);
    }

    public void assertUsersIsOfUsersTypeInSourceSession() {
        assertTrue(page.isUsersOfUsersTypeInSourceSession(AJAX_TIMEOUT), USERS_IS_OF_USERS_TYPE_IN_SOURCE_SESSION);
    }

    public void assertLobbyActionsAvailableInSourceSession() {
        assertTrue(page.areLobbyActionsAvailableInSourceSession(AJAX_TIMEOUT), LOBBY_ACTIONS_AVAILABLE_IN_SOURCE_SESSION);
    }

    public void assertSpeakerPresentInLobbyInSourceSession(String speakerName) {
        String message = String.format(SPEAKER_PRESENT_IN_LOBBY_IN_SOURCE_SESSION, speakerName);
        assertTrue(page.isSpeakerPresentInLobbyInSourceSession(speakerName, AJAX_TIMEOUT), message);
    }

    public void assertSpeakerNotPresentInLobbyInSourceSession(String speakerName) {
        String message = String.format(SPEAKER_NOT_PRESENT_IN_LOBBY_IN_SOURCE_SESSION, speakerName);
        assertTrue(page.isSpeakerNotPresentInLobbyInSourceSession(speakerName, AJAX_TIMEOUT), message);
    }

    public void assertSpeakerLobbyActionsAvailableInSourceSession(String speakerName) {
        String message = String.format(SPEAKER_LOBBY_ACTIONS_AVAILABLE_IN_SOURCE_SESSION, speakerName);
        assertTrue(page.areSpeakerLobbyActionsAvailableInSourceSession(speakerName, AJAX_TIMEOUT), message);
    }

    public void assertLobbySpeakersCountEqualsInSourceSession(int count) {
        String message = String.format(LOBBY_SPEAKERS_COUNT_IN_SOURCE_SESSION, count);
        assertTrue(page.isLobbySpeakersCountEqualInSourceSession(count, AJAX_TIMEOUT), message);
    }

    public void assertLobbySpeakersCountNotVisibleInSourceSession() {
        assertTrue(page.isLobbySpeakersCountNotVisibleInSourceSession(AJAX_TIMEOUT), LOBBY_SPEAKERS_COUNT_NOT_VISIBLE_IN_SOURCE_SESSION);
    }
}
