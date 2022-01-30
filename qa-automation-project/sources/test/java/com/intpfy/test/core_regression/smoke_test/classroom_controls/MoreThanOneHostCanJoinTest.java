package com.intpfy.test.core_regression.smoke_test.classroom_controls;

import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class MoreThanOneHostCanJoinTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create 'Connect Pro (Classroom)' event.
    private final Event event = Event
            .createConnectProClassroomBuilder()
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check that more than one Host can join",
            description = "More than one Host can join event.",
            groups = {
                    TWO_USERS,
                    EVENT,
                    SPEAKER
            }
    )
    @TestRailCase("835")
    public void test() {

        // Log in as Host 1 with Meeting control only.

        String firstHostName = RandomUtil.createRandomHostName();
        SpeakerPage firstHostPage = authorizer.logInAsHostWithMeetingControlOnly(event, firstHostName);
        firstHostPage.assertIsOpened();

        // Log in as Host 2 with Meeting control only.

        WebContextUtil.switchToNewContext();

        String secondHostName = RandomUtil.createRandomHostName();
        SpeakerPage secondHostPage = authorizer.logInAsHostWithMeetingControlOnly(event, secondHostName);
        secondHostPage.assertIsOpened();

        secondHostPage.confirmHostJoinedMeetingDW();

        // Switch to Host 1 and check that is still logged in.

        WebContextUtil.switchToDefaultContext();

        firstHostPage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}
