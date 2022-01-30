package com.intpfy.test.core_regression.keycloak.event_settings_with_whitelist;

import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.event.AudiencePage;
import com.intpfy.gui.pages.keycloak.KeycloakPage;
import com.intpfy.model.event.Event;
import com.intpfy.model.event.SamlSecurityGroupSet;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.KeycloakEventTest;
import com.intpfy.user.KeycloakUser;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.model.event.Role.Audience;
import static com.intpfy.test.KeycloakTest.getDomain;
import static com.intpfy.test.KeycloakTest.getNotPartner;
import static com.intpfy.test.TestGroups.*;

public class LogInWithAccountFromWhitelistTest extends BaseWebTest implements KeycloakEventTest {

    private Event event;

    private KeycloakUser notPartner;

    @Override
    public Event getEvent() {

        if (event == null) {

            notPartner = getNotPartner();

            // Create Security Group Set which applies to Audience,
            // has Allowlist containing Email approved by Keycloak (the same as will be used for Login)
            // and no Security Groups.
            SamlSecurityGroupSet samlSecurityGroupSet = new SamlSecurityGroupSet.Builder()
                    .withRoles(Audience)
                    .withNoSecurityGroups(true)
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
            description = "Keycloak user without Partner role can Log in to Event " +
                    "if it has Allowlist, containing user Email, and no Security Groups.",
            groups = {
                    ONE_USER,
                    EVENT,
                    KEYCLOAK
            }
    )
    @TestRailCase("1934")
    public void test() {

        // Open 'Login' page on Organization domain.

        LoginPage loginPage = navigateToDomain(getDomain());
        loginPage.assertIsOpened();

        // Proceed to 'Keycloak' page with Audience token.

        KeycloakPage keycloakPage = loginPage.proceedToKeycloakPage(event.getAudienceToken());
        keycloakPage.assertIsOpened();

        // Sign in to Audience page as user without Partner role.

        AudiencePage audiencePage = keycloakPage.signInAsAudience(notPartner);
        audiencePage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}
