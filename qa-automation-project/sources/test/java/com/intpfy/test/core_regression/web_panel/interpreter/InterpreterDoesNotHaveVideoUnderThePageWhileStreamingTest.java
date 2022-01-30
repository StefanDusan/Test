package com.intpfy.test.core_regression.web_panel.interpreter;

import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.interpreter.InterpreterVerifier;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.factories.WebFactoryHelper;
import com.intpfyqa.test.Verify;
import com.intpfyqa.test_rail.TestRailCase;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.intpfy.test.TestGroups.*;

public class InterpreterDoesNotHaveVideoUnderThePageWhileStreamingTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    private static final By ADDITIONAL_VIDEO_LOCATOR = By.xpath("//body/div[contains(@id, 'OT')]");

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
            testName = "Check that interpreter doesn't have video under the page while streaming",
            description = "Additional video from Speaker not displayed for Interpreter.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    INTERPRETER,
                    SPEAKER
            }
    )
    @TestRailCase("2114")
    public void test() {

        // Log in as Speaker with Audio and Video.

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeakerWithAudioAndVideo(event, speakerName);
        speakerPage.assertIsOpened();

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Log in as Interpreter with Outgoing language.

        WebContextUtil.switchToNewContext();

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, language);
        interpreterPage.assertIsOpened();

        // Unmute.

        interpreterPage.unmute();

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);
        interpreterVerifier.assertUnmuted();

        // Check that additional video from Speaker not visible.

        assertAdditionalVideoNotVisible(interpreterPage);

        throwErrorIfVerificationsFailed();
    }

    private void assertAdditionalVideoNotVisible(IParent parent) {

        boolean isNotVisible = WebFactoryHelper
                .getElementFactory()
                .createElement(Element.class, "Additional video", parent, ADDITIONAL_VIDEO_LOCATOR)
                .notVisible(Duration.ZERO);

        Verify.assertTrue(isNotVisible, "Additional video not visible.");
    }
}
