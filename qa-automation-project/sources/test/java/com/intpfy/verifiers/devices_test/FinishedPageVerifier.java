package com.intpfy.verifiers.devices_test;

import com.intpfy.gui.pages.devices_test.FinishedPage;
import com.intpfyqa.test.Verify;

import static com.intpfy.verifiers.devices_test.FinishedPageVerificationMessages.VIDEO_PLAYING;
import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

public final class FinishedPageVerifier extends DevicesTestVerifier {

    private final FinishedPage page;

    public FinishedPageVerifier(FinishedPage page) {
        super(page);
        this.page = page;
    }

    public void assertVideoPlaying() {
        Verify.assertTrue(page.isVideoPlaying(AJAX_TIMEOUT), VIDEO_PLAYING);
    }
}
