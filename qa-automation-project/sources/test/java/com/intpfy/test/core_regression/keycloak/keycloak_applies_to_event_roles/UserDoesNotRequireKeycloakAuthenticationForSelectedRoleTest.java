package com.intpfy.test.core_regression.keycloak.keycloak_applies_to_event_roles;

import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.UsernamePage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.model.event.SamlSecurityGroupSet;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.KeycloakEventTest;
import com.intpfy.util.RandomUtil;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.model.event.Role.*;
import static com.intpfy.test.KeycloakTest.getDomain;
import static com.intpfy.test.TestGroups.*;

public class UserDoesNotRequireKeycloakAuthenticationForSelectedRoleTest extends BaseWebTest implements KeycloakEventTest {

    // Create Security Group Set which applies to all roles except Speaker,
    // has no Security Groups
    // and no Allowlist.
    private final SamlSecurityGroupSet samlSecurityGroupSet = new SamlSecurityGroupSet.Builder()
            .withRoles(Audience, Interpreter, Moderator)
            .withNoSecurityGroups(true)
            .build();

    // Create 'Event Pro' event with enabled Keycloak.
    private final Event event = Event
            .createEventProBuilder()
            .withKeycloakAuthentication(true)
            .withSamlSecurityGroupSet(samlSecurityGroupSet)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Verify that user doesn’t require keycloak authentication for selected role",
            description = "Keycloak authentication not required if Keycloak doesn't apply to User role.",
            groups = {
                    ONE_USER,
                    EVENT,
                    KEYCLOAK
            }
    )
    @TestRailCase("1949")
    public void test() {

        // Open 'Login' page on Organization domain.

        LoginPage loginPage = navigateToDomain(getDomain());
        loginPage.assertIsOpened();

        // Proceed to 'Username' page with Speaker token.

        UsernamePage usernamePage = loginPage.proceedToUsernamePage(event.getSpeakerToken());
        usernamePage.assertIsOpened();

        // Sign in to Speaker page.

        SpeakerPage speakerPage = usernamePage.logInAsSpeakerWithoutCallSettings(RandomUtil.createRandomSpeakerName());
        speakerPage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}
