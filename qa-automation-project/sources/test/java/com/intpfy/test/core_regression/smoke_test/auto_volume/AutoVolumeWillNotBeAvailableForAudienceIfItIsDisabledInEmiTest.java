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

public class AutoVolumeWillNotBeAvailableForAudienceIfItIsDisabledInEmiTest extends BaseWebTest implements WebRtcTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    private final Language language = Language.getRandomLanguage();

    // Create 'Connect (WebMeet)' event with 1 Language and disabled 'Floor fill (Auto-volume)' for Audience.
    private final Event event = Event
            .createConnectWebMeetBuilder()
            .withLanguage(language)
            .withAudienceAccessToAutoVolume(false)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check that auto volume will not be available for Audience if it is disabled in EMI",
            description = "Auto-volume will not be available for Audience if it's disabled in EMI.",
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
    @TestRailCase("807")
    public void test() {

        // Log in as Speaker with Audio only.

        String firstSpeakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage firstSpeakerPage = authorizer.logInAsSpeakerWithAudioOnly(event, firstSpeakerName);
        firstSpeakerPage.assertIsOpened();

        SpeakerVerifier firstSpeakerVerifier = new SpeakerVerifier(firstSpeakerPage);

        firstSpeakerVerifier.assertStreamWithAudioOnlyGoingOnUI();

        // Log in as Interpreter with Outgoing language and start streaming.

        WebApplicationContext interpreterContext = WebContextUtil.switchToNewContext();

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

        // Check that Auto-volume unavailable (toggle not visible) when no Language channel selected.

        AudienceVerifier audienceVerifier = new AudienceVerifier(audiencePage);

        audienceVerifier.assertAutoVolumeUnavailable();

        // Select same language channel as Interpreter.

        audiencePage.selectLanguageChannel(language);
        audienceVerifier.assertLanguageChannelSelected(language);

        // Check that Audience can hear Interpreter.

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPage();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);

        int expectedStreamsCount = 1;
        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int languageChannelStreamIndex = 1;
        webRtcVerifier.assertIncomingAudioPresent(languageChannelStreamIndex);

        // Check that Auto-volume still unavailable.

        audienceVerifier.assertAutoVolumeUnavailable();

        // Switch to Interpreter and turn Mic OFF.

        WebContextUtil.switchToContext(interpreterContext);

        interpreterPage.mute();
        interpreterVerifier.assertMuted();

        // Check that Audience hears neither Speaker nor Interpreter (no streams present).

        WebContextUtil.switchToContext(audienceContext);

        webRtcVerifier.assertNoStreamsPresent();

        throwErrorIfVerificationsFailed();
    }
}
