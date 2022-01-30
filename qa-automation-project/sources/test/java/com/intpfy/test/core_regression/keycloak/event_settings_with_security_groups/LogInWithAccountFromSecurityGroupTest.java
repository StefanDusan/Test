package com.intpfy.test.core_regression.keycloak.event_settings_with_security_groups;

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

public class LogInWithAccountFromSecurityGroupTest extends BaseWebTest implements KeycloakEventTest {

    private Event event;

    @Override
    public Event getEvent() {

        if (event == null) {

            // Create Security Group Set which applies to Audience,
            // has Security Group and no Allowlist.
            SamlSecurityGroupSet samlSecurityGroupSet = new SamlSecurityGroupSet.Builder()
                    .withRoles(Audience)
                    .withNoSecurityGroups(false)
                    .withGroups(getSecurityGroup())
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
            testName = "Log in with account from security group",
            description = "Keycloak user without Partner role but belonging to Security Group can Log in to Event if it has Security Group.",
            groups = {
                    ONE_USER,
                    EVENT,
                    KEYCLOAK
            }
    )
    @TestRailCase("1938")
    public void test() {

        // Open 'Login' page on Organization domain.

        LoginPage loginPage = navigateToDomain(getDomain());
        loginPage.assertIsOpened();

        // Proceed to 'Keycloak' page with Audience token.

        KeycloakPage keycloakPage = loginPage.proceedToKeycloakPage(event.getAudienceToken());
        keycloakPage.assertIsOpened();

        // Sign in to Audience page as user without Partner role but belonging to Security Group.

        AudiencePage audiencePage = keycloakPage.signInAsAudience(getNotPartnerWithSecurityGroup());
        audiencePage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}
