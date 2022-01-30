package com.intpfy.verifiers.emi.user_creation;

import com.intpfy.gui.pages.emi.user_creation.EditUserPage;
import com.intpfy.verifiers.emi.BaseEmiVerifier;
import com.intpfyqa.test.Verify;

import static com.intpfy.verifiers.emi.user_creation.UserCreationVerificationMessages.*;
import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

public final class EditUserPageVerifier extends BaseEmiVerifier {

    private final EditUserPage page;

    public EditUserPageVerifier(EditUserPage page) {
        super(page);
        this.page = page;
    }

    public void assertDomainNameEquals(String name) {
        Verify.assertTrue(page.isDomainNameEqual(name, AJAX_TIMEOUT), String.format(DOMAIN_NAME_EQUALS, name));
    }

    public void assertActiveDirectoryAuthenticationEnabled() {
        Verify.assertTrue(page.isActiveDirectoryAuthenticationEnabled(AJAX_TIMEOUT), ACTIVE_DIRECTORY_AUTHENTICATION_ENABLED);
    }

    public void assertActiveDirectoryAuthenticationDisabled() {
        Verify.assertTrue(page.isActiveDirectoryAuthenticationDisabled(AJAX_TIMEOUT), ACTIVE_DIRECTORY_AUTHENTICATION_DISABLED);
    }

    public void assertKeycloakEnabled() {
        Verify.assertTrue(page.isKeycloakEnabled(AJAX_TIMEOUT), KEYCLOAK_ENABLED);
    }

    public void assertKeycloakDisabled() {
        Verify.assertTrue(page.isKeycloakDisabled(AJAX_TIMEOUT), KEYCLOAK_DISABLED);
    }

    public void assertSecurityGroupEquals(String group) {
        Verify.assertTrue(page.isSecurityGroupEqual(group, AJAX_TIMEOUT), String.format(SECURITY_GROUP_EQUALS, group));
    }

    public void assertSecurityGroupEmpty() {
        Verify.assertTrue(page.isSecurityGroupEmpty(AJAX_TIMEOUT), SECURITY_GROUP_EMPTY);
    }

    public void assertAllowlistCreated() {
        Verify.assertTrue(page.isAllowlistCreated(AJAX_TIMEOUT), ALLOWLIST_CREATED);
    }

    public void assertAllowlistNotCreated() {
        Verify.assertTrue(page.isAllowlistNotCreated(AJAX_TIMEOUT), ALLOWLIST_NOT_CREATED);
    }

    public void assertActiveDirectoryIdentifierEquals(String identifier) {
        String message = String.format(ACTIVE_DIRECTORY_IDENTIFIER_EQUALS, identifier);
        Verify.assertTrue(page.isActiveDirectoryIdentifierEqual(identifier, AJAX_TIMEOUT), message);
    }
}
