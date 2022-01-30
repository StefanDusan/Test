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

public class ChangeOutgoingLanguageTest extends BaseWebTest implements EventTest {

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
            testName = "Check the change outgoing language (and switching)",
            description = "Interpreter can change Outgoing language.",
            groups = {
                    ONE_USER,
                    EVENT,
                    INTERPRETER
            }
    )
    @TestRailCase("1532")
    public void test() {

        // Log in as Interpreter with Main and Relay language.

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, mainLanguage, relayLanguage);
        interpreterPage.assertIsOpened();

        // Change Outgoing language to Relay.

        interpreterPage.changeOutgoingLanguageToRelay();

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);

        interpreterVerifier.assertOutgoingLanguageChanged(relayLanguage);

        // Change Outgoing language to Main.

        interpreterPage.changeOutgoingLanguageToMain();
        interpreterVerifier.assertOutgoingLanguageChanged(mainLanguage);

        throwErrorIfVerificationsFailed();
    }
}
