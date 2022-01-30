package com.intpfy.verifiers.event.interpreter;

import com.intpfy.gui.dialogs.interpreter.HandoverDW;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.model.Language;
import com.intpfy.verifiers.event.BaseEventVerifier;
import com.intpfyqa.gui.web.selenium.VerifyPage;
import com.intpfyqa.test.Verify;

import java.time.Duration;

import static com.intpfy.verifiers.event.common.CommonVerificationMessages.*;
import static com.intpfy.verifiers.event.interpreter.InterpreterVerificationMessages.*;
import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

public class InterpreterVerifier extends BaseEventVerifier {

    private final InterpreterPage page;

    public InterpreterVerifier(InterpreterPage page) {
        super(page);
        this.page = page;
    }

    public void assertMuted() {
        Verify.assertTrue(page.isMuted(AJAX_TIMEOUT), MUTED);
    }

    public void assertUnmuted() {
        Verify.assertTrue(page.isUnmuted(AJAX_TIMEOUT), UNMUTED);
    }

    public void verifyMuteIndicatorDisabled() {
        Verify.verifyTrue(page.isMuteIndicatorDisabled(AJAX_TIMEOUT), MUTE_INDICATOR_DISABLED);
    }

    public void assertActiveIncomingLanguage(Language language) {
        String message = String.format(ACTIVE_INCOMING_LANGUAGE, language);
        Verify.assertTrue(page.isIncomingLanguageActive(language, AJAX_TIMEOUT), message);
    }

    public void verifyActiveIncomingLanguage(Language language) {
        String message = String.format(ACTIVE_INCOMING_LANGUAGE, language);
        Verify.verifyTrue(page.isIncomingLanguageActive(language, AJAX_TIMEOUT), message);
    }

    public void verifyActiveOutgoingLanguage(Language language) {
        String message = String.format(ACTIVE_OUTGOING_LANGUAGE, language);
        Verify.verifyTrue(page.isOutgoingLanguageActive(language, AJAX_TIMEOUT), message);
    }

    public void assertActiveOutgoingLanguage(Language language) {
        String message = String.format(ACTIVE_OUTGOING_LANGUAGE, language);
        Verify.assertTrue(page.isOutgoingLanguageActive(language, AJAX_TIMEOUT), message);
    }

    public void verifyOutgoingLanguageChanged(Language language) {
        Verify.verifyTrue(page.isOutgoingLanguageChangedMessageDisplayed(language, AJAX_TIMEOUT), OUTGOING_CHANNEL_CHANGED_MESSAGE_DISPLAYED);
        verifyActiveOutgoingLanguage(language);
    }

    public void assertOutgoingLanguageChanged(Language language) {
        Verify.assertTrue(page.isOutgoingLanguageChangedMessageDisplayed(language, AJAX_TIMEOUT), OUTGOING_CHANNEL_CHANGED_MESSAGE_DISPLAYED);
        assertActiveOutgoingLanguage(language);
    }

    public void verifyHandoverAvailable() {
        Verify.verifyTrue(page.isHandoverAvailable(), HANDOVER_AVAILABLE);
    }

    public void verifyHandoverUnavailable() {
        Verify.verifyTrue(page.isHandoverUnavailable(), HANDOVER_UNAVAILABLE);
    }

    public void assertHandoverUnavailable() {
        Verify.assertTrue(page.isHandoverUnavailable(), HANDOVER_UNAVAILABLE);
    }

    public void verifyHandoverWaitingForPartnerResponse() {
        Verify.verifyTrue(page.isHandoverWaitingForPartnerResponse(), HANDOVER_WAITING_FOR_PARTNER_RESPONSE);
    }

    public void assertIncomingChannelMuted() {
        VerifyPage.onPage(page, "Incoming channel muted and it's volume level not changing.")
                .booleanValueShouldBeEqual(s -> page.isIncomingChannelMuted(AJAX_TIMEOUT), true, MUTED)
                .booleanValueShouldBeEqual(s -> page.isIncomingChannelVolumeLevelNotChanging(AJAX_TIMEOUT), true, VOLUME_LEVEL_NOT_CHANGING)
                .completeAssert();
    }

    public void assertOutgoingChannelMuted() {
        VerifyPage.onPage(page, "Outgoing channel muted and it's volume level not changing.")
                .booleanValueShouldBeEqual(s -> page.isOutgoingChannelMuted(AJAX_TIMEOUT), true, MUTED)
                .booleanValueShouldBeEqual(s -> page.isOutgoingChannelVolumeLevelNotChanging(AJAX_TIMEOUT), true, VOLUME_LEVEL_NOT_CHANGING)
                .completeAssert();
    }

    public void assertIncomingChannelUnmuted() {
        Verify.assertTrue(page.isIncomingChannelUnmuted(AJAX_TIMEOUT), INCOMING_CHANNEL_MUTED);
    }

    public void assertOutgoingChannelUnmuted() {
        Verify.assertTrue(page.isOutgoingChannelUnmuted(AJAX_TIMEOUT), OUTGOING_CHANNEL_MUTED);
    }

    public void assertVolumeLevelIsMaxForIncomingChannel() {
        Verify.assertTrue(page.isVolumeLevelMaxForIncomingChannel(), VOLUME_LEVEL_MAX_FOR_INCOMING_CHANNEL);
    }

    public void assertVolumeLevelIsMaxForOutgoingChannel() {
        Verify.assertTrue(page.isVolumeLevelMaxForOutgoingChannel(), VOLUME_LEVEL_MAX_FOR_OUTGOING_CHANNEL);
    }

    public void assertVolumeLevelIsMinForIncomingChannel() {
        Verify.assertTrue(page.isVolumeLevelMinForIncomingChannel(), VOLUME_LEVEL_MIN_FOR_INCOMING_CHANNEL);
    }

    public void assertVolumeLevelIsMinForOutgoingChannel() {
        Verify.assertTrue(page.isVolumeLevelMinForOutgoingChannel(), VOLUME_LEVEL_MIN_FOR_OUTGOING_CHANNEL);
    }

    public void assertStreaming() {
        VerifyPage.onPage(page, IS_STREAMING)
                .booleanValueShouldBeEqual(i -> page.isUnmuted(AJAX_TIMEOUT), true, UNMUTED)
                .booleanValueShouldBeEqual(i -> page.isVolumeLevelChanging(AJAX_TIMEOUT), true, VOLUME_LEVEL_CHANGING)
                .completeAssert();
    }

    public void assertCoughing() {
        Verify.assertTrue(page.isCoughing(), IS_COUGHING);
    }

    public void assertNotCoughing() {
        Verify.assertTrue(page.isNotCoughing(), IS_NOT_COUGHING);
    }

    public void verifyShortcutsIndicatorDisplayedForIncomingChannel() {
        Verify.verifyTrue(page.isShortcutsIndicatorDisplayedForIncomingChannel(AJAX_TIMEOUT), SHORTCUTS_INDICATOR_DISPLAYED_FOR_INCOMING_CHANNEL);
    }

    public void verifyShortcutsIndicatorDisplayedForOutgoingChannel() {
        Verify.verifyTrue(page.isShortcutsIndicatorDisplayedForOutgoingChannel(AJAX_TIMEOUT), SHORTCUTS_INDICATOR_DISPLAYED_FOR_OUTGOING_CHANNEL);
    }

    public void verifyShortcutsIndicatorNotDisplayedForIncomingChannel() {
        Verify.verifyTrue(page.isShortcutsIndicatorNotDisplayedForIncomingChannel(AJAX_TIMEOUT), SHORTCUTS_INDICATOR_NOT_DISPLAYED_FOR_INCOMING_CHANNEL);
    }

    public void verifyShortcutsIndicatorNotDisplayedForOutgoingChannel() {
        Verify.verifyTrue(page.isShortcutsIndicatorNotDisplayedForOutgoingChannel(AJAX_TIMEOUT), SHORTCUTS_INDICATOR_NOT_DISPLAYED_FOR_OUTGOING_CHANNEL);
    }

    public void assertMessageExistsInPartnerChat(String message) {
        Verify.assertTrue(page.messageExistsInPartnerChat(message, AJAX_TIMEOUT), String.format(MESSAGE_EXISTS_IN_PARTNER_CHAT, message));
    }

    public void verifySlowDownButtonActive() {
        Verify.verifyTrue(page.isSlowDownButtonActive(AJAX_TIMEOUT), SLOW_DOWN_BUTTON_ACTIVE);
    }

    public void verifySlowDownButtonNotActive(Duration timeout) {
        Verify.verifyTrue(page.isSlowDownButtonNotInactive(timeout), SLOW_DOWN_BUTTON_NOT_ACTIVE);
    }

    public void verifyRequestSentDWDisplayed() {
        Verify.verifyTrue(page.isRequestSentDWDisplayed(AJAX_TIMEOUT), REQUEST_SENT_DIALOG_WINDOW_DISPLAYED);
    }

    public void verifyRequestSentDWNotDisplayed(Duration timeout) {
        Verify.verifyTrue(page.isRequestSentDWDisplayed(timeout), REQUEST_SENT_DIALOG_WINDOW_NOT_DISPLAYED);
    }

    public void assertHandoverDWInMutedState(HandoverDW handoverDW) {
        Verify.assertTrue(handoverDW.isMuted(), HANDOVER_DIALOG_WINDOW_IN_MUTED_STATE);
    }
}
