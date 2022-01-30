package com.intpfy.test.core_regression.web_panel.speaker.speaker_webmeet_classroom_with_chairperson;


import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class SlowDownButtonSTokenTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    private final Language language = Language.getRandomLanguage();

    // Create 'Connect Pro (Classroom)' event with 1 Language.
    private final Event event = Event
            .createConnectProClassroomBuilder()
            .withLanguage(language)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "The button 'Slow-down' works correctly (S-token)",
            description = "Speaker sees 'Slow-down' dialog window if Interpreter requests Slow down.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    INTERPRETER,
                    SPEAKER
            }
    )
    @TestRailCase("1635")
    public void test() {

        // Log in as Host with Meeting control only.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithMeetingControlOnly(event, hostName);
        hostPage.assertIsOpened();

        // Log in as Interpreter with Outgoing language.

        WebContextUtil.switchToNewContext();

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, language);
        interpreterPage.assertIsOpened();

        // Request Slow down.

        interpreterPage.requestSlowDown();

        // Switch to Speaker and check that 'Slow down' dialog window with expected message, language and initials displayed.

        WebContextUtil.switchToDefaultContext();

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(hostPage);

        speakerVerifier.assertSlowDownDWDisplayed();
        speakerVerifier.verifySlowDownDW("Please slow down", language, "I.");

        // Check that 'Slow down' dialog window not displayed after some time.

        speakerVerifier.assertSlowDownDWNotDisplayed();

        throwErrorIfVerificationsFailed();
    }
}
