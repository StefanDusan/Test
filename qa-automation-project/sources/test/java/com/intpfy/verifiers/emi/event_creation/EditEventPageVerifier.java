package com.intpfy.verifiers.emi.event_creation;

import com.intpfy.gui.pages.emi.event_creation.EditEventPage;
import com.intpfy.verifiers.emi.BaseEmiVerifier;

import static com.intpfy.verifiers.emi.event_creation.EditEventPageVerificationMessages.PRE_CALL_TEST_ENABLED_FOR_AUDIENCE;
import static com.intpfy.verifiers.emi.event_creation.EditEventPageVerificationMessages.PRE_CALL_TEST_ENABLED_FOR_INTERPRETER_AND_SPEAKER;
import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;
import static com.intpfyqa.test.Verify.assertTrue;

public final class EditEventPageVerifier extends BaseEmiVerifier {

    private final EditEventPage page;

    public EditEventPageVerifier(EditEventPage page) {
        super(page);
        this.page = page;
    }

    public void assertPreCallTestEnabledForAudience() {
        assertTrue(page.isPreCallTestEnabledForAudience(AJAX_TIMEOUT), PRE_CALL_TEST_ENABLED_FOR_AUDIENCE);
    }

    public void assertPreCallTestEnabledForInterpreterAndSpeaker() {
        assertTrue(page.isPreCallTestEnabledForInterpreterAndSpeaker(AJAX_TIMEOUT), PRE_CALL_TEST_ENABLED_FOR_INTERPRETER_AND_SPEAKER);
    }
}
