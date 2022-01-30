package com.intpfy.verifiers.event.speaker;

import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.Language;
import com.intpfy.verifiers.event.BaseEventVerifier;
import com.intpfyqa.gui.web.selenium.VerifyPage;
import com.intpfyqa.test.Verify;

import java.time.Duration;

import static com.intpfy.verifiers.event.common.CommonVerificationMessages.*;
import static com.intpfy.verifiers.event.speaker.SpeakerVerificationMessages.*;
import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

public class SpeakerVerifier extends BaseEventVerifier {

    private final SpeakerPage page;

    public SpeakerVerifier(SpeakerPage page) {
        super(page);
        this.page = page;
    }

    public void assertInterpretingLanguageSelected(Language language) {
        String message = String.format(INTERPRETING_LANGUAGE_SELECTED, language);
        Verify.assertTrue(page.isInterpretingLanguageSelected(language, AJAX_TIMEOUT), message);
    }

    public void verifyInterpretingLanguageSelected(Language language) {
        String message = String.format(INTERPRETING_LANGUAGE_SELECTED, language);
        Verify.verifyTrue(page.isInterpretingLanguageSelected(language, AJAX_TIMEOUT), message);
    }

    public void assertNoInterpretingLanguageSelected() {
        Verify.assertTrue(page.isNoInterpretingLanguageSelected(AJAX_TIMEOUT), NO_INTERPRETING_LANGUAGE_SELECTED);
    }

    // ...GoingOnUI methods check only UI part of stream.
    // Media streams checks are implemented in the WebRtcVerifier.

    public void assertStreamWithAudioOnlyGoingOnUI() {
        getStreamWithAudioOnlyGoingOnUIVerification().completeAssert();
    }

    public void verifyStreamWithAudioOnlyGoingOnUI() {
        getStreamWithAudioOnlyGoingOnUIVerification().completeVerify();
    }

    private VerifyPage<SpeakerPage> getStreamWithAudioOnlyGoingOnUIVerification() {

        return VerifyPage.onPage(page, STREAM_WITH_AUDIO_ONLY_GOING_ON_UI)
                .booleanValueShouldBeEqual(s -> page.isMicOn(AJAX_TIMEOUT), true, MIC_ON)
                .booleanValueShouldBeEqual(s -> page.isVolumeLevelChanging(AJAX_TIMEOUT), true, VOLUME_LEVEL_CHANGING)
                .booleanValueShouldBeEqual(s -> page.isCameraOff(AJAX_TIMEOUT), true, CAMERA_OFF)
                .booleanValueShouldBeEqual(p -> page.isPublisherVideoContainerVisible(AJAX_TIMEOUT), true, PUBLISHER_VIDEO_CONTAINER_VISIBLE)
                .booleanValueShouldBeEqual(p -> page.isPublisherVideoContainerAudioOnly(AJAX_TIMEOUT), true, PUBLISHER_VIDEO_CONTAINER_IS_AUDIO_ONLY);
    }

    public void assertStreamWithAudioAndVideoGoingOnUI() {
        getStreamWithAudioAndVideoGoingOnUIVerification().completeAssert();
    }

    public void verifyStreamWithAudioAndVideoGoingOnUI() {
        getStreamWithAudioAndVideoGoingOnUIVerification().completeVerify();
    }

    private VerifyPage<SpeakerPage> getStreamWithAudioAndVideoGoingOnUIVerification() {

        return VerifyPage.onPage(page, STREAM_WITH_AUDIO_AND_VIDEO_GOING_ON_UI)
                .booleanValueShouldBeEqual(p -> page.isMicOn(AJAX_TIMEOUT), true, MIC_ON)
                .booleanValueShouldBeEqual(p -> page.isVolumeLevelChanging(AJAX_TIMEOUT), true, VOLUME_LEVEL_CHANGING)
                .booleanValueShouldBeEqual(p -> page.isCameraOn(AJAX_TIMEOUT), true, CAMERA_ON)
                .booleanValueShouldBeEqual(p -> page.isPublisherVideoContainerVisible(AJAX_TIMEOUT), true, PUBLISHER_VIDEO_CONTAINER_VISIBLE)
                .booleanValueShouldBeEqual(p -> page.isPublisherVideoContainerNotAudioOnly(AJAX_TIMEOUT), true, PUBLISHER_VIDEO_CONTAINER_IS_NOT_AUDIO_ONLY);
    }

    public void assertStreamWithScreenSharingGoingOnUI() {
        getStreamWithScreenSharingGoingOnUIVerification().completeAssert();
    }

    public void verifyStreamWithScreenSharingGoingOnUI() {
        getStreamWithScreenSharingGoingOnUIVerification().completeVerify();
    }

    private VerifyPage<SpeakerPage> getStreamWithScreenSharingGoingOnUIVerification() {

        return VerifyPage.onPage(page, STREAM_WITH_SCREEN_SHARING_GOING_ON_UI)
                .booleanValueShouldBeEqual(p -> page.isScreenSharingEnabled(AJAX_TIMEOUT), true, SCREEN_SHARING_ENABLED)
                .booleanValueShouldBeEqual(p -> page.isPublisherScreenContainerVisible(AJAX_TIMEOUT), true, PUBLISHER_SCREEN_CONTAINER_VISIBLE);
    }

    public void assertStreamWithScreenSharingNotGoingOnUI() {
        getStreamWithScreenSharingNotGoingOnUIVerification().completeAssert();
    }

    private VerifyPage<SpeakerPage> getStreamWithScreenSharingNotGoingOnUIVerification() {

        return VerifyPage.onPage(page, STREAM_WITH_SCREEN_SHARING_NOT_GOING_ON_UI)
                .booleanValueShouldBeEqual(p -> page.isScreenSharingDisabled(AJAX_TIMEOUT), true, SCREEN_SHARING_DISABLED)
                .booleanValueShouldBeEqual(p -> page.isPublisherScreenContainerNotVisible(AJAX_TIMEOUT), true, PUBLISHER_SCREEN_CONTAINER_NOT_VISIBLE);
    }


    // Verification for publisher screen container is not needed
    // because publisher screen container depends on publisher video container:
    // screen container cannot be visible if video container is not visible.
    public void assertNoStreamGoingOnUI() {
        Verify.assertTrue(page.isPublisherVideoContainerNotVisible(AJAX_TIMEOUT), PUBLISHER_VIDEO_CONTAINER_NOT_VISIBLE);
    }

    public void verifyNoStreamGoingOnUI() {
        Verify.verifyTrue(page.isPublisherVideoContainerNotVisible(AJAX_TIMEOUT), PUBLISHER_VIDEO_CONTAINER_NOT_VISIBLE);
    }

    public void assertSpeakerAskedToStreamInParticipants(String speakerName) {
        String message = String.format(SPEAKER_ASKED_TO_STREAM_IN_PARTICIPANTS, speakerName);
        Verify.assertTrue(page.isSpeakerAskedToStreamInParticipants(speakerName, AJAX_TIMEOUT), message);
    }

    public void verifySpeakerAskedToStreamInParticipants(String speakerName) {
        String message = String.format(SPEAKER_ASKED_TO_STREAM_IN_PARTICIPANTS, speakerName);
        Verify.verifyTrue(page.isSpeakerAskedToStreamInParticipants(speakerName, AJAX_TIMEOUT), message);
    }

    public void assertSpeakerStreamingInParticipants(String speakerName) {
        String message = String.format(SPEAKER_STREAMING_IN_PARTICIPANTS, speakerName);
        Verify.assertTrue(page.isSpeakerStreamingInParticipants(speakerName, AJAX_TIMEOUT), message);
    }

    public void verifySpeakerStreamingInParticipants(String speakerName) {
        String message = String.format(SPEAKER_STREAMING_IN_PARTICIPANTS, speakerName);
        Verify.verifyTrue(page.isSpeakerStreamingInParticipants(speakerName, AJAX_TIMEOUT), message);
    }

    public void assertSpeakerNotStreamingInParticipants(String speakerName) {
        String message = String.format(SPEAKER_NOT_STREAMING_IN_PARTICIPANTS, speakerName);
        Verify.assertTrue(page.isSpeakerNotStreamingInParticipants(speakerName, AJAX_TIMEOUT), message);
    }

    public void verifySpeakerNotStreamingInParticipants(String speakerName) {
        String message = String.format(SPEAKER_NOT_STREAMING_IN_PARTICIPANTS, speakerName);
        Verify.verifyTrue(page.isSpeakerNotStreamingInParticipants(speakerName, AJAX_TIMEOUT), message);
    }

    public void assertAutoVolumeAvailable() {
        Verify.assertTrue(page.isAutoVolumeAvailable(AJAX_TIMEOUT), AUTO_VOLUME_AVAILABLE);
    }

    public void assertAutoVolumeUnavailable() {
        Verify.assertTrue(page.isAutoVolumeUnavailable(AJAX_TIMEOUT), AUTO_VOLUME_UNAVAILABLE);
    }

    public void assertAutoVolumeEnabled() {
        Verify.assertTrue(page.isAutoVolumeEnabled(AJAX_TIMEOUT), AUTO_VOLUME_ENABLED);
    }

    public void assertAutoVolumeDisabled() {
        Verify.assertTrue(page.isAutoVolumeDisabled(AJAX_TIMEOUT), AUTO_VOLUME_DISABLED);
    }

    public void assertAudioPresentInSourceAndLanguageChannel() {
        assertAudioPresentInSourceChannel();
        assertAudioPresentInLanguageChannel();
    }

    public void assertAudioNotPresentInSourceAndLanguageChannel() {
        assertAudioNotPresentInSourceChannel();
        assertAudioNotPresentInLanguageChannel();
    }

    public void assertAudioPresentInSourceChannel() {
        getAudioPresentInSourceChannelVerification().completeAssert();
    }

    public void verifyAudioPresentInSourceChannel() {
        getAudioPresentInSourceChannelVerification().completeVerify();
    }

    private VerifyPage<SpeakerPage> getAudioPresentInSourceChannelVerification() {

        return VerifyPage.onPage(page, AUDIO_PRESENT_IN_SOURCE_CHANNEL)
                .booleanValueShouldBeEqual(s -> page.isSourceChannelUnmuted(AJAX_TIMEOUT), true, UNMUTED)
                .booleanValueShouldBeEqual(s -> page.isSourceChannelVolumeLevelChanging(AJAX_TIMEOUT), true, VOLUME_LEVEL_CHANGING);
    }

    public void assertAudioNotPresentInSourceChannel() {
        getAudioNotPresentInSourceChannelVerification().completeAssert();
    }

    public void verifyAudioNotPresentInSourceChannel() {
        getAudioNotPresentInSourceChannelVerification().completeVerify();
    }

    private VerifyPage<SpeakerPage> getAudioNotPresentInSourceChannelVerification() {

        return VerifyPage.onPage(page, AUDIO_NOT_PRESENT_IN_SOURCE_CHANNEL)
                .booleanValueShouldBeEqual(s -> page.isSourceChannelMuted(AJAX_TIMEOUT), true, MUTED)
                .booleanValueShouldBeEqual(s -> page.isSourceChannelVolumeLevelNotChanging(AJAX_TIMEOUT), true, VOLUME_LEVEL_NOT_CHANGING);
    }

    public void assertAudioPresentInLanguageChannel() {
        getAudioPresentInLanguageChannelVerification().completeAssert();
    }

    public void verifyAudioPresentInLanguageChannel() {
        getAudioPresentInLanguageChannelVerification().completeVerify();
    }

    private VerifyPage<SpeakerPage> getAudioPresentInLanguageChannelVerification() {

        return VerifyPage.onPage(page, AUDIO_PRESENT_IN_LANGUAGE_CHANNEL)
                .booleanValueShouldBeEqual(s -> page.isLanguageChannelUnmuted(AJAX_TIMEOUT), true, UNMUTED)
                .booleanValueShouldBeEqual(s -> page.isLanguageChannelVolumeLevelChanging(AJAX_TIMEOUT), true, VOLUME_LEVEL_CHANGING);
    }

    public void assertAudioNotPresentInLanguageChannel() {
        getAudioNotPresentInLanguageChannelVerification().completeAssert();
    }

    public void verifyAudioNotPresentInLanguageChannel() {
        getAudioNotPresentInLanguageChannelVerification().completeVerify();
    }

    private VerifyPage<SpeakerPage> getAudioNotPresentInLanguageChannelVerification() {

        return VerifyPage.onPage(page, AUDIO_NOT_PRESENT_IN_LANGUAGE_CHANNEL)
                .booleanValueShouldBeEqual(s -> page.isLanguageChannelMuted(AJAX_TIMEOUT), true, MUTED)
                .booleanValueShouldBeEqual(s -> page.isLanguageChannelVolumeLevelNotChanging(AJAX_TIMEOUT), true, VOLUME_LEVEL_NOT_CHANGING);
    }

    public void assertSourceChannelUnmuted() {
        Verify.assertTrue(page.isSourceChannelUnmuted(AJAX_TIMEOUT), SOURCE_CHANNEL_UNMUTED);
    }

    public void assertLanguageChannelMuted() {
        Verify.assertTrue(page.isLanguageChannelMuted(AJAX_TIMEOUT), LANGUAGE_CHANNEL_MUTED);
    }

    public void assertLanguageChannelUnmuted() {
        Verify.assertTrue(page.isLanguageChannelUnmuted(AJAX_TIMEOUT), LANGUAGE_CHANNEL_UNMUTED);
    }

    public void verifyLanguageChannelUnmuted() {
        Verify.verifyTrue(page.isLanguageChannelUnmuted(AJAX_TIMEOUT), LANGUAGE_CHANNEL_UNMUTED);
    }

    public void verifyLanguageChannelVolumeLevelNotChanging() {
        Verify.verifyTrue(page.isLanguageChannelVolumeLevelNotChanging(AJAX_TIMEOUT), LANGUAGE_CHANNEL_VOLUME_LEVEL_NOT_CHANGING);
    }

    public void assertVolumeLevelIsMaxForSourceChannel() {
        Verify.assertTrue(page.isVolumeLevelMaxForSourceChannel(AJAX_TIMEOUT), VOLUME_LEVEL_MAX_FOR_SOURCE_CHANNEL);
    }

    public void verifyVolumeLevelIsMaxForSourceChannel() {
        Verify.verifyTrue(page.isVolumeLevelMaxForSourceChannel(AJAX_TIMEOUT), VOLUME_LEVEL_MAX_FOR_SOURCE_CHANNEL);
    }

    public void assertVolumeLevelIsMaxForLanguageChannel() {
        Verify.assertTrue(page.isVolumeLevelMaxForLanguageChannel(AJAX_TIMEOUT), VOLUME_LEVEL_MAX_FOR_LANGUAGE_CHANNEL);
    }

    public void verifyVolumeLevelIsMaxForLanguageChannel() {
        Verify.verifyTrue(page.isVolumeLevelMaxForLanguageChannel(AJAX_TIMEOUT), VOLUME_LEVEL_MAX_FOR_LANGUAGE_CHANNEL);
    }

    public void assertVolumeLevelIsMinForSourceChannel() {
        Verify.assertTrue(page.isVolumeLevelMinForSourceChannel(AJAX_TIMEOUT), VOLUME_LEVEL_MIN_FOR_SOURCE_CHANNEL);
    }

    public void assertVolumeLevelIsMinForLanguageChannel() {
        Verify.assertTrue(page.isVolumeLevelMinForLanguageChannel(AJAX_TIMEOUT), VOLUME_LEVEL_MIN_FOR_LANGUAGE_CHANNEL);
    }

    public void assertMicOn() {
        Verify.assertTrue(page.isMicOn(AJAX_TIMEOUT), MIC_ON);
    }

    public void verifyMicOn() {
        Verify.verifyTrue(page.isMicOn(AJAX_TIMEOUT), MIC_ON);
    }

    public void assertMicOff() {
        Verify.assertTrue(page.isMicOff(AJAX_TIMEOUT), MIC_OFF);
    }

    public void verifyMicOff() {
        verifyMicOff(AJAX_TIMEOUT);
    }

    public void verifyMicOff(Duration timeout) {
        Verify.verifyTrue(page.isMicOff(timeout), MIC_OFF);
    }

    public void assertCameraOn() {
        Verify.assertTrue(page.isCameraOn(AJAX_TIMEOUT), CAMERA_ON);
    }

    public void verifyCameraOn() {
        Verify.verifyTrue(page.isCameraOn(AJAX_TIMEOUT), CAMERA_ON);
    }

    public void assertCameraOff() {
        Verify.assertTrue(page.isCameraOff(AJAX_TIMEOUT), CAMERA_OFF);
    }

    public void verifyCameraOff() {
        Verify.verifyTrue(page.isCameraOff(AJAX_TIMEOUT), CAMERA_OFF);
    }

    public void assertHandRaised() {
        Verify.assertTrue(page.isHandRaised(AJAX_TIMEOUT), HAND_RAISED);
    }

    public void verifyHandRaised() {
        Verify.verifyTrue(page.isHandRaised(AJAX_TIMEOUT), HAND_RAISED);
    }

    public void verifyHandDown() {
        Verify.verifyTrue(page.isHandDown(AJAX_TIMEOUT), HAND_DOWN);
    }

    public void assertSpeakerHandRaisedInParticipants(String speakerName) {
        String message = String.format(SPEAKER_HAND_RAISED_IN_PARTICIPANTS, speakerName);
        Verify.assertTrue(page.isSpeakerHandRaisedInParticipants(speakerName, AJAX_TIMEOUT), message);
    }

    public void verifySpeakerHandRaisedInParticipants(String speakerName) {
        String message = String.format(SPEAKER_HAND_RAISED_IN_PARTICIPANTS, speakerName);
        Verify.verifyTrue(page.isSpeakerHandRaisedInParticipants(speakerName, AJAX_TIMEOUT), message);
    }

    public void assertHandDownInParticipants(String speakerName) {
        String message = String.format(SPEAKER_HAND_DOWN_IN_PARTICIPANTS, speakerName);
        Verify.assertTrue(page.isSpeakerHandDownInParticipants(speakerName, AJAX_TIMEOUT), message);
    }

    public void verifyHandDownInParticipants(String speakerName) {
        String message = String.format(SPEAKER_HAND_DOWN_IN_PARTICIPANTS, speakerName);
        Verify.verifyTrue(page.isSpeakerHandDownInParticipants(speakerName, AJAX_TIMEOUT), message);
    }

    public void verifyVolumeLevelChanging() {
        Verify.verifyTrue(page.isVolumeLevelChanging(AJAX_TIMEOUT), VOLUME_LEVEL_CHANGING);
    }

    public void assertVolumeLevelNotChanging() {
        Verify.assertTrue(page.isVolumeLevelNotChanging(AJAX_TIMEOUT), VOLUME_LEVEL_NOT_CHANGING);
    }

    public void verifyVolumeLevelNotChanging() {
        verifyVolumeLevelNotChanging(AJAX_TIMEOUT);
    }

    public void verifyVolumeLevelNotChanging(Duration timeout) {
        Verify.verifyTrue(page.isVolumeLevelNotChanging(timeout), VOLUME_LEVEL_NOT_CHANGING);
    }

    public void verifyPublisherVideoContainerIsAudioOnly() {
        Verify.verifyTrue(page.isPublisherVideoContainerAudioOnly(AJAX_TIMEOUT), PUBLISHER_VIDEO_CONTAINER_IS_AUDIO_ONLY);
    }

    public void verifyPublisherVideoContainerIsNotAudioOnly() {
        Verify.verifyTrue(page.isPublisherVideoContainerNotAudioOnly(AJAX_TIMEOUT), PUBLISHER_VIDEO_CONTAINER_IS_NOT_AUDIO_ONLY);
    }

    public void assertConnected() {
        Verify.assertTrue(page.isConnected(AJAX_TIMEOUT), CONNECTED);
    }

    public void assertDisconnected() {
        Verify.assertTrue(page.isDisconnected(AJAX_TIMEOUT), DISCONNECTED);
    }

    public void assertVideoOnlyFullscreenModeEnabled() {
        Verify.assertTrue(page.isVideoOnlyFullscreenModeEnabled(AJAX_TIMEOUT), VIDEO_ONLY_FULLSCREEN_MODE_ENABLED);
    }

    public void assertVideoOnlyFullscreenModeDisabled() {
        Verify.assertTrue(page.isVideoOnlyFullscreenModeDisabled(AJAX_TIMEOUT), VIDEO_ONLY_FULLSCREEN_MODE_DISABLED);
    }

    public void assertSlowDownDWDisplayed() {
        Verify.assertTrue(page.isSlowDownDWDisplayed(AJAX_TIMEOUT), SLOW_DOWN_DIALOG_WINDOW_DISPLAYED);
    }

    public void assertSlowDownDWNotDisplayed() {
        Duration timeout = Duration.ofSeconds(8);
        Verify.assertTrue(page.isSlowDownDWNotDisplayed(timeout), SLOW_DOWN_DIALOG_WINDOW_NOT_DISPLAYED);
    }

    public void verifySlowDownDW(String message, Language language, String initials) {

        String messageVerificationMessage = String.format(SLOW_DOWN_DIALOG_WINDOW_MESSAGE, message);
        String languageVerificationMessage = String.format(SLOW_DOWN_DIALOG_WINDOW_LANGUAGE, language);
        String initialsVerificationMessage = String.format(SLOW_DOWN_DIALOG_WINDOW_INITIALS, initials);

        VerifyPage.onPage(page, SLOW_DOWN_DIALOG_WINDOW_CONTENT)
                .stringValueShouldBeEqual(m -> page.getSlowDownMessage(), message, messageVerificationMessage)
                .shouldBeEquals(m -> page.getSlowDownLanguage(), language, languageVerificationMessage)
                .stringValueShouldBeEqual(m -> page.getSlowDownInitials(), initials, initialsVerificationMessage)
                .completeVerify();
    }

    public void assertMuted() {
        Verify.assertTrue(page.isMuted(AJAX_TIMEOUT), MUTED);
    }

    public void assertStreamControlsDisabled() {
        Verify.assertTrue(page.areStreamControlsDisabled(AJAX_TIMEOUT), STREAM_CONTROLS_DISABLED);
    }

    public void verifySpeakerPresentInParticipants(String speakerName) {
        String message = String.format(SPEAKER_PRESENT_IN_PARTICIPANTS, speakerName);
        Verify.verifyTrue(page.isSpeakerPresentInParticipants(speakerName, AJAX_TIMEOUT), message);
    }

    public void verifySpeakerHasHostRoleInParticipants(String speakerName) {
        String message = String.format(SPEAKER_HAS_HOST_ROLE_IN_PARTICIPANTS, speakerName);
        Verify.verifyTrue(page.speakerHasHostRoleInParticipants(speakerName, AJAX_TIMEOUT), message);
    }

    public void verifySpeakerNotPresentInParticipants(String speakerName) {
        String message = String.format(SPEAKER_NOT_PRESENT_IN_PARTICIPANTS, speakerName);
        Verify.verifyTrue(page.isSpeakerNotPresentInParticipants(speakerName, AJAX_TIMEOUT), message);
    }

    public void assertSpeakerNotPresentInParticipants(String speakerName) {
        String message = String.format(SPEAKER_NOT_PRESENT_IN_PARTICIPANTS, speakerName);
        Verify.assertTrue(page.isSpeakerNotPresentInParticipants(speakerName, AJAX_TIMEOUT), message);
    }

    public void assertUnreadPrivateMessagesPresent() {
        Verify.assertTrue(page.unreadPrivateMessagesPresent(AJAX_TIMEOUT), UNREAD_PRIVATE_MESSAGES_PRESENT);
    }

    public void assertUnreadPrivateMessagesNotPresent() {
        Verify.assertTrue(page.unreadPrivateMessagesNotPresent(AJAX_TIMEOUT), UNREAD_PRIVATE_MESSAGES_NOT_PRESENT);
    }

    public void assertSpeakersVisible() {
        Verify.assertTrue(page.isSpeakersVisible(Duration.ZERO), SPEAKERS_VISIBLE);
    }

    public void verifySpeakersNotVisible() {
        Verify.verifyTrue(page.isSpeakersNotVisible(Duration.ZERO), SPEAKERS_NOT_VISIBLE);
    }

    public void assertEventChatMessageHidden(String message) {
        Verify.assertTrue(page.isEventChatMessageHidden(message, AJAX_TIMEOUT), String.format(EVENT_CHAT_MESSAGE_HIDDEN, message));
    }

    public void assertVoteTabOpened() {
        Verify.assertTrue(page.isVoteTabOpened(AJAX_TIMEOUT), VOTE_TAB_OPENED);
    }

    public void verifyVoteInProgress() {
        Verify.verifyTrue(page.isVoteInProgress(AJAX_TIMEOUT), VOTE_IN_PROGRESS);
    }

    public void verifyVoteQuestion(String question) {
        String message = String.format(VOTE_QUESTION, question);
        Verify.verifyEquals(question, page.getVoteQuestion(), message);
    }

    public void verifyVoteAnswerAsHost(int answerIndex, String answer) {
        String message = String.format(VOTE_ANSWER, answerIndex, answer);
        Verify.verifyEquals(answer, page.getVoteAnswerAsHost(answerIndex), message);
    }

    public void verifyMultipleVoteAnswerAsSpeaker(int answerIndex, String answer) {
        String message = String.format(VOTE_ANSWER, answerIndex, answer);
        Verify.verifyEquals(answer, page.getMultipleVoteAnswerAsSpeaker(answerIndex), message);
    }

    public void verifySingleVoteAnswerAsSpeaker(int answerIndex, String answer) {
        String message = String.format(VOTE_ANSWER, answerIndex, answer);
        Verify.verifyEquals(answer, page.getSingleVoteAnswerAsSpeaker(answerIndex), message);
    }

    public void assertMultipleVoteAnswerSelected(String answer) {
        String message = String.format(MULTIPLE_VOTE_ANSWER_SELECTED, answer);
        Verify.assertTrue(page.isMultipleVoteAnswerSelected(answer, AJAX_TIMEOUT), message);
    }

    public void assertSingleVoteAnswerSelected(String answer) {
        String message = String.format(SINGLE_VOTE_ANSWER_SELECTED, answer);
        Verify.assertTrue(page.isSingleVoteAnswerSelected(answer, AJAX_TIMEOUT), message);
    }

    public void assertVoteAccepted() {
        Verify.assertTrue(page.isVoteAccepted(AJAX_TIMEOUT), VOTE_ACCEPTED);
    }

    public void assertSpeakersIsOfLobbyType() {
        Verify.assertTrue(page.isSpeakersOfLobbyType(AJAX_TIMEOUT), SPEAKERS_IS_OF_LOBBY_TYPE);
    }

    public void assertSpeakersOfParticipantsType() {
        Verify.assertTrue(page.isSpeakersOfParticipantsType(AJAX_TIMEOUT), SPEAKERS_IS_OF_PARTICIPANTS_TYPE);
    }

    public void assertLobbyActionsAvailable() {
        Verify.assertTrue(page.areLobbyActionsAvailable(AJAX_TIMEOUT), LOBBY_ACTIONS_AVAILABLE);
    }

    public void assertSpeakerLobbyActionsAvailable(String speakerName) {
        Verify.assertTrue(page.areSpeakerLobbyActionsAvailable(speakerName, AJAX_TIMEOUT), String.format(SPEAKER_LOBBY_ACTIONS_AVAILABLE, speakerName));
    }

    public void assertLobbySpeakersCountEquals(int count) {
        Verify.assertTrue(page.isLobbySpeakersCountEqual(count, AJAX_TIMEOUT), String.format(LOBBY_SPEAKERS_COUNT, count));
    }

    public void assertLobbySpeakersCountEqualsZero() {
        assertLobbySpeakersCountEquals(0);
    }

    public void assertSpeakerPresentInLobby(String speakerName) {
        Verify.assertTrue(page.isSpeakerPresentInLobby(speakerName, AJAX_TIMEOUT), String.format(SPEAKER_PRESENT_IN_LOBBY, speakerName));
    }

    public void assertSpeakerNotPresentInLobby(String speakerName) {
        Verify.assertTrue(page.isSpeakerNotPresentInLobby(speakerName, AJAX_TIMEOUT), String.format(SPEAKER_NOT_PRESENT_IN_LOBBY, speakerName));
    }
}
