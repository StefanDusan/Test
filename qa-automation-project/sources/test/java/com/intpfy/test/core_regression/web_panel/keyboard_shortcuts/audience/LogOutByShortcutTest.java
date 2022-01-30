package com.intpfy.test.core_regression.web_panel.keyboard_shortcuts.audience;


import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.event.AudiencePage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class LogOutByShortcutTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create random event.
    private final Event event = Event
            .createRandomBuilder()
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Log out by shortcut",
            description = "Audience can Log out by shortcut.",
            groups = {
                    ONE_USER,
                    EVENT,
                    SHORTCUTS,
                    AUDIENCE
            }
    )
    @TestRailCase("2041")
    public void test() {

        // Log in as Audience.

        AudiencePage audiencePage = authorizer.logInAsAudience(event);
        audiencePage.assertIsOpened();

        // Log out by shortcut.

        LoginPage loginPage = audiencePage.logOutByShortcut();
        loginPage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}
