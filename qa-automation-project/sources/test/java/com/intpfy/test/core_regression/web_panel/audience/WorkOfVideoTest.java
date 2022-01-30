package com.intpfy.test.core_regression.web_panel.audience;

import com.intpfy.gui.pages.event.AudiencePage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.audience.AudienceVerifier;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class WorkOfVideoTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create 'Connect (WebMeet)' event with enabled Audience access to Video.
    private final Event event = Event
            .createConnectWebMeetBuilder()
            .withAllowAudienceVideo(true)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check the work of video",
            description = "Audience can turn Video ON / OFF.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    AUDIENCE,
                    SPEAKER
            }
    )
    @TestRailCase("1542")
    public void test() {

        // Log in as Speaker with Audio and Video.

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeakerWithAudioAndVideo(event, speakerName);
        speakerPage.assertIsOpened();

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Log in as Audience.

        WebContextUtil.switchToNewContext();

        AudiencePage audiencePage = authorizer.logInAsAudience(event);
        audiencePage.assertIsOpened();

        // Check that Video ON but video container for Speaker not visible.

        AudienceVerifier audienceVerifier = new AudienceVerifier(audiencePage);

        audienceVerifier.assertVideoOn();
        audienceVerifier.assertVideoContainerNotVisible(speakerName);

        // Select Source channel (connect).

        Language source = Language.Source;
        audiencePage.selectLanguageChannel(source);
        audienceVerifier.assertConnectedToLanguageChannel(source);

        // Check that video container for Speaker visible.

        audienceVerifier.assertVideoContainerVisible(speakerName);

        // Turn Video OFF and check that video container for Speaker not visible.

        audiencePage.turnVideoOff();
        audienceVerifier.assertVideoOff();

        audienceVerifier.assertVideoContainerNotVisible(speakerName);

        // Turn Video ON and check that video container for Speaker visible.

        audiencePage.turnVideoOn();
        audienceVerifier.assertVideoOn();

        audienceVerifier.assertVideoContainerVisible(speakerName);

        throwErrorIfVerificationsFailed();
    }
}
