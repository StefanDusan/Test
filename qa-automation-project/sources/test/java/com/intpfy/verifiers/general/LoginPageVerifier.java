package com.intpfy.verifiers.general;

import com.intpfy.gui.pages.LoginPage;
import com.intpfyqa.test.Verify;

import static com.intpfy.verifiers.general.LoginPageVerificationMessages.*;
import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

public final class LoginPageVerifier {

    private final LoginPage page;

    public LoginPageVerifier(LoginPage page) {
        this.page = page;
    }

    public void assertEventLoginErrorMessageDisplayed() {
        Verify.assertTrue(page.isEventLoginErrorMessageDisplayed(AJAX_TIMEOUT), EVENT_LOGIN_ERROR_MESSAGE_DISPLAYED);
    }

    public void assertKeycloakLoginErrorMessageDisplayed() {
        Verify.assertTrue(page.isKeycloakLoginErrorMessageDisplayed(AJAX_TIMEOUT), KEYCLOAK_LOGIN_ERROR_MESSAGE_DISPLAYED);
    }

    public void assertLoggedOutByHostMessageDisplayed() {
        Verify.assertTrue(page.isLoggedOutByHostMessageDisplayed(AJAX_TIMEOUT), LOGGED_OUT_BY_HOST_MESSAGE_DISPLAYED);
    }
}
