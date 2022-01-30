package com.intpfy.test.core_regression.web_panel.keyboard_shortcuts.interpreter;


import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.verifiers.event.interpreter.InterpreterVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import com.intpfyqa.utils.TestUtils;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.intpfy.test.TestGroups.*;

public class CoughByShortcutTest extends BaseWebTest implements EventTest {

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
            testName = "Cough by shortcut",
            description = "Interpreter can Cough by shortcut.",
            groups = {
                    ONE_USER,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    SHORTCUTS,
                    INTERPRETER
            }
    )
    @TestRailCase("2027")
    public void test() {

        // Log in as Interpreter with Outgoing language.

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, language);
        interpreterPage.assertIsOpened();

        // Start streaming.

        interpreterPage.unmute();

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);

        interpreterVerifier.assertStreaming();

        // Cough by shortcut for 5 seconds.

        Duration coughDuration = Duration.ofSeconds(5);

        interpreterPage.coughByShortcut(coughDuration);
        interpreterVerifier.assertCoughing();

        // Check that after 5 seconds Interpreter not Coughing.

        TestUtils.sleep(coughDuration.toMillis());

        interpreterVerifier.assertNotCoughing();
        interpreterVerifier.assertStreaming();

        throwErrorIfVerificationsFailed();
    }
}
