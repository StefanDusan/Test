package com.intpfy.test.core_regression.smoke_test._2fa_and_ad;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.ActiveDirectoryPage;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.model.event.Role;
import com.intpfy.model.event.SamlSecurityGroupSet;
import com.intpfy.model.user.OrganizationModel;
import com.intpfy.test.ActiveDirectoryTest;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.test.OrganizationTest;
import com.intpfy.user.OrganizationUser;
import com.intpfy.util.BrowserUtil;
import com.intpfy.util.RandomUtil;
import com.intpfyqa.gui.web.selenium.browser.BrowserWindow;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.ActiveDirectoryTest.*;
import static com.intpfy.test.TestGroups.*;

public class YouCannotAccessEMIWhenUnauthorizedInNewTabTest extends BaseWebTest implements EventTest, OrganizationTest, ActiveDirectoryTest {

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

    private OrganizationUser organizationUser;
    private OrganizationModel organizationModel;

    @Override
    public Event getEvent() {
        return event;
    }

    @Override
    public OrganizationUser getOrganizationUser() {

        if (organizationUser == null) {
            organizationUser = takeUser();
        }
        return organizationUser;
    }

    // Create Organization model with enabled AD authentication,
    // incorrect Security Group equal to Identifier
    // and no Allowlist.
    @Override
    public OrganizationModel getOrganizationModel() {

        if (organizationModel == null) {

            organizationModel = new OrganizationModel.Builder()
                    .withLogin(getOrganizationUser().getEmail())
                    .withDomain(RandomUtil.createRandomDomainName())
                    .withSamlIdProviderCode(getIdentifier())
                    .withSamlRequiredGroup(getIdentifier())
                    .build();
        }
        return organizationModel;
    }

    @Test(
            testName = "Verify you cannot access EMI when un-authorized in new tab",
            description = "AD user not added to Allowlist and having incorrect Security Group can not Log in to EMI.",
            groups = {
                    ONE_USER,
                    EMI,
                    EVENT,
                    ACTIVE_DIRECTORY,
                    SPEAKER
            }
    )
    @TestRailCase("1633")
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

        // Open new browser tab on Organization domain.

        BrowserWindow browserWindow = BrowserUtil.openNewWindow(createUrlWithDomain(organizationModel.getDomain()));

        loginPage = new LoginPage(browserWindow);
        loginPage.assertIsOpened();

        // Try to proceed to 'Active directory' page as Admin and check that user remains on 'Login' page.

        loginPage.proceedToActiveDirectoryPageAsAdmin();
        loginPage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}
