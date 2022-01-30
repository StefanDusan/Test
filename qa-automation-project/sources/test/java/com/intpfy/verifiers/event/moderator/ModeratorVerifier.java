package com.intpfy.verifiers.event.moderator;

import com.intpfy.gui.dialogs.moderator.CountdownTimerDW;
import com.intpfy.gui.pages.event.ModeratorPage;
import com.intpfy.model.Language;
import com.intpfy.verifiers.event.BaseEventVerifier;
import com.intpfyqa.gui.web.selenium.VerifyPage;
import com.intpfyqa.utils.TestUtils;

import java.time.Duration;

import static com.intpfy.verifiers.event.common.CommonVerificationMessages.*;
import static com.intpfy.verifiers.event.moderator.ModeratorVerificationMessages.*;
import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;
import static com.intpfyqa.test.Verify.assertTrue;
import static com.intpfyqa.test.Verify.verifyTrue;
import static java.lang.String.format;

public class ModeratorVerifier extends BaseEventVerifier {

    private final ModeratorPage page;

    public ModeratorVerifier(ModeratorPage page) {
        super(page);
        this.page = page;
    }

    public void assertVolumeOnForSourceSession() {
        assertTrue(page.isVolumeOnForSourceSession(), VOLUME_ON_FOR_SOURCE_SESSION);
    }

    public void assertVolumeOnForLanguageSession(Language language) {
        String message = format(VOLUME_ON_FOR_LANGUAGE_SESSION, language);
        assertTrue(page.isVolumeOnForLanguageSession(language), message);
    }

    public void assertVolumeOffForLanguageSession(Language language) {
        String message = format(VOLUME_OFF_FOR_LANGUAGE_SESSION, language);
        assertTrue(page.isVolumeOffForLanguageSession(language), message);
    }

    public void assertMicOff() {
        assertTrue(page.isMicOff(), MIC_OFF);
    }

    public void assertMicOnForLanguageSession(Language language) {
        String message = format(MIC_ON_FOR_LANGUAGE_SESSION, language);
        assertTrue(page.isMicOnForLanguageSession(language, AJAX_TIMEOUT), message);
    }

    public void assertMicOffForLanguageSession(Language language) {
        String message = format(MIC_OFF_FOR_LANGUAGE_SESSION, language);
        assertTrue(page.isMicOffForLanguageSession(language, AJAX_TIMEOUT), message);
    }

    public void assertMicOffForSourceSession() {
        assertTrue(page.isMicOffForSourceSession(AJAX_TIMEOUT), MIC_OFF_FOR_SOURCE_SESSION);
    }

    public void assertIncomingVolumeLevelChangingInLanguageSession(Language language) {
        String message = format(INCOMING_VOLUME_LEVEL_IN_LANGUAGE_SESSION_CHANGING, language);
        assertTrue(page.isIncomingVolumeLevelChangingInLanguageSession(language, AJAX_TIMEOUT), message);
    }

    public void assertIncomingVolumeLevelNotChangingInLanguageSession(Language language) {
        String message = format(INCOMING_VOLUME_LEVEL_IN_LANGUAGE_SESSION_NOT_CHANGING, language);
        assertTrue(page.isIncomingVolumeLevelNotChangingInLanguageSession(language, AJAX_TIMEOUT), message);
    }

    public void assertOutgoingVolumeLevelChangingInLanguageSession(Language language) {
        String message = format(OUTGOING_VOLUME_LEVEL_IN_LANGUAGE_SESSION_CHANGING, language);
        assertTrue(page.isOutgoingVolumeLevelChangingInLanguageSession(language, AJAX_TIMEOUT), message);
    }

    public void assertOutgoingVolumeLevelNotChangingInLanguageSession(Language language) {
        String message = format(OUTGOING_VOLUME_LEVEL_IN_LANGUAGE_SESSION_NOT_CHANGING, language);
        assertTrue(page.isOutgoingVolumeLevelNotChangingInLanguageSession(language, AJAX_TIMEOUT), message);
    }

    public void assertOutgoingVolumeLevelNotChangingInSourceSession() {
        assertTrue(page.isOutgoingVolumeLevelNotChangingInSourceSession(AJAX_TIMEOUT), OUTGOING_VOLUME_LEVEL_IN_SOURCE_SESSION_NOT_CHANGING);
    }

    public void assertSlowDownDWDisplayed() {
        assertTrue(page.isSlowDownDWDisplayed(), SLOW_DOWN_DIALOG_WINDOW_DISPLAYED);
    }

    public void assertSlowDownDWNotDisplayed() {
        Duration timeout = Duration.ofSeconds(8);
        assertTrue(page.isSlowDownDWNotDisplayed(timeout), SLOW_DOWN_DIALOG_WINDOW_NOT_DISPLAYED);
    }

    public void verifySlowDownDWContent(String message, Language language, String initials) {

        String messageHeader = "'Slow down' dialog window content.";
        String messageVerificationMessage = format(SLOW_DOWN_DIALOG_WINDOW_MESSAGE, message);
        String languageVerificationMessage = format(SLOW_DOWN_DIALOG_WINDOW_LANGUAGE, language);
        String initialsVerificationMessage = format(SLOW_DOWN_DIALOG_WINDOW_INITIALS, initials);

        VerifyPage.onPage(page, messageHeader)
                .stringValueShouldBeEqual(m -> page.getSlowDownMessage(), message, messageVerificationMessage)
                .shouldBeEquals(m -> page.getSlowDownLanguage(), language, languageVerificationMessage)
                .stringValueShouldBeEqual(m -> page.getSlowDownInitials(), initials, initialsVerificationMessage)
                .completeVerify();
    }

    public void assertVideoOnForSourceSession() {
        assertTrue(page.isVideoOnForSourceSession(AJAX_TIMEOUT), VIDEO_ON_FOR_SOURCE_SESSION);
    }

    public void assertVideoOffForSourceSession() {
        assertTrue(page.isVideoOffForSourceSession(AJAX_TIMEOUT), VIDEO_OFF_FOR_SOURCE_SESSION);
    }

    public void assertMessageExistsInLanguageSessionPartnerChat(String message, Language languageSession) {
        String verificationMessage = format(MESSAGE_EXISTS_IN_LANGUAGE_SESSION_PARTNER_CHAT, languageSession, message);
        assertTrue(page.messageExistsInPartnerChat(message, languageSession, AJAX_TIMEOUT), verificationMessage);
    }

    public void assertSourceSectionShown() {
        assertTrue(page.isSourceSectionShown(AJAX_TIMEOUT), SOURCE_SECTION_SHOWN);
    }

    public void assertSourceSectionHidden() {
        assertTrue(page.isSourceSectionHidden(AJAX_TIMEOUT), SOURCE_SECTION_HIDDEN);
    }

    public void assertTimerHoursAndMinutesValue(CountdownTimerDW timerDW, int expectedHours, int expectedMinutes) {

        String hoursMessage = format(TIMER_HOURS_VALUE_EQUAL_TO, expectedHours);
        String minutesMessage = format(TIMER_MINUTES_VALUE_EQUAL_TO, expectedMinutes);

        VerifyPage.onPage(page, ACTUAL_TIMER_HOURS_AND_MINUTES_VALUE_DISPLAYED)
                .booleanValueShouldBeEqual(m -> timerDW.isHoursValueEqual(expectedHours, AJAX_TIMEOUT), true, hoursMessage)
                .booleanValueShouldBeEqual(m -> timerDW.isMinutesValueEqual(expectedMinutes, AJAX_TIMEOUT), true, minutesMessage)
                .completeAssert();
    }

    public void assertTimerHoursAndMinutesValue(int expectedHours, int expectedMinutes) {

        String hoursMessage = format(TIMER_HOURS_VALUE_EQUAL_TO, expectedHours);
        String minutesMessage = format(TIMER_MINUTES_VALUE_EQUAL_TO, expectedMinutes);

        VerifyPage.onPage(page, ACTUAL_TIMER_HOURS_AND_MINUTES_VALUE_DISPLAYED)
                .booleanValueShouldBeEqual(m -> page.isTimerHoursValueEqual(expectedHours, AJAX_TIMEOUT), true, hoursMessage)
                .booleanValueShouldBeEqual(m -> page.isTimerMinutesValueEqual(expectedMinutes, AJAX_TIMEOUT), true, minutesMessage)
                .completeAssert();
    }

    public void assertTimerSecondsValueDecreasing(Duration timeout) {

        int secondsBeforeTimeout = page.getTimerSeconds();
        TestUtils.sleep(timeout.toMillis());
        int secondsAfterTimeout = page.getTimerSeconds();

        assertTrue(secondsBeforeTimeout > secondsAfterTimeout, TIMER_SECONDS_VALUE_DECREASING);
    }

    public void verifyRalButtonActive() {
        verifyTrue(page.isRalButtonActive(AJAX_TIMEOUT), RAL_BUTTON_ACTIVE);
    }

    public void verifyRalButtonInactive() {
        verifyTrue(page.isRalButtonInactive(AJAX_TIMEOUT), RAL_BUTTON_INACTIVE);
    }

    public void verifyLanguageSessionVisible(Language language) {
        String message = format(LANGUAGE_SESSION_VISIBLE, language);
        verifyTrue(page.isLanguageSessionVisible(language, AJAX_TIMEOUT), message);
    }

    public void verifyLanguageSessionNotVisible(Language language) {
        String message = format(LANGUAGE_SESSION_NOT_VISIBLE, language);
        verifyTrue(page.isLanguageSessionNotVisible(language, AJAX_TIMEOUT), message);
    }

    public void verifySpinnerDisplayed() {
        verifyTrue(page.isSpinnerDisplayed(AJAX_TIMEOUT), SPINNER_DISPLAYED);
    }

    public void verifySpinnerNotDisplayed() {
        verifyTrue(page.isSpinnerNotDisplayed(AJAX_TIMEOUT), SPINNER_NOT_DISPLAYED);
    }
}
