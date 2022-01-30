package com.intpfy.test.core_regression.admin_panel.features_stage_test;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.ActiveDirectoryPage;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.model.event.Event;
import com.intpfy.model.event.Role;
import com.intpfy.model.event.SamlSecurityGroupSet;
import com.intpfy.test.ActiveDirectoryTest;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.verifiers.general.LoginPageVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.ActiveDirectoryTest.*;
import static com.intpfy.test.TestGroups.*;

public class UserCannotJoinEventWhenIsNotAddedToWhitelistTest extends BaseWebTest implements EventTest, ActiveDirectoryTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    private final SamlSecurityGroupSet samlSecurityGroupSet = new SamlSecurityGroupSet.Builder()
            .withRoles(Role.Interpreter)
            .withNoSecurityGroups(true)
            .withAllowlistEmails(getIncorrectEmailForAllowlist())
            .build();

    // Create random event with enabled AD authentication
    // and Security Group Set which applies to Interpreter,
    // has no Security Groups
    // and has Allowlist with incorrect Email.
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
            testName = "[Web AD] User cannot join event when is not added to whitelist",
            description = "User cannot join event when is not added to Whitelist.",
            groups = {
                    ONE_USER,
                    EVENT,
                    ACTIVE_DIRECTORY,
                    INTERPRETER
            }
    )
    @TestRailCase("1878")
    public void test() {

        // Open 'Login' page.

        LoginPage loginPage = authorizer.logOut();
        loginPage.assertIsOpened();

        // Proceed to 'Active Directory' page.

        ActiveDirectoryPage activeDirectoryPage = loginPage.proceedToActiveDirectoryPage(event.getInterpreterToken());
        activeDirectoryPage.assertIsOpened();

        // Try to Log in as Interpreter and check that 'Login' page opened instead of 'Interpreter' page.

        activeDirectoryPage.logInAsInterpreter(getEmailForLogin(), getPassword());
        loginPage.assertIsOpened();

        // Check that Event Login error message displayed.

        LoginPageVerifier loginPageVerifier = new LoginPageVerifier(loginPage);
        loginPageVerifier.assertEventLoginErrorMessageDisplayed();

        throwErrorIfVerificationsFailed();
    }
}
