package com.intpfy.test.core_regression.web_panel.moderator_advanced_mode_event_pro_or_webmeet;

import com.intpfy.gui.dialogs.common.ChannelSwitchDW;
import com.intpfy.gui.dialogs.common.ConfirmationDW;
import com.intpfy.gui.dialogs.common.NotificationDW;
import com.intpfy.gui.dialogs.moderator.SetActiveChannelsDW;
import com.intpfy.gui.pages.event.AdvancedModeratorPage;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.run.web.WebApplicationContext;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.interpreter.InterpreterVerifier;
import com.intpfy.verifiers.event.moderator.AdvancedModeratorVerifier;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.intpfy.test.TestGroups.*;
import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

public class ChangeLanguageForSpeakerInterpreterByModeratorTest extends BaseWebTest implements EventTest {

    private static final String CHANNEL_SWITCH_NOTIFICATION_TEXT = "The user's channels have been changed";
    private static final Duration CHANNEL_SWITCH_NOTIFICATION_NOT_DISPLAYED_TIMEOUT = AJAX_TIMEOUT.plus(Duration.ofSeconds(3));

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
            testName = "Check the change language for Speaker/Interpreter (by Moderator)",
            description = "Moderator can change Language for Speaker / Interpreter.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    INTERPRETER,
                    SPEAKER,
                    MODERATOR
            }
    )
    @TestRailCase("1548")
    public void test() {

        // Log in as Moderator with Advanced monitoring.

        String moderatorName = RandomUtil.createRandomModeratorName();
        AdvancedModeratorPage moderatorPage = authorizer.logInAsModeratorWithAdvancedMonitoring(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Log in as Interpreter with Main and Relay language.

        WebApplicationContext secondContext = WebContextUtil.switchToNewContext();

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, mainLanguage, relayLanguage);
        interpreterPage.assertIsOpened();

        // Switch to Moderator and open 'Set active channels' dialog window for Interpreter.

        WebContextUtil.switchToDefaultContext();

        SetActiveChannelsDW setActiveChannelsDW = moderatorPage.openSetActiveChannelsDWFromSourceSession(interpreterName);
        setActiveChannelsDW.assertIsOpened();

        // Check that Interpreter name displayed in dialog window.

        AdvancedModeratorVerifier moderatorVerifier = new AdvancedModeratorVerifier(moderatorPage);

        moderatorVerifier.verifyUsername(setActiveChannelsDW, interpreterName);

        // Change Outgoing language for Interpreter.

        setActiveChannelsDW.selectOutgoingLanguage(relayLanguage);

        setActiveChannelsDW.save();
        setActiveChannelsDW.assertNotVisible();

        // Switch to Interpreter and confirm Outgoing language change.

        WebContextUtil.switchToContext(secondContext);

        ConfirmationDW confirmationDW = new ConfirmationDW(interpreterPage);
        confirmationDW.assertIsOpened();

        confirmationDW.confirm();
        confirmationDW.assertNotVisible();

        // Check that 'Channel switch' dialog window not displayed after timeout and Outgoing language changed.

        ChannelSwitchDW channelSwitchDW = new ChannelSwitchDW(interpreterPage);
        channelSwitchDW.assertIsOpened();

        channelSwitchDW.assertNotVisible(CHANNEL_SWITCH_NOTIFICATION_NOT_DISPLAYED_TIMEOUT);

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);
        interpreterVerifier.verifyOutgoingLanguageChanged(relayLanguage);

        // Switch to Moderator and check that 'Notification' dialog window with required text displayed.

        WebContextUtil.switchToDefaultContext();

        NotificationDW notificationDW = new NotificationDW(moderatorPage);
        notificationDW.assertIsOpened();

        moderatorVerifier.verifyNotificationText(notificationDW, CHANNEL_SWITCH_NOTIFICATION_TEXT);

        notificationDW.confirm();
        notificationDW.assertNotVisible();

        // Log in as Speaker.

        WebContextUtil.switchToContext(secondContext);

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Switch to Moderator and open 'Set active channels' dialog window for Speaker.

        WebContextUtil.switchToDefaultContext();

        setActiveChannelsDW = moderatorPage.openSetActiveChannelsDWFromSourceSession(speakerName);
        setActiveChannelsDW.assertIsOpened();

        // Check that Speaker name displayed in dialog window.

        moderatorVerifier.verifyUsername(setActiveChannelsDW, speakerName);

        // Change Interpreting (incoming) language for Speaker.

        setActiveChannelsDW.selectIncomingLanguage(mainLanguage);

        setActiveChannelsDW.save();
        setActiveChannelsDW.assertNotVisible();

        // Switch to Speaker and confirm Interpreting language change.

        WebContextUtil.switchToContext(secondContext);

        confirmationDW = new ConfirmationDW(speakerPage);
        confirmationDW.assertIsOpened();

        confirmationDW.confirm();
        confirmationDW.assertNotVisible();

        // Check that 'Channel switch' dialog window not displayed after timeout and Interpreting language selected.

        channelSwitchDW = new ChannelSwitchDW(speakerPage);
        channelSwitchDW.assertIsOpened();

        channelSwitchDW.assertNotVisible(CHANNEL_SWITCH_NOTIFICATION_NOT_DISPLAYED_TIMEOUT);

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.verifyInterpretingLanguageSelected(mainLanguage);

        // Switch to Moderator and check that 'Notification' dialog window with required text displayed.

        WebContextUtil.switchToDefaultContext();

        notificationDW = new NotificationDW(moderatorPage);
        notificationDW.assertIsOpened();

        moderatorVerifier.verifyNotificationText(notificationDW, CHANNEL_SWITCH_NOTIFICATION_TEXT);

        notificationDW.confirm();
        notificationDW.assertNotVisible();

        throwErrorIfVerificationsFailed();
    }
}
