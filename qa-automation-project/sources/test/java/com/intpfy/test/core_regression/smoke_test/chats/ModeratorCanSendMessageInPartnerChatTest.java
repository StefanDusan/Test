package com.intpfy.test.core_regression.smoke_test.chats;

import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.event.ModeratorPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.verifiers.event.interpreter.InterpreterVerifier;
import com.intpfy.verifiers.event.moderator.ModeratorVerifier;
import com.intpfyqa.test_rail.TestRailBugId;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class ModeratorCanSendMessageInPartnerChatTest extends BaseWebTest implements EventTest {

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
            testName = "Check that Moderator can send message in Partner Chat",
            description = "Moderator can send message in Partner Chat.",
            groups = {
                    ONE_USER,
                    EVENT,
                    CHATS,
                    INTERPRETER,
                    MODERATOR
            }
    )
    @TestRailCase("791")
    @TestRailBugId(values = {"CORE-5933", "CORE-6485"})
    public void test() {

        // Log in as Moderator.

        String moderatorName = RandomUtil.createRandomModeratorName();
        ModeratorPage moderatorPage = authorizer.logInAsModerator(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Send message to Partner chat for Language 1 channel.

        String message = RandomUtil.createRandomChatMessage();
        moderatorPage.sendMessageToPartnerChat(message, language);

        // Check that message exists in Partner chat.

        ModeratorVerifier moderatorVerifier = new ModeratorVerifier(moderatorPage);
        moderatorVerifier.assertMessageExistsInLanguageSessionPartnerChat(message, language);

        // Log out and Log in as Interpreter with Outgoing language.

        LoginPage loginPage = moderatorPage.logOut();
        loginPage.assertIsOpened();

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, language);
        interpreterPage.assertIsOpened();

        // Check that message from Moderator exists in Partner chat.

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);
        interpreterVerifier.assertMessageExistsInPartnerChat(message);

        throwErrorIfVerificationsFailed();
    }
}
