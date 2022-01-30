package com.intpfy.verifiers.emi;

import com.intpfy.gui.pages.emi.ProfilePage;
import com.intpfyqa.test.Verify;

import static com.intpfy.verifiers.emi.ProfilePageVerificationMessages.PROFILE_DATA_EMAIL_NOT_EQUALS;
import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

public class ProfilePageVerifier extends BaseEmiVerifier {

    private final ProfilePage page;

    public ProfilePageVerifier(ProfilePage page) {
        super(page);
        this.page = page;
    }

    public void assertProfileDataEmailNotEquals(String email) {
        String message = String.format(PROFILE_DATA_EMAIL_NOT_EQUALS, email);
        Verify.assertTrue(page.isProfileDataEmailNotEqual(email, AJAX_TIMEOUT), message);
    }
}
