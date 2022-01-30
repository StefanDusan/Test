package com.intpfy.test.core_regression.smoke_test.moderator;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.dialogs.logout.LogOutUserDW;
import com.intpfy.gui.dialogs.moderator.UsersSessionDW;
import com.intpfy.gui.pages.event.AdvancedModeratorPage;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.moderator.AdvancedModeratorVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class SearchThroughTheListOfUsersTest extends BaseWebTest implements EventTest {

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
            testName = "Check search through the list of users and perform action on a selected user",
            description = "Moderator can search through the list of users and perform action on selected User.",
            groups = {
                    THREE_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    SPEAKER,
                    MODERATOR
            }
    )
    @TestRailCase("1657")
    public void test() {

        // Log in as Moderator with Advanced monitoring.

        String moderatorName = RandomUtil.createRandomModeratorName();
        AdvancedModeratorPage moderatorPage = authorizer.logInAsModeratorWithAdvancedMonitoring(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Log in as Speaker.

        WebContextUtil.switchToNewContext();

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Log in as Interpreter.

        WebContextUtil.switchToNewContext();

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName);
        interpreterPage.assertIsOpened();

        // Switch to Moderator and open 'Users session' dialog window for Source session.

        WebContextUtil.switchToDefaultContext();

        UsersSessionDW usersSessionDW = moderatorPage.openUsersSessionDWForSourceSession();
        usersSessionDW.assertIsOpened();

        // Check that Speaker and Interpreter present in dialog window.

        AdvancedModeratorVerifier moderatorVerifier = new AdvancedModeratorVerifier(moderatorPage);

        moderatorVerifier.assertUserPresentInUsersSessionDW(usersSessionDW, speakerName);
        moderatorVerifier.assertUserPresentInUsersSessionDW(usersSessionDW, interpreterName);

        // Search Speaker in dialog window and check that only Speaker present after search.

        usersSessionDW.searchUser(speakerName);

        moderatorVerifier.assertUserPresentInUsersSessionDW(usersSessionDW, speakerName);
        moderatorVerifier.assertUserNotPresentInUsersSessionDW(usersSessionDW, interpreterName);

        // Log out Speaker.

        LogOutUserDW logOutUserDW = usersSessionDW.logOutUser(speakerName);
        logOutUserDW.assertIsOpened();

        logOutUserDW.confirm();
        logOutUserDW.assertNotVisible();

        // Check that neither Speaker nor Interpreter present in dialog window.

        moderatorVerifier.assertUserNotPresentInUsersSessionDW(usersSessionDW, speakerName);
        moderatorVerifier.assertUserNotPresentInUsersSessionDW(usersSessionDW, interpreterName);

        // Close dialog window.

        usersSessionDW.close();
        usersSessionDW.assertNotVisible();

        throwErrorIfVerificationsFailed();
    }
}
