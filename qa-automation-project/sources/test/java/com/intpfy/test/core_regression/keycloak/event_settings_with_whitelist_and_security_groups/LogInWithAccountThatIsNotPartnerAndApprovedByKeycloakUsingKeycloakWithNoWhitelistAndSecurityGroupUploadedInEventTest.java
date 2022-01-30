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
import static com.intpfy.test.KeycloakTest.getDomain;
import static com.intpfy.test.KeycloakTest.getNotPartner;
import static com.intpfy.test.TestGroups.*;

public class LogInWithAccountThatIsNotPartnerAndApprovedByKeycloakUsingKeycloakWithNoWhitelistAndSecurityGroupUploadedInEventTest
        extends BaseWebTest implements KeycloakEventTest {

    // Create Security Group Set which applies to Audience,
    // has no Security Groups
    // and no Allowlist.
    private final SamlSecurityGroupSet samlSecurityGroupSet = new SamlSecurityGroupSet.Builder()
            .withRoles(Audience)
            .withNoSecurityGroups(true)
            .build();

    // Create random event with enabled Keycloak.
    private final Event event = Event
            .createRandomBuilder()
            .withKeycloakAuthentication(true)
            .withSamlSecurityGroupSet(samlSecurityGroupSet)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Log in with account that is not partner and approved by keycloak using keycloak, with no whitelist and security group uploaded in event",
            description = "Keycloak user without Partner role can Log in to Event if it has no Security Groups and no Allowlist.",
            groups = {
                    ONE_USER,
                    EVENT,
                    KEYCLOAK
            }
    )
    @TestRailCase("1947")
    public void test() {

        // Open 'Login' page on Organization domain.

        LoginPage loginPage = navigateToDomain(getDomain());
        loginPage.assertIsOpened();

        // Proceed to 'Keycloak' page with Audience token.

        KeycloakPage keycloakPage = loginPage.proceedToKeycloakPage(event.getAudienceToken());
        keycloakPage.assertIsOpened();

        // Sign in to Audience page as user without Partner role.

        AudiencePage audiencePage = keycloakPage.signInAsAudience(getNotPartner());
        audiencePage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}