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
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class ChangeIncomingChannelByShortcutTest extends BaseWebTest implements EventTest {

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
            testName = "Change incoming channel by shortcut",
            description = "Interpreter can change Incoming channel language by shortcut.",
            groups = {
                    ONE_USER,
                    EVENT,
                    SHORTCUTS,
                    INTERPRETER
            }
    )
    @TestRailCase("2029")
    public void test() {

        // Log in as Interpreter with Main and Relay language.

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, mainLanguage, relayLanguage);
        interpreterPage.assertIsOpened();

        // Check that Source language is active in Incoming channel.

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);

        Language source = Language.Source;

        interpreterVerifier.assertActiveIncomingLanguage(source);

        // Change Incoming language to Relay by shortcut.

        interpreterPage.changeIncomingLanguageByShortcut();

        interpreterVerifier.verifyShortcutsIndicatorDisplayedForIncomingChannel();
        interpreterVerifier.assertActiveIncomingLanguage(relayLanguage);

        // Check that 'Shortcuts indicator' is not displayed for Incoming channel after several seconds.

        interpreterVerifier.verifyShortcutsIndicatorNotDisplayedForIncomingChannel();

        // Change Incoming language back to Source by shortcut.

        interpreterPage.changeIncomingLanguageByShortcut();

        interpreterVerifier.verifyShortcutsIndicatorDisplayedForIncomingChannel();
        interpreterVerifier.assertActiveIncomingLanguage(source);

        // Check that 'Shortcuts indicator' is not displayed for Incoming channel after several seconds.

        interpreterVerifier.verifyShortcutsIndicatorNotDisplayedForIncomingChannel();

        throwErrorIfVerificationsFailed();
    }
}
