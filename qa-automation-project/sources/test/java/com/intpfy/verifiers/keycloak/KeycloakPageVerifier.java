package com.intpfy.verifiers.keycloak;

import com.intpfy.gui.pages.keycloak.KeycloakPage;
import com.intpfyqa.test.Verify;

import static com.intpfy.verifiers.keycloak.KeycloakPageVerificationMessages.SIGN_IN_ERROR_MESSAGE_DISPLAYED;
import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

public final class KeycloakPageVerifier {

    private final KeycloakPage page;

    public KeycloakPageVerifier(KeycloakPage page) {
        this.page = page;
    }

    public void assertSignInErrorMessageDisplayed() {
        Verify.assertTrue(page.isSignInErrorMessageDisplayed(AJAX_TIMEOUT), SIGN_IN_ERROR_MESSAGE_DISPLAYED);
    }
}
