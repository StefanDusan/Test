package com.intpfy.test.core_regression.web_panel.moderator_advanced_mode_event_pro_or_webmeet;

import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.event.ModeratorPage;
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
import com.intpfy.verifiers.event.moderator.ModeratorVerifier;
import com.intpfy.verifiers.media.web_rtc.WebRtcVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class AudioOnAnyLanguageOnOffTest extends BaseWebTest implements WebRtcTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    private final Language language = Language.getRandomLanguage();

    // Create random event with 1 Language.
    private final Event event = Event
            .createRandomBuilder()
            .withLanguage(language)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check the audio on any language (on/off)",
            description = "Moderator can turn Audio ON / OFF for Language session.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    INTERPRETER,
                    MODERATOR
            }
    )
    @TestRailCase("1553")
    public void test() {

        // Log in as Moderator.

        String moderatorName = RandomUtil.createRandomModeratorName();
        ModeratorPage moderatorPage = authorizer.logInAsModerator(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Log in as Interpreter with Outgoing language.

        WebApplicationContext interpreterContext = WebContextUtil.switchToNewContext();

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, language);
        interpreterPage.assertIsOpened();

        // Switch to Moderator and turn Volume ON for Language session.

        WebContextUtil.switchToDefaultContext();

        moderatorPage.turnVolumeOnForLanguageSession(language);

        ModeratorVerifier moderatorVerifier = new ModeratorVerifier(moderatorPage);

        moderatorVerifier.assertVolumeOnForLanguageSession(language);

        // Switch to Interpreter and start streaming.

        WebContextUtil.switchToContext(interpreterContext);

        interpreterPage.unmute();

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);
        interpreterVerifier.assertStreaming();

        // Switch to Moderator and check that Audio from Interpreter present.

        WebContextUtil.switchToDefaultContext();

        moderatorVerifier.assertIncomingVolumeLevelChangingInLanguageSession(language);

        WebRtcPage webRtcPage = WebRtcUtil.openWebRtcPage();
        webRtcPage.assertIsOpened();

        WebRtcVerifier webRtcVerifier = new WebRtcVerifier(webRtcPage);

        int expectedStreamsCount = 1;
        webRtcVerifier.assertStreamsCount(expectedStreamsCount);

        int interpreterStreamIndex = 1;
        webRtcVerifier.assertIncomingAudioPresent(interpreterStreamIndex);

        // Turn Volume OFF for Language session.

        moderatorPage.turnVolumeOffForLanguageSession(language);
        moderatorVerifier.assertVolumeOffForLanguageSession(language);

        // Check that Audio from Interpreter not present.

        moderatorVerifier.assertIncomingVolumeLevelNotChangingInLanguageSession(language);

        webRtcVerifier.assertNoStreamsPresent();

        throwErrorIfVerificationsFailed();
    }
}
