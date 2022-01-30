package com.intpfy.verifiers.emi.users_page;

import com.intpfy.gui.pages.emi.UsersPage;
import com.intpfy.verifiers.emi.BaseEmiVerifier;
import com.intpfyqa.test.Verify;

import static com.intpfy.verifiers.emi.users_page.UsersPageVerificationMessages.USER_DISPLAYED;
import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

public final class UsersPageVerifier extends BaseEmiVerifier {

    private final UsersPage page;

    public UsersPageVerifier(UsersPage page) {
        super(page);
        this.page = page;
    }

    public void assertUserDisplayed(String email) {
        Verify.assertTrue(page.isDisplayed(email, AJAX_TIMEOUT), String.format(USER_DISPLAYED, email));
    }
}
