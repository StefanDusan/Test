package com.intpfy.test.core_regression.keycloak.event_settings_with_whitelist_and_security_groups;

import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.keycloak.KeycloakPage;
import com.intpfy.model.event.Event;
import com.intpfy.model.event.SamlSecurityGroupSet;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.KeycloakEventTest;
import com.intpfy.user.KeycloakUser;
import com.intpfy.verifiers.general.LoginPageVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.model.event.Role.Audience;
import static com.intpfy.test.KeycloakTest.*;
import static com.intpfy.test.TestGroups.*;

public class LogInWithAccountFromWhitelistTest extends BaseWebTest implements KeycloakEventTest {

    private Event event;

    private KeycloakUser notPartner;

    @Override
    public Event getEvent() {

        if (event == null) {

            notPartner = getNotPartner();

            // Create Security Group Set which applies to Audience,
            // has Security Group
            // and Allowlist containing Email approved by Keycloak (the same as will be used for Login).
            SamlSecurityGroupSet samlSecurityGroupSet = new SamlSecurityGroupSet.Builder()
                    .withRoles(Audience)
                    .withNoSecurityGroups(false)
                    .withGroups(getSecurityGroup())
                    .withAllowlistEmails(notPartner.getEmail())
                    .build();

            // Create random event with enabled Keycloak.
            event = Event
                    .createRandomBuilder()
                    .withKeycloakAuthentication(true)
                    .withSamlSecurityGroupSet(samlSecurityGroupSet)
                    .build();
        }

        return event;
    }

    @Test(
            testName = "Log in with account from whitelist",
            description = "Keycloak user without Partner role can not Log in to Event " +
                    "if it has Allowlist, containing user Email, and Security Group added by Organization.",
            groups = {
                    ONE_USER,
                    EVENT,
                    KEYCLOAK
            }
    )
    @TestRailCase("1943")
    public void test() {

        // Open 'Login' page on Organization domain.

        LoginPage loginPage = navigateToDomain(getDomain());
        loginPage.assertIsOpened();

        // Proceed to 'Keycloak' page with Audience token.

        KeycloakPage keycloakPage = loginPage.proceedToKeycloakPage(event.getAudienceToken());
        keycloakPage.assertIsOpened();

        // Try to Sign in to Audience page as user without Partner role.

        keycloakPage.signInAsAudience(notPartner);

        // Check that 'Login' page opened instead of 'Audience' page and Keycloak Login error message displayed.

        loginPage.assertIsOpened();

        LoginPageVerifier loginPageVerifier = new LoginPageVerifier(loginPage);
        loginPageVerifier.assertKeycloakLoginErrorMessageDisplayed();

        throwErrorIfVerificationsFailed();
    }
}
