package com.intpfy.test.core_regression.admin_panel.features_stage_test;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.dialogs.common.LanguageSettingsDW;
import com.intpfy.gui.pages.ActiveDirectoryPage;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.model.event.Event;
import com.intpfy.model.event.Role;
import com.intpfy.model.event.SamlSecurityGroupSet;
import com.intpfy.test.ActiveDirectoryTest;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.util.BrowserUtil;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.ActiveDirectoryTest.*;
import static com.intpfy.test.TestGroups.*;

public class UserCanJoinEventWhenIsAddedToWhitelistTest extends BaseWebTest implements EventTest, ActiveDirectoryTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    private final SamlSecurityGroupSet samlSecurityGroupSet = new SamlSecurityGroupSet.Builder()
            .withRoles(Role.Interpreter)
            .withNoSecurityGroups(true)
            .withAllowlistEmails(getCorrectEmailForAllowlist())
            .build();

    // Create random event with enabled AD authentication
    // and Security Group Set which applies to Interpreter,
    // has no Security Groups
    // and has Allowlist with correct Email.
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
            testName = "[Web AD] User can join event when is added to whitelist",
            description = "User can join event when is added to Whitelist.",
            groups = {
                    ONE_USER,
                    EVENT,
                    ACTIVE_DIRECTORY,
                    INTERPRETER
            }
    )
    @TestRailCase("1879")
    public void test() {

        // Open 'Login' page.

        LoginPage loginPage = authorizer.logOut();
        loginPage.assertIsOpened();

        // Proceed to 'Active Directory' page.

        ActiveDirectoryPage activeDirectoryPage = loginPage.proceedToActiveDirectoryPage(event.getInterpreterToken());
        activeDirectoryPage.assertIsOpened();

        // Log in as Interpreter.

        LanguageSettingsDW languageSettingsDW = activeDirectoryPage.logInAsInterpreter(getEmailForLogin(), getPassword());
        languageSettingsDW.assertIsOpened();

        languageSettingsDW.save();
        languageSettingsDW.assertNotVisible();

        InterpreterPage interpreterPage = new InterpreterPage(BrowserUtil.getActiveWindow());
        interpreterPage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}
