package com.intpfy.test.core_regression.web_panel.interpreter;

import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.verifiers.event.interpreter.InterpreterVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class StartAudioTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create random event with 2 Languages.
    private final Event event = Event
            .createRandomBuilder()
            .withLanguages(Language.getRandomLanguages(2))
            .build();

    private final Language mainLanguage = event.getLanguages().get(0);
    private final Language relayLanguage = event.getLanguages().get(1);

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check the start audio (microphone on/off)",
            description = "Interpreter can turn Mic ON / OFF.",
            groups = {
                    ONE_USER,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    INTERPRETER
            }
    )
    @TestRailCase("1533")
    public void test() {

        // Log in as Interpreter with Main and Relay language and start streaming.

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, mainLanguage, relayLanguage);
        interpreterPage.assertIsOpened();

        interpreterPage.unmute();

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);
        interpreterVerifier.assertStreaming();

        // Turn Mic OFF and check that Interpreter muted.

        interpreterPage.mute();
        interpreterVerifier.assertMuted();

        throwErrorIfVerificationsFailed();
    }
}
