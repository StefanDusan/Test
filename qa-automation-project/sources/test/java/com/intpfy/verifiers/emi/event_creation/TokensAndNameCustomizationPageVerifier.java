package com.intpfy.verifiers.emi.event_creation;

import com.intpfy.gui.pages.emi.event_creation.TokensAndNameCustomizationPage;
import com.intpfy.verifiers.emi.BaseEmiVerifier;
import com.intpfyqa.test.Verify;

import static com.intpfy.verifiers.emi.event_creation.EditEventPageVerificationMessages.PRE_CALL_TEST_ENABLED_FOR_AUDIENCE;
import static com.intpfy.verifiers.emi.event_creation.EditEventPageVerificationMessages.PRE_CALL_TEST_ENABLED_FOR_INTERPRETER_AND_SPEAKER;
import static com.intpfy.verifiers.emi.event_creation.TokensAndNameCustomizationPageVerificationMessages.*;
import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;
import static com.intpfyqa.test.Verify.assertTrue;

public class TokensAndNameCustomizationPageVerifier extends BaseEmiVerifier {

    private final TokensAndNameCustomizationPage page;

    public TokensAndNameCustomizationPageVerifier(TokensAndNameCustomizationPage page) {
        super(page);
        this.page = page;
    }

    public void assertAudienceTokenEquals(String token) {
        assertTrue(page.isAudienceTokenEqual(token, AJAX_TIMEOUT), String.format(AUDIENCE_TOKEN_EQUALS, token));
    }

    public void assertInterpreterTokenEquals(String token) {
        assertTrue(page.isInterpreterTokenEqual(token, AJAX_TIMEOUT), String.format(INTERPRETER_TOKEN_EQUALS, token));
    }

    public void assertSpeakerTokenEquals(String token) {
        assertTrue(page.isSpeakerTokenEqual(token, AJAX_TIMEOUT), String.format(SPEAKER_TOKEN_EQUALS, token));
    }

    public void assertModeratorTokenEquals(String token) {
        assertTrue(page.isModeratorTokenEqual(token, AJAX_TIMEOUT), String.format(MODERATOR_TOKEN_EQUALS, token));
    }

    public void assertAudienceAccessToSourceAudioAllowed() {
        assertTrue(page.isAudienceAccessToSourceAudioAllowed(AJAX_TIMEOUT), AUDIENCE_ACCESS_TO_SOURCE_AUDIO_ALLOWED);
    }

    public void assertAudienceAccessToSourceVideoOnWebAllowed() {
        assertTrue(page.isAudienceAccessToSourceVideoOnWebAllowed(AJAX_TIMEOUT), AUDIENCE_ACCESS_TO_SOURCE_VIDEO_ON_WEB_ALLOWED);
    }

    public void assertPreCallTestEnabledForAudience() {
        assertTrue(page.isPreCallTestEnabledForAudience(AJAX_TIMEOUT), PRE_CALL_TEST_ENABLED_FOR_AUDIENCE);
    }

    public void assertPreCallTestEnabledForInterpreterAndSpeaker() {
        assertTrue(page.isPreCallTestEnabledForInterpreterAndSpeaker(AJAX_TIMEOUT), PRE_CALL_TEST_ENABLED_FOR_INTERPRETER_AND_SPEAKER);
    }

    public void assertEventChatEnabledForInterpreter() {
        assertTrue(page.isEventChatEnabledForInterpreter(AJAX_TIMEOUT), EVENT_CHAT_ENABLED_FOR_INTERPRETER);
    }

    public void assertEventChatDisabledForInterpreter() {
        assertTrue(page.isEventChatDisabledForInterpreter(AJAX_TIMEOUT), EVENT_CHAT_DISABLED_FOR_INTERPRETER);
    }

    public void assertEventChatDisabledForSpeaker() {
        assertTrue(page.isEventChatDisabledForSpeaker(AJAX_TIMEOUT), EVENT_CHAT_DISABLED_FOR_SPEAKER);
    }

    public void assertCaptionsAccessEnabled() {
        assertTrue(page.isCaptionsAccessEnabled(AJAX_TIMEOUT), CAPTIONS_ACCESS_ENABLED);
    }

    public void assertDocumentSharingEnabled() {
        assertTrue(page.isDocumentSharingEnabled(AJAX_TIMEOUT), DOCUMENT_SHARING_ENABLED);
    }

    public void assertLobbyRoomEnabled() {
        Verify.assertTrue(page.isLobbyRoomEnabled(AJAX_TIMEOUT), LOBBY_ROOM_ENABLED);
    }

}
