package com.intpfy.test.core_regression.web_panel.moderator_advanced_mode_event_pro_or_webmeet;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.dialogs.logout.LogOutUserDW;
import com.intpfy.gui.dialogs.moderator.UsersSessionDW;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.event.AdvancedModeratorPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.util.BrowserUtil;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.moderator.AdvancedModeratorVerifier;
import com.intpfyqa.test_rail.TestRailBugId;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class LogOutUserTest extends BaseWebTest implements EventTest {

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
            testName = "Check the logout user",
            description = "Moderator can Log out user.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    SPEAKER,
                    MODERATOR
            }
    )
    @TestRailCase("1557")
    @TestRailBugId("CORE-6826")
    public void test() {

        // Log in as Speaker.

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Log in as Moderator with Advanced monitoring.

        WebContextUtil.switchToNewContext();

        String moderatorName = RandomUtil.createRandomModeratorName();
        AdvancedModeratorPage moderatorPage = authorizer.logInAsModeratorWithAdvancedMonitoring(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Open 'Users session' dialog window for Source session.

        UsersSessionDW usersSessionDW = moderatorPage.openUsersSessionDWForSourceSession();
        usersSessionDW.assertIsOpened();

        // Check that Speaker present in dialog window.

        AdvancedModeratorVerifier moderatorVerifier = new AdvancedModeratorVerifier(moderatorPage);

        moderatorVerifier.assertUserPresentInUsersSessionDW(usersSessionDW, speakerName);

        // Log out Speaker.

        LogOutUserDW logOutUserDW = usersSessionDW.logOutUser(speakerName);
        logOutUserDW.assertIsOpened();

        logOutUserDW.confirm();
        logOutUserDW.assertNotVisible();

        // Check that Speaker not present in dialog window.

        moderatorVerifier.assertUserNotPresentInUsersSessionDW(usersSessionDW, speakerName);

        // Close dialog window and check that Speaker not present in Source session.

        usersSessionDW.close();
        usersSessionDW.assertNotVisible();

        moderatorVerifier.assertUserNotPresentInSourceSession(speakerName);

        // Switch to Speaker and check that 'Login' page opened.

        WebContextUtil.switchToDefaultContext();

        LoginPage loginPage = new LoginPage(BrowserUtil.getActiveWindow());
        loginPage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}
