package com.intpfy.test.core_regression.web_panel.moderator;

import com.intpfy.gui.dialogs.moderator.SessionsDW;
import com.intpfy.gui.pages.event.ModeratorPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class OpenCloseSessionsPopupTest extends BaseWebTest implements EventTest {

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
            testName = "Check Open / Close 'Sessions' pop-up",
            description = "Moderator can open / close 'Sessions' dialog window.",
            groups = {
                    ONE_USER,
                    EVENT,
                    MODERATOR
            }
    )
    @TestRailCase("1786")
    public void test() {

        // Log in as Moderator.

        String moderatorName = RandomUtil.createRandomModeratorName();
        ModeratorPage moderatorPage = authorizer.logInAsModerator(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Open 'Sessions' dialog window.

        SessionsDW sessionsDW = moderatorPage.openSessionsDW();
        sessionsDW.assertIsOpened();

        // Close 'Sessions' dialog window.

        sessionsDW.close();
        sessionsDW.assertNotVisible();

        throwErrorIfVerificationsFailed();
    }
}
