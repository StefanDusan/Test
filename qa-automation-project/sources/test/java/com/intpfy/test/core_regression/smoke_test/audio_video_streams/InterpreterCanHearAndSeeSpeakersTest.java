package com.intpfy.test.core_regression.smoke_test.audio_video_streams;

import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.gui.pages.web_rtc.WebRtcPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.WebRtcTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.util.WebRtcUtil;
import com.intpfy.verifiers.event.interpreter.InterpreterVerifier;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfy.verifiers.media.web_rtc.WebRtcVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class InterpreterCanHearAndSeeSpeakersTest extends BaseWebTest implements WebRtcTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create 'Connect (WebMeet)' event with 2 Languages.
    private final Event event = Event
            .createConnectWebMeetBuilder()
            .withLanguages(Language.getRandomLanguages(2))
            .build();

    private final Language mainLanguage = event.getLanguages().get(0);
    private final Language relayLanguage = event.getLanguages().get(1);

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check that interpreter can hear and see speakers",
            description = "Interpreter can hear and see Speakers.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    INTERPRETER,
                    SPEAKER,
            }
    )
    @TestRailCase("821")
    public void test() {

        // Log in as Speaker with Audio and Video.

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeakerWithAudioAndVideo(event, speakerName);
        speakerPage.assertIsOpened();

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Log in as Interpreter with Outgoing and Relay language.

        WebContextUtil.switchToNewContext();

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, mainLanguage, relayLanguage);
        interpreterPage.assertIsOpened();

        // Check that Video container for Speaker visible.

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);

        interpreterVerifier.assertVideoContainerVisible(speakerName);

        // Check that Audio and Video from Speaker present.

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPage();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);

        int expectedStreamsCount = 1;

        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int speakerStreamIndex = 1;

        webRtcVerifier.assertIncomingAudioAndVideoPresent(speakerStreamIndex);

        // Change Outgoing language to Relay.

        interpreterPage.changeOutgoingLanguageToRelay();
        interpreterVerifier.assertOutgoingLanguageChanged(relayLanguage);

        // Check that Video container for Speaker visible.

        interpreterVerifier.assertVideoContainerVisible(speakerName);

        // Check that Audio and Video from Speaker present.

        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        webRtcVerifier.assertIncomingAudioAndVideoPresent(speakerStreamIndex);

        throwErrorIfVerificationsFailed();
    }
}
