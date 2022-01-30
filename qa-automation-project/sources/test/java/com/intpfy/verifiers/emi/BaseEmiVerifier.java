package com.intpfy.verifiers.emi;

import com.intpfy.gui.pages.emi.BaseEmiPage;
import com.intpfyqa.test.Verify;

import java.io.File;

import static com.intpfy.verifiers.emi.BaseEmiVerificationMessages.EMAIL_EQUALS;
import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

public abstract class BaseEmiVerifier {

    private final BaseEmiPage page;

    protected BaseEmiVerifier(BaseEmiPage page) {
        this.page = page;
    }

    public void assertEmailEquals(String email) {
        Verify.assertTrue(page.isEmailEqual(email, AJAX_TIMEOUT), String.format(EMAIL_EQUALS, email));
    }

    protected void verifyFileExists(File file, String verificationMessage) {
        Verify.verifyTrue(file.exists(), verificationMessage);
    }
}
