package com.intpfy.test.core_regression.web_panel.keyboard_shortcuts.interpreter;


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
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class LowerIncomingSpeakerVolumeByShortcutTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    private final Language language = Language.getRandomLanguage();

    // Create 'Connect Pro (Classroom)' event with 1 Language.
    private final Event event = Event
            .createConnectProClassroomBuilder()
            .withLanguage(language)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Lower incoming speaker volume by shortcut",
            description = "Interpreter can lower Incoming channel volume by shortcut.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    SHORTCUTS,
                    INTERPRETER,
                    SPEAKER
            }
    )
    @TestRailCase("2032")
    public void test() {

        // Log in as Host with Audio only.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithAudioOnly(event, hostName);
        hostPage.assertIsOpened();

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);
        hostVerifier.assertStreamWithAudioOnlyGoingOnUI();

        // Log in as Interpreter with Outgoing language.

        WebContextUtil.switchToNewContext();

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, language);
        interpreterPage.assertIsOpened();

        // Set Max volume level for Incoming channel.

        interpreterPage.setMaxVolumeLevelForIncomingChannel();

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);

        interpreterVerifier.assertVolumeLevelIsMaxForIncomingChannel();

        // Unmute to remove focus from Volume control (shortcut should work without Volume control being focused, see CORE-5164).

        interpreterPage.unmute();

        // Set Min volume level for Incoming channel by shortcut.

        interpreterPage.setMinVolumeLevelForIncomingChannelByShortcut();
        interpreterVerifier.assertVolumeLevelIsMinForIncomingChannel();

        throwErrorIfVerificationsFailed();
    }
}
