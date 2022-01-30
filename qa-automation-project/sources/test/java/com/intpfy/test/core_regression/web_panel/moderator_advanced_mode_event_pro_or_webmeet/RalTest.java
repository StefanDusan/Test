package com.intpfy.test.core_regression.web_panel.moderator_advanced_mode_event_pro_or_webmeet;

import com.intpfy.gui.pages.event.AdvancedModeratorPage;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.gui.pages.web_rtc.WebRtcPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.run.web.WebApplicationContext;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.WebRtcTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.util.WebRtcUtil;
import com.intpfy.verifiers.event.interpreter.InterpreterVerifier;
import com.intpfy.verifiers.event.moderator.AdvancedModeratorVerifier;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfy.verifiers.media.web_rtc.WebRtcVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import com.intpfyqa.utils.TestUtils;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class RalTest extends BaseWebTest implements WebRtcTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    private final Language language = Language.getRandomLanguage();

    // Create 'Connect (WebMeet)' event with 1 Language.
    private final Event event = Event
            .createConnectWebMeetBuilder()
            .withLanguage(language)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check the RAL",
            description = "Moderator can Restart all lines successfully.",
            groups = {
                    THREE_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    INTERPRETER,
                    SPEAKER,
                    MODERATOR
            }
    )
    @TestRailCase("1556")
    public void test() {

        // Log in as Speaker with Audio and Video.

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeakerWithAudioAndVideo(event, speakerName);
        speakerPage.assertIsOpened();

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Log in as Interpreter with Outgoing language and start streaming.

        WebContextUtil.switchToNewContext();

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, language);
        interpreterPage.assertIsOpened();

        interpreterPage.unmute();

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);
        interpreterVerifier.assertStreaming();

        // Log in as Moderator with Advanced monitoring.

        WebApplicationContext moderatorContext = WebContextUtil.switchToNewContext();

        String moderatorName = RandomUtil.createRandomModeratorName();
        AdvancedModeratorPage moderatorPage = authorizer.logInAsModeratorWithAdvancedMonitoring(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Check that video container for Speaker visible.

        AdvancedModeratorVerifier moderatorVerifier = new AdvancedModeratorVerifier(moderatorPage);

        moderatorVerifier.assertVideoContainerVisible(speakerName);

        // Turn Volume ON for Source session.

        moderatorPage.turnVolumeOnForSourceSession();
        moderatorVerifier.assertVolumeOnForSourceSession();

        // Check that Audio and Video from Speaker present.

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPage();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);

        int expectedStreamsCount = 1;

        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int incomingStreamIndex = 1;

        webRtcVerifier.assertIncomingAudioAndVideoPresent(incomingStreamIndex);

        moderatorPage.closeBrowserTabInactiveErrorDW();

        // RAL and check that Source session User list reloading for some time.

        moderatorPage.restartAllLines();

        moderatorVerifier.verifySourceSessionUserListReloading();
        moderatorVerifier.verifySourceSessionUserListNotReloading();

        // RAL and check that Speaker disappears from Source session User list for some time.

        moderatorPage.restartAllLines();

        moderatorVerifier.verifyUserNotPresentInSourceSession(speakerName);
        moderatorVerifier.verifyUserPresentInSourceSession(speakerName);

        // RAL and check that video container for Speaker not visible for some time.

        moderatorPage.restartAllLines();

        // Verification fails sometimes on remote test execution.
        // moderatorVerifier.verifyVideoContainerNotVisible(speakerName);
        moderatorVerifier.assertVideoContainerVisible(speakerName);

        // Check that Audio and Video from Speaker present.

        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        webRtcVerifier.assertIncomingAudioAndVideoPresent(incomingStreamIndex);

        moderatorPage.closeBrowserTabInactiveErrorDW();

        // Switch to Speaker and Disconnect.

        WebContextUtil.switchToDefaultContext();

        speakerPage.disconnect();

        // Switch to Moderator and turn Volume ON for Language session.

        WebContextUtil.switchToContext(moderatorContext);

        moderatorPage.turnVolumeOnForLanguageSession(language);
        moderatorVerifier.assertVolumeOnForLanguageSession(language);

        // Check that Audio from Interpreter present.

        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        webRtcVerifier.assertIncomingAudioPresent(incomingStreamIndex);

        moderatorPage.closeBrowserTabInactiveErrorDW();

        // RAL and check that Audio from Interpreter present.

        moderatorPage.restartAllLines();

        // Sleep for 3 seconds to make sure that RAL changes are arrived to WebRTC page.
        TestUtils.sleep(3000);

        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        webRtcVerifier.assertIncomingAudioPresent(incomingStreamIndex);

        moderatorPage.closeBrowserTabInactiveErrorDW();

        throwErrorIfVerificationsFailed();
    }
}
