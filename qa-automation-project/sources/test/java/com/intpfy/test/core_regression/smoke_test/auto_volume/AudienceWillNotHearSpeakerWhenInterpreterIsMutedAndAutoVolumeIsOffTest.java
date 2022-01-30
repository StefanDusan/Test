package com.intpfy.test.core_regression.smoke_test.auto_volume;

import com.intpfy.gui.pages.event.AudiencePage;
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
import com.intpfy.verifiers.event.audience.AudienceVerifier;
import com.intpfy.verifiers.event.interpreter.InterpreterVerifier;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfy.verifiers.media.web_rtc.WebRtcVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class AudienceWillNotHearSpeakerWhenInterpreterIsMutedAndAutoVolumeIsOffTest extends BaseWebTest implements WebRtcTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    private final Language language = Language.getRandomLanguage();

    // Create 'Connect (WebMeet)' event with 1 Language and enabled Floor fill (Auto-volume) for Audience.
    private final Event event = Event
            .createConnectWebMeetBuilder()
            .withLanguage(language)
            .withAudienceAccessToFloor(true)
            .withAudienceAccessToAutoVolume(true)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check that audience will NOT hear speaker when Interpreter is muted and auto volume is OFF",
            description = "Audience will not hear Speaker when Interpreter is muted and Auto-volume OFF.",
            groups = {
                    THREE_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    AUDIENCE,
                    INTERPRETER,
                    SPEAKER
            }
    )
    @TestRailCase("811")
    public void test() {

        // Log in as Interpreter with Outgoing language and start streaming.

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, language);
        interpreterPage.assertIsOpened();

        interpreterPage.unmute();

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);
        interpreterVerifier.assertStreaming();

        // Log in as Audience.

        WebApplicationContext audienceContext = WebContextUtil.switchToNewContext();

        AudiencePage audiencePage = authorizer.logInAsAudience(event);
        audiencePage.assertIsOpened();

        // Select same language channel as Interpreter.

        audiencePage.selectLanguageChannel(language);

        AudienceVerifier audienceVerifier = new AudienceVerifier(audiencePage);

        audienceVerifier.assertLanguageChannelSelected(language);

        // Disable Auto-volume.

        audienceVerifier.assertAutoVolumeAvailable();

        audiencePage.disableAutoVolume();
        audienceVerifier.assertAutoVolumeDisabled();

        // Check that Audience can hear Interpreter.

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPage();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);

        int expectedStreamsCount = 1;
        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int languageChannelStatsTabIndex = 1;
        webRtcVerifier.assertIncomingAudioPresent(languageChannelStatsTabIndex);

        // Switch to Interpreter and turn Mic OFF.

        WebContextUtil.switchToDefaultContext();

        interpreterPage.mute();
        interpreterVerifier.assertMuted();

        // Log in as Speaker with Audio only.

        WebContextUtil.switchToNewContext();

        String firstSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage firstSpeakerPage = authorizer.logInAsSpeakerWithAudioOnly(event, firstSpeakerName);
        firstSpeakerPage.assertIsOpened();

        SpeakerVerifier firstSpeakerVerifier = new SpeakerVerifier(firstSpeakerPage);
        firstSpeakerVerifier.assertStreamWithAudioOnlyGoingOnUI();

        // Check that Audience hears neither Speaker nor Interpreter (no streams present).

        WebContextUtil.switchToContext(audienceContext);

        webRtcVerifier.assertNoStreamsPresent();

        throwErrorIfVerificationsFailed();
    }
}
