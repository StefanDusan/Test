package com.intpfy.test.core_regression.web_panel.moderator_advanced_mode_event_pro_or_webmeet;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.dialogs.common.LanguageSettingsDW;
import com.intpfy.gui.pages.event.AdvancedModeratorPage;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.run.web.WebApplicationContext;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.interpreter.InterpreterVerifier;
import com.intpfy.verifiers.event.moderator.AdvancedModeratorVerifier;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.test_rail.TestRailBugId;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class ChangeLanguageForSpeakerInterpreterTest extends BaseWebTest implements EventTest {

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
            testName = "Check the change language for Speaker/Interpreter (by Speaker/Interpreter)",
            description = "Moderator can see Language change by Speaker / Interpreter.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    INTERPRETER,
                    SPEAKER,
                    MODERATOR,
            }
    )
    @TestRailCase("1549")
    @TestRailBugId("CORE-6870")
    public void test() {

        // Log in as Moderator with Advanced monitoring.

        String moderatorName = RandomUtil.createRandomModeratorName();
        AdvancedModeratorPage moderatorPage = authorizer.logInAsModeratorWithAdvancedMonitoring(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Log in as Speaker.

        WebApplicationContext secondContext = WebContextUtil.switchToNewContext();

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Open 'Language settings' dialog window and select Interpreting language.

        LanguageSettingsDW speakerLanguageSettingsDW = speakerPage.openLanguageSettings();
        speakerLanguageSettingsDW.assertIsOpened();

        speakerLanguageSettingsDW.selectIncomingLanguage(mainLanguage);

        speakerLanguageSettingsDW.save();
        speakerLanguageSettingsDW.assertNotVisible();

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.verifyInterpretingLanguageSelected(mainLanguage);

        // Switch to Moderator.

        WebContextUtil.switchToDefaultContext();

        AdvancedModeratorVerifier moderatorVerifier = new AdvancedModeratorVerifier(moderatorPage);

        moderatorVerifier.verifyUserIncomingLanguageInSourceSession(speakerName, mainLanguage);

        // Log in as Interpreter with Main and Relay language.

        WebContextUtil.switchToContext(secondContext);

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, mainLanguage, relayLanguage);
        interpreterPage.assertIsOpened();

        // Change Incoming language to Relay.

        interpreterPage.changeIncomingLanguageToRelay();

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);
        interpreterVerifier.verifyActiveIncomingLanguage(relayLanguage);

        // Switch to Moderator and check that Interpreter not present in Source session and can listen in Language session.

        WebContextUtil.switchToDefaultContext();

        moderatorVerifier.verifyUserNotPresentInSourceSession(interpreterName);

        moderatorVerifier.verifyUserPresentInLanguageSession(interpreterName, relayLanguage);
        moderatorVerifier.verifyUserCanListenInLanguageSession(interpreterName, relayLanguage);

        // Switch to Interpreter and change Outgoing language to Relay.

        WebContextUtil.switchToContext(secondContext);

        interpreterPage.changeOutgoingLanguageToRelay();

        // Switch to Moderator and check that Interpreter present in Source session and can listen there.

        WebContextUtil.switchToDefaultContext();

        moderatorVerifier.verifyUserPresentInSourceSession(interpreterName);
        moderatorVerifier.verifyUserCanListenInSourceSession(interpreterName);

        throwErrorIfVerificationsFailed();
    }
}
