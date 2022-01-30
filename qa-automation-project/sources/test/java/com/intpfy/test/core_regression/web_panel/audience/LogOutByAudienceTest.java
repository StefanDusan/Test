package com.intpfy.test.core_regression.web_panel.audience;

import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.event.AudiencePage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class LogOutByAudienceTest extends BaseWebTest implements EventTest {

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
            testName = "Check logout by Audience",
            description = "Audience can Log out",
            groups = {
                    ONE_USER,
                    EVENT,
                    AUDIENCE
            }
    )
    @TestRailCase("1539")
    public void test() {

        // Log in as Audience.

        AudiencePage audiencePage = authorizer.logInAsAudience(event);
        audiencePage.assertIsOpened();

        // Log out.

        LoginPage loginPage = audiencePage.logOut();
        loginPage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}
