package com.intpfy.test.core_regression.web_panel.moderator_advanced_mode_event_pro_or_webmeet;

import com.intpfy.gui.dialogs.moderator.MicrophoneSettingsDW;
import com.intpfy.gui.dialogs.moderator.StartVoicePublishingDW;
import com.intpfy.gui.pages.event.ModeratorPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.verifiers.event.moderator.ModeratorVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class MicrophoneOnOffTest extends BaseWebTest implements EventTest {

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
            testName = "Check the microphone (on/off)",
            description = "Moderator can turn Mic ON / OFF for Language session.",
            groups = {
                    ONE_USER,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    INTERPRETER,
                    MODERATOR
            }
    )
    @TestRailCase("1558")
    public void test() {

        // Log in as Moderator.

        String moderatorName = RandomUtil.createRandomModeratorName();
        ModeratorPage moderatorPage = authorizer.logInAsModerator(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Check that Mic on the top of the page is OFF.

        ModeratorVerifier moderatorVerifier = new ModeratorVerifier(moderatorPage);

        moderatorVerifier.assertMicOff();

        // Open 'Microphone settings' dialog window and confirm it with default audio device.

        MicrophoneSettingsDW microphoneSettingsDW = moderatorPage.openMicSettings();
        microphoneSettingsDW.assertIsOpened();

        microphoneSettingsDW.confirm();
        microphoneSettingsDW.assertNotVisible();

        // Check that Mic OFF and Outgoing Audio not present in Source session.

        moderatorVerifier.assertMicOffForSourceSession();
        moderatorVerifier.assertOutgoingVolumeLevelNotChangingInSourceSession();

        // Check that Mic OFF and Outgoing Audio not present in Language session.

        moderatorVerifier.assertMicOffForLanguageSession(language);
        moderatorVerifier.assertOutgoingVolumeLevelNotChangingInLanguageSession(language);

        // Turn Mic ON for Language session.

        StartVoicePublishingDW startVoicePublishingDW = moderatorPage.turnMicOnForLanguageSession(language);
        startVoicePublishingDW.assertIsOpened();

        startVoicePublishingDW.confirm();
        startVoicePublishingDW.assertNotVisible();

        // Check that Mic ON and Outgoing Audio present in Language session.

        moderatorVerifier.assertMicOnForLanguageSession(language);
        moderatorVerifier.assertOutgoingVolumeLevelChangingInLanguageSession(language);

        // Turn Mic OFF for Language session.

        moderatorPage.turnMicOffForLanguageSession(language);

        // Check that Mic on the top of the page and Language session Mic OFF and Outgoing Audio not present in Language session.

        moderatorVerifier.assertMicOff();

        moderatorVerifier.assertMicOffForLanguageSession(language);
        moderatorVerifier.assertOutgoingVolumeLevelNotChangingInLanguageSession(language);

        throwErrorIfVerificationsFailed();
    }
}
