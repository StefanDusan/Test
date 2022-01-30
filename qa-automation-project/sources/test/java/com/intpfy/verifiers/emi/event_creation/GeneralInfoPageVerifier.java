package com.intpfy.verifiers.emi.event_creation;

import com.intpfy.gui.pages.emi.event_creation.GeneralInfoPage;
import com.intpfy.verifiers.emi.BaseEmiVerifier;
import com.intpfyqa.test.Verify;

import static com.intpfy.verifiers.emi.event_creation.GeneralInfoPageVerificationMessages.HOST_PASSWORD_EQUALS;
import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

public class GeneralInfoPageVerifier extends BaseEmiVerifier {

    private final GeneralInfoPage page;

    public GeneralInfoPageVerifier(GeneralInfoPage page) {
        super(page);
        this.page = page;
    }

    public void assertHostPasswordEquals(String password) {
        Verify.assertTrue(page.isHostPasswordEqual(password, AJAX_TIMEOUT), String.format(HOST_PASSWORD_EQUALS, password));
    }
}
