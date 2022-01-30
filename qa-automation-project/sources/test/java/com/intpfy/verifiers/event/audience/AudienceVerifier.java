package com.intpfy.verifiers.event.audience;

import com.intpfy.gui.pages.event.AudiencePage;
import com.intpfy.model.Language;
import com.intpfy.verifiers.event.BaseEventVerifier;
import com.intpfyqa.test.Verify;

import static com.intpfy.verifiers.event.audience.AudienceVerificationMessages.*;
import static com.intpfy.verifiers.event.common.CommonVerificationMessages.*;
import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

public class AudienceVerifier extends BaseEventVerifier {

    private final AudiencePage page;

    public AudienceVerifier(AudiencePage page) {
        super(page);
        this.page = page;
    }

    public void assertConnected() {
        Verify.assertTrue(page.isConnected(), CONNECTED);
    }

    public void assertDisconnected() {
        Verify.assertTrue(page.isDisconnected(), DISCONNECTED);
    }

    public void assertLanguageChannelSelected(Language language) {
        String message = String.format(LANGUAGE_CHANNEL_SELECTED, language);
        Verify.assertTrue(page.isLanguageChannelSelected(language, AJAX_TIMEOUT), message);
    }

    public void assertConnectedToSourceChannel() {
        assertConnectedToLanguageChannel(Language.Source);
    }

    public void assertConnectedToLanguageChannel(Language language) {
        assertConnected();
        assertLanguageChannelSelected(language);
    }

    public void verifyNoLanguageChannelSelected() {
        Verify.verifyTrue(page.isNoLanguageChannelSelected(AJAX_TIMEOUT), NO_LANGUAGE_CHANNEL_SELECTED);
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

    public void assertVideoOn() {
        assertShowVideoToggleOn();
        assertVideoPanelVisible();
    }

    public void assertVideoOff() {
        assertShowVideoToggleOff();
        assertVideoPanelNotVisible();
    }

    public void assertShowVideoToggleOn() {
        Verify.assertTrue(page.isShowVideoToggleOn(AJAX_TIMEOUT), SHOW_VIDEO_TOGGLE_ON);
    }

    public void assertShowVideoToggleOff() {
        Verify.assertTrue(page.isShowVideoToggleOff(AJAX_TIMEOUT), SHOW_VIDEO_TOGGLE_OFF);
    }

    public void assertVideoOnlyFullscreenModeEnabled() {
        Verify.assertTrue(page.isVideoOnlyFullscreenModeEnabled(AJAX_TIMEOUT), VIDEO_ONLY_FULLSCREEN_MODE_ENABLED);
    }

    public void assertVideoOnlyFullscreenModeDisabled() {
        Verify.assertTrue(page.isVideoOnlyFullscreenModeDisabled(AJAX_TIMEOUT), VIDEO_ONLY_FULLSCREEN_MODE_DISABLED);
    }
}
