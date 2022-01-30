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

public class ChangeOutgoingChannelByShortcutTest extends BaseWebTest implements EventTest {

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
            testName = "Change outgoing channel by shortcut",
            description = "Interpreter can change Outgoing channel language by shortcut.",
            groups = {
                    ONE_USER,
                    EVENT,
                    SHORTCUTS,
                    INTERPRETER
            }
    )
    @TestRailCase("2030")
    public void test() {

        // Log in as Interpreter with Main and Relay language.

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, mainLanguage, relayLanguage);
        interpreterPage.assertIsOpened();

        // Check that Main language is active in Outgoing channel.

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);

        interpreterVerifier.assertActiveOutgoingLanguage(mainLanguage);

        // Change Outgoing language to Relay by shortcut.

        interpreterPage.changeOutgoingLanguageByShortcut();

        interpreterVerifier.verifyShortcutsIndicatorDisplayedForOutgoingChannel();
        interpreterVerifier.verifyOutgoingLanguageChanged(relayLanguage);
        interpreterVerifier.assertActiveOutgoingLanguage(relayLanguage);

        // Check that 'Shortcuts indicator' is not displayed for Outgoing channel after several seconds.

        interpreterVerifier.verifyShortcutsIndicatorNotDisplayedForOutgoingChannel();

        // Change Outgoing language back to Main by shortcut.

        interpreterPage.changeOutgoingLanguageByShortcut();

        interpreterVerifier.verifyShortcutsIndicatorDisplayedForOutgoingChannel();
        interpreterVerifier.verifyOutgoingLanguageChanged(mainLanguage);
        interpreterVerifier.assertActiveOutgoingLanguage(mainLanguage);

        // Check that 'Shortcuts indicator' is not displayed for Outgoing channel after several seconds.

        interpreterVerifier.verifyShortcutsIndicatorNotDisplayedForOutgoingChannel();

        throwErrorIfVerificationsFailed();
    }
}
