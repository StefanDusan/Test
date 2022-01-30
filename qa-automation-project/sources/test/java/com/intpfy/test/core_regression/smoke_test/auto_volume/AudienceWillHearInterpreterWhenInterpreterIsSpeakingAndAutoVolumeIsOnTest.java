package com.intpfy.test.core_regression.smoke_test.auto_volume;

import com.intpfy.gui.pages.event.AudiencePage;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.web_rtc.WebRtcPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.WebRtcTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.util.WebRtcUtil;
import com.intpfy.verifiers.event.audience.AudienceVerifier;
import com.intpfy.verifiers.event.interpreter.InterpreterVerifier;
import com.intpfy.verifiers.media.web_rtc.WebRtcVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class AudienceWillHearInterpreterWhenInterpreterIsSpeakingAndAutoVolumeIsOnTest extends BaseWebTest implements WebRtcTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    private final Language language = Language.getRandomLanguage();

    // Create random event with 1 Language and enabled Floor fill (Auto-volume) for Audience.
    private final Event event = Event
            .createRandomBuilder()
            .withLanguage(language)
            .withAudienceAccessToFloor(true)
            .withAudienceAccessToAutoVolume(true)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check that audience will hear interpreter when Interpreter is speaking and auto volume is ON",
            description = "Audience will hear Interpreter when Interpreter is speaking and Auto-volume ON.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    AUDIENCE,
                    INTERPRETER
            }
    )
    @TestRailCase("808")
    public void test() {

        // Log in as Interpreter with Outgoing language and start streaming.

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, language);
        interpreterPage.assertIsOpened();

        interpreterPage.unmute();

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);
        interpreterVerifier.assertStreaming();

        // Log in as Audience.

        WebContextUtil.switchToNewContext();

        AudiencePage audiencePage = authorizer.logInAsAudience(event);
        audiencePage.assertIsOpened();

        // Select same language channel as Interpreter.

        audiencePage.selectLanguageChannel(language);

        AudienceVerifier audienceVerifier = new AudienceVerifier(audiencePage);

        audienceVerifier.assertLanguageChannelSelected(language);

        // Check that Auto-volume enabled.

        audienceVerifier.assertAutoVolumeAvailable();
        audienceVerifier.assertAutoVolumeEnabled();

        // Check that Audience can hear Interpreter.

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPageInReducedWindow();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);

        int expectedStreamsCount = 1;
        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int languageChannelStreamIndex = 1;
        webRtcVerifier.assertIncomingAudioPresent(languageChannelStreamIndex);

        throwErrorIfVerificationsFailed();
    }
}
