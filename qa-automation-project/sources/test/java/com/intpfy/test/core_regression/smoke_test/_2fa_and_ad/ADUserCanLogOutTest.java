package com.intpfy.test.core_regression.smoke_test._2fa_and_ad;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.ActiveDirectoryPage;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.event.AudiencePage;
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

public class ADUserCanLogOutTest extends BaseWebTest implements EventTest, ActiveDirectoryTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    private final SamlSecurityGroupSet samlSecurityGroupSet = new SamlSecurityGroupSet.Builder()
            .withRoles(Role.Audience)
            .withNoSecurityGroups(false)
            .withGroups(getCorrectSecurityGroup())
            .build();

    // Create random event with enabled AD authentication
    // and Security Group Set which applies to Audience
    // and has correct Security Group.
    private final Event event = Event
            .createRandomBuilder()
            .withSamlAuthenticationRequired(true)
            .withSamlSecurityGroupSet(samlSecurityGroupSet)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check that AD user can logout",
            description = "AD user can Log out.",
            groups = {
                    ONE_USER,
                    EVENT,
                    ACTIVE_DIRECTORY,
                    AUDIENCE
            }
    )
    @TestRailCase("828")
    public void test() {

        // Open 'Login' page.

        LoginPage loginPage = authorizer.logOut();
        loginPage.assertIsOpened();

        // Proceed to 'Active Directory' page.

        ActiveDirectoryPage activeDirectoryPage = loginPage.proceedToActiveDirectoryPage(event.getAudienceToken());
        activeDirectoryPage.assertIsOpened();

        // Log in as Audience.

        AudiencePage audiencePage = activeDirectoryPage.logInAsAudience(getEmailForLogin(), getPassword());
        audiencePage.assertIsOpened();

        // Log out.

        loginPage = audiencePage.logOut();
        loginPage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}
