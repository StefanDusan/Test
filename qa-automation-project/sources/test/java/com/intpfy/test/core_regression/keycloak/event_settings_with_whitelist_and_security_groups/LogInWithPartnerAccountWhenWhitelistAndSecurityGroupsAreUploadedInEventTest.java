package com.intpfy.test.core_regression.keycloak.event_settings_with_whitelist_and_security_groups;

import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.event.AudiencePage;
import com.intpfy.gui.pages.keycloak.KeycloakPage;
import com.intpfy.model.event.Event;
import com.intpfy.model.event.SamlSecurityGroupSet;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.KeycloakEventTest;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.model.event.Role.Audience;
import static com.intpfy.test.KeycloakTest.*;
import static com.intpfy.test.TestGroups.*;

public class LogInWithPartnerAccountWhenWhitelistAndSecurityGroupsAreUploadedInEventTest extends BaseWebTest implements KeycloakEventTest {

    private Event event;

    @Override
    public Event getEvent() {

        if (event == null) {

            // Create Security Group Set which applies to Audience,
            // has Security Group
            // and Allowlist containing Email approved by Keycloak (but not the same as will be used for Login).
            SamlSecurityGroupSet samlSecurityGroupSet = new SamlSecurityGroupSet.Builder()
                    .withRoles(Audience)
                    .withNoSecurityGroups(true)
                    .withGroups(getSecurityGroup())
                    .withAllowlistEmails(getNotPartner().getEmail())
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
            testName = "Log in with partner account when whitelist and security groups are uploaded in event",
            description = "Keycloak user with Partner role can Log in to Event if it has Allowlist, containing Email different from user's, and Security Group.",
            groups = {
                    ONE_USER,
                    EVENT,
                    KEYCLOAK
            }
    )
    @TestRailCase("1942")
    public void test() {

        // Open 'Login' page on Organization domain.

        LoginPage loginPage = navigateToDomain(getDomain());
        loginPage.assertIsOpened();

        // Proceed to 'Keycloak' page with Audience token.

        KeycloakPage keycloakPage = loginPage.proceedToKeycloakPage(event.getAudienceToken());
        keycloakPage.assertIsOpened();

        // Sign in to Audience page as user with Partner role.

        AudiencePage audiencePage = keycloakPage.signInAsAudience(getPartner());
        audiencePage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}
