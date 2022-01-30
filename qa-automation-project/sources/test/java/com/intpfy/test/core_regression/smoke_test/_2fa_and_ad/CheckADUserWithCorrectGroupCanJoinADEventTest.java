package com.intpfy.test.core_regression.smoke_test._2fa_and_ad;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.ActiveDirectoryPage;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.model.event.Role;
import com.intpfy.model.event.SamlSecurityGroupSet;
import com.intpfy.test.ActiveDirectoryTest;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.ActiveDirectoryTest.*;
import static com.intpfy.test.TestGroups.*;

public class CheckADUserWithCorrectGroupCanJoinADEventTest extends BaseWebTest implements EventTest, ActiveDirectoryTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    private final SamlSecurityGroupSet samlSecurityGroupSet = new SamlSecurityGroupSet.Builder()
            .withRoles(Role.Speaker)
            .withNoSecurityGroups(false)
            .withGroups(getCorrectSecurityGroup())
            .build();

    // Create 'Event Pro' event with enabled AD authentication
    // and Security Group Set which applies to Speaker
    // and has correct Security Group.
    private final Event event = Event
            .createEventProBuilder()
            .withSamlAuthenticationRequired(true)
            .withSamlSecurityGroupSet(samlSecurityGroupSet)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check AD user with correct group can join AD event",
            description = "AD user with correct group can join AD Event.",
            groups = {
                    ONE_USER,
                    EVENT,
                    ACTIVE_DIRECTORY,
                    SPEAKER
            }
    )
    @TestRailCase("825")
    public void test() {

        // Open 'Login' page.

        LoginPage loginPage = authorizer.logOut();
        loginPage.assertIsOpened();

        // Proceed to 'Active Directory' page.

        ActiveDirectoryPage activeDirectoryPage = loginPage.proceedToActiveDirectoryPage(event.getSpeakerToken());
        activeDirectoryPage.assertIsOpened();

        // Log in as Speaker.

        SpeakerPage speakerPage = activeDirectoryPage.logInAsSpeakerToEventPro(getEmailForLogin(), getPassword());
        speakerPage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}
