package com.intpfy.test.core_regression.web_panel.interpreter;

import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.interpreter.InterpreterVerifier;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class VideoIsDisplayedFromSpeakerTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create 'Connect (WebMeet)' event.
    private final Event event = Event
            .createConnectWebMeetBuilder()
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check the video is displayed from speaker",
            description = "Interpreter can show and hide Video from Speaker.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    INTERPRETER,
                    SPEAKER
            }
    )
    @TestRailCase("1535")
    public void test() {

        // Log in as Speaker with Audio and Video.

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeakerWithAudioAndVideo(event, speakerName);
        speakerPage.assertIsOpened();

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Log in as Interpreter.

        WebContextUtil.switchToNewContext();

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName);
        interpreterPage.assertIsOpened();

        // Check that video panel and video container for Speaker visible.

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);

        interpreterVerifier.assertVideoPanelVisible();
        interpreterVerifier.assertVideoContainerVisible(speakerName);

        // Hide video panel and check that video panel and video container for Speaker not visible.

        interpreterPage.hideVideoPanel();

        interpreterVerifier.assertVideoPanelNotVisible();
        interpreterVerifier.assertVideoContainerNotVisible(speakerName);

        // Show video panel and check that video panel and video container for Speaker visible.

        interpreterPage.showVideoPanel();

        interpreterVerifier.assertVideoPanelVisible();
        interpreterVerifier.assertVideoContainerVisible(speakerName);

        throwErrorIfVerificationsFailed();
    }
}
