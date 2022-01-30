package com.intpfy.test.core_regression.smoke_test.classroom_controls;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.run.web.WebApplicationContext;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.util.BrowserUtil;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfy.verifiers.general.LoginPageVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class HostCanLogOutSpeakerTest extends BaseWebTest implements EventTest {

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
            testName = "Check that Host can log out Speaker",
            description = "Host can Log out Speaker",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    SPEAKER,
            }
    )
    @TestRailCase("798")
    public void test() {

        // Log in as Host with Meeting control only.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithMeetingControlOnly(event, hostName);
        hostPage.assertIsOpened();

        // Log in as Speaker.

        WebApplicationContext speakerContext = WebContextUtil.switchToNewContext();

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Switch to Host and Log out Speaker.

        WebContextUtil.switchToDefaultContext();

        hostPage.logOutSpeakerFromParticipants(speakerName);

        // Check that Speaker not present in 'Participants' list.

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);
        hostVerifier.verifySpeakerNotPresentInParticipants(speakerName);

        // Switch to Speaker and check that 'Login' page opened.

        WebContextUtil.switchToContext(speakerContext);

        LoginPage loginPage = new LoginPage(BrowserUtil.getActiveWindow());
        loginPage.assertIsOpened();

        // Check that 'Logged out by Host' message displayed.

        LoginPageVerifier loginPageVerifier = new LoginPageVerifier(loginPage);
        loginPageVerifier.assertLoggedOutByHostMessageDisplayed();

        throwErrorIfVerificationsFailed();
    }
}
