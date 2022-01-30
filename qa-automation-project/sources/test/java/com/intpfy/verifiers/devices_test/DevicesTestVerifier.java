package com.intpfy.verifiers.devices_test;

import com.intpfy.gui.pages.devices_test.BaseDevicesTestPage;
import com.intpfyqa.test.Verify;

import static com.intpfy.verifiers.devices_test.DevicesTestVerificationMessages.*;
import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

public class DevicesTestVerifier {

    private final BaseDevicesTestPage page;

    public DevicesTestVerifier(BaseDevicesTestPage page) {
        this.page = page;
    }

    public void assertNextButtonVisible() {
        Verify.assertTrue(page.isNextButtonVisible(AJAX_TIMEOUT), NEXT_BUTTON_VISIBLE);
    }

    public void assertSkipTutorialButtonVisible() {
        Verify.assertTrue(page.isSkipTutorialButtonVisible(AJAX_TIMEOUT), SKIP_TUTORIAL_BUTTON_VISIBLE);
    }

    public void assertDotsControlsVisible() {
        Verify.assertTrue(page.areDotsControlsVisible(AJAX_TIMEOUT), DOTS_CONTROLS_VISIBLE);
    }
}
